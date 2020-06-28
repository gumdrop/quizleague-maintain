package quizleague.rest.endpoint

import java.util.UUID.randomUUID
import java.util.logging.{Level, Logger}

import javax.ws.rs.{GET, POST, Path, Produces}
import quizleague.data.Storage
import quizleague.data.Storage.{list,group,key}
import quizleague.domain._
import quizleague.conversions.RefConversions.{log, _}
import quizleague.util.json.codecs.DomainCodecs._
import io.circe._
import io.circe.syntax._
import quizleague.domain.container.NestedDomainContainer
import quizleague.domain.stats.CompetitionStatistics

import scala.reflect.ClassTag
import scala.language.implicitConversions

@Path("/migrate")
class NestedEntityMigrationEndpoint {

    val log = Logger.getLogger(this.getClass.toString())

    def toTuple[T <: Entity](entity:T) = (entity.key.get.key, entity)
    implicit def toMap[T <: Entity](entities:Iterable[T]) = entities.map(toTuple _).toMap

    @POST
    @Path("/dbmigration")
    @Produces(Array("application/json"))
    def migrate() = {

        implicit val context = StorageContext()

        def idpair[T <: Entity](entity:T) = (entity.id, entity)
        def keypair[T <: Entity](entity: T) = (entity.key.get, entity)

        def listMap[T <: Entity](implicit tag:ClassTag[T],decoder: Decoder[T]) = list[T]()(tag,decoder).map(idpair _).toMap

        def groupMap[T <: Entity](implicit tag:ClassTag[T],decoder: Decoder[T]) = group[T](tag,decoder).map(idpair _).toMap


        val seasons = list[Season]
        val competitionSet = listMap[Competition]
        val leagueTableSet = listMap[LeagueTable]
        val fixturesSet = listMap[Fixtures]
        val fixtureSet = listMap[Fixture]
        val reportsSet = listMap[Reports]
        val textSet = listMap[Text]
        val globalText = list[GlobalText]
        val competitionStatistics = list[CompetitionStatistics]
        val chats = groupMap[Chat]
        val chatMessages = group[ChatMessage]


        val competitionsToSave = seasons.flatMap(s => s.competitions.flatMap(c => competitionSet.get(c.id).map(_.withKey(Key(s.key.map(_.key),"competition",c.id)))))
        val fixturesToSave = competitionSet
          .values
            .flatMap(c =>
                c match {
                    case a:FixturesCompetition => List(a)
                    case _ => List()
                }
            )
          .flatMap(c => c.fixtures.flatMap(f => fixturesSet.get(f.id).map(_.withKey(Key(c.key.map(_.key),"fixtures",f.id)))))

        val leagueTableToSave = competitionSet
          .values
          .flatMap(c =>
              c match {
                  case a:CompetitionTables => List(a)
                  case _ => List()
              }
          )
          .flatMap(c => c.tables.flatMap(f => leagueTableSet.get(f.id).map(_.withKey(Key(c.key.map(_.key),"leaguetable",f.id)))))

        val fixtureToSave = fixturesSet.values
          .flatMap(f => f.fixtures
            .flatMap(fix => fixtureSet
              .get(fix.id)
              .fold(List[Fixture]())
              (fx => List(fx.withKey(Key(f.key.get,"fixture",fx.id))))));


        val reportsToSave  = fixtureToSave
          .flatMap(f => f.key.flatMap(key => f.result.map((key, _))))
          .flatMap{case(key,result) => result.reports.map(reports => (key, reportsSet.get(reports.id).get))}
          .flatMap{case(key, reports) =>  reports.reports.map(report => report.withKey(Key(key, "report", randomUUID.toString)))}

        val reportText = reportsToSave.map(report => textSet.get(report.text.id).get.withKey(Key(report.key.map(_.key),"text", report.text.id)))

        val competitionStatisticsToSave = competitionStatistics.map(cs => {
            val results = cs.results.map(r => r.copy(competition = r.competition.flatMap(x => competitionsToSave.find(_.id == x.id).map(c => Ref[Competition](c.key.get)))))
            cs.copy(results = results).withKey(Key(None,"competitionstatistics",cs.id))
        })

        val chatsToSave = chats.values.map(chat => {
            val reportsKey = Key(chat.key.get.parentKey.get)
            val reportsId = reportsKey.id

            val fixtureKey = fixtureSet.values
                .filter(f => f.result
                  .flatMap(_.reports)
                  .exists(_.id == reportsId))
              .headOption
              .flatMap(_.key)
              .map(_.key)

            chat.withKey(Key(fixtureKey,"chat", chat.id))
        })

        val chatMessagesToSave = chatMessages.flatMap(cm => {
          val chatId = cm.key.flatMap(_.parentKey).map(Key(_)).map(_.id)
          chatId
            .flatMap(id => chats.get(id))
              .map(_.key)
              .map(key => cm.withKey(key))
        })

        NestedDomainContainer(
            applicationcontext = list[ApplicationContext],
            season = seasons,
            competition = competitionsToSave,
            leaguetable = leagueTableToSave,
            team = list[Team],
            venue = list[Venue],
            chat = chatsToSave,
            chatMessage = chatMessagesToSave,
            globaltext = globalText,
            text = list[Text] ++ reportText,
            user = list[User],
            fixtures = fixturesToSave,
            fixture = fixtureToSave,
            reports = reportsToSave,
            competitionStatistics = competitionStatisticsToSave

        ).asJson.noSpaces
    }

    def safeRefToObject[T <: Entity](ref:Ref[T])(implicit tag:ClassTag[T],decoder: Decoder[T], context:StorageContext = StorageContext()):List[T] = {
        try{
            List(Storage.load(ref.id))
        }
        catch {
            case npe : Exception => {
                log.log(Level.WARNING, s"error in load for ref : $ref", npe)
                List()
            }
        }
    }
}
