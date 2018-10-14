package quizleague.rest.calendar

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import quizleague.data.Storage._
import quizleague.data._
import quizleague.domain._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.conversions.RefConversions._
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import quizleague.domain.TeamCompetition
import quizleague.domain.SingletonCompetition
import java.time._


class CalendarHandler extends HttpServlet{
  
  val utc = ZoneOffset.UTC
  val local = ZoneId.of("Europe/London")
  val dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'").withZone(utc)
  override def doPost(req: HttpServletRequest, resp: HttpServletResponse) = {}  
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) = {
      val bits = parts(req)
      val head = bits.head
      val id = bits.tail.head
      
      implicit val context = StorageContext()
      
      
      
      val contents = head match{
        
        case "team" => makeICal(load[Team](id))
        case _ => ""
        
      }
      
      resp.setContentType("text/calendar")
      resp.getWriter.append(contents)
      resp.getWriter.flush()
    }
    
    private def formatEvent(event:BaseEvent, text:String)(implicit context:StorageContext):String = {
      val now = toUtc(LocalDateTime.now())
      val uidPart = text.replaceAll("\\s", "") 
      val address = event.venue.address.replaceAll("\\n\\r", ",").replaceAll("\\n", ",").replaceAll("\\r", ",")
      s"""
BEGIN:VEVENT
DTSTAMP:$now
UID:${event.date}.$uidPart.chilternquizleague.uk
DESCRIPTION:$text
SUMMARY:$text
DTSTART:${toUtc(event.date.atTime(event.time))}
DTEND:${toUtc(event.date.atTime(event.time plus event.duration))}
LOCATION:${event.venue.name},$address
END:VEVENT
"""

    }
    private def formatFixture(fixture:Fixture, description:String)(implicit context:StorageContext) = {
      
      val text = s"${fixture.home.shortName} - ${fixture.away.shortName} : $description"
      
      val now = toUtc(LocalDateTime.now())
      val uidPart = fixture.home.shortName.replaceAll("\\s", "") 
      val address = fixture.venue.address.replaceAll("\\n\\r", ",").replaceAll("\\n", ",").replaceAll("\\r", ",")
      s"""
BEGIN:VEVENT
DTSTAMP:$now
UID:${fixture.date}.$uidPart.chilternquizleague.uk
DESCRIPTION:$text
SUMMARY:$text
DTSTART:${toUtc(fixture.date.atTime(fixture.time))}
DTEND:${toUtc(fixture.date.atTime(fixture.time plus fixture.duration))}
LOCATION:${fixture.venue.name},$address
END:VEVENT
"""

    }
    private def formatBlankFixtures(fixtures:Fixtures)(implicit context:StorageContext) = {
      
      val now = toUtc(LocalDateTime.now)
      val uidPart = fixtures.description.replaceAll("\\s", "") 
      s"""
BEGIN:VEVENT
DTSTAMP:$now
UID:${fixtures.date}.$uidPart.chilternquizleague.uk
DESCRIPTION:${fixtures.description}
SUMMARY:${fixtures.description}
DTSTART:${toUtc(fixtures.date.atTime(fixtures.start))}
DTEND:${toUtc(fixtures.date.atTime(fixtures.start plus fixtures.duration))}
END:VEVENT
"""

    }

    private def makeICal(team:Team)(implicit context:StorageContext):String = {
      val builder = new StringBuilder("BEGIN:VCALENDAR\nVERSION:2.0\n")
      //.append("X-WR-TIMEZONE:UTC\n")
      
        def teamCompetitions(season:Season) = {
          season.competitions.flatMap(refToObject(_) match {
            case c:TeamCompetition => List(c)
            case _ => List()
          }
          )
        }
      
      def singletonCompetitions(season:Season) = {
        season.competitions.flatMap(refToObject(_) match {
          case c:SingletonCompetition => List(c)
          case _ => List()
        }
        )
      }

        val t = team
        val gap = applicationContext()


        builder.append(s"X-WR-CALNAME:${gap.leagueName} calendar for ${t.name}\n")
        for{
          c <- teamCompetitions(gap.currentSeason)
          fixtures <- c.fixtures
          f <- fixtures.fixtures if(f.home.id == team.id || f.away.id == team.id)
        }
        yield{
          builder.append(formatFixture(f, s"${fixtures.parentDescription} ${fixtures.description}"))
        }
        for{
          c <- singletonCompetitions(gap.currentSeason)
        
        }
        yield{
          builder.append(c.event.fold("")(formatEvent(_, s"${gap.leagueName} ${c.name}")))
        }
        for{
          c <- teamCompetitions(gap.currentSeason)
          fixtures <- c.fixtures if fixtures.fixtures.isEmpty
                 }
        yield{
          builder.append(formatBlankFixtures(fixtures))
        }
        for{
          e <- gap.currentSeason.calendar
        }
        yield{
          builder.append(formatEvent(e, e.description))
        }


      builder.append("END:VCALENDAR\n").toString()
    }
  
    def parts(req: HttpServletRequest) = req.getPathInfo().split("\\/").tail;
    def toUtc(dateTime:LocalDateTime) = ZonedDateTime.of(dateTime,local).format(dateFormat)
}