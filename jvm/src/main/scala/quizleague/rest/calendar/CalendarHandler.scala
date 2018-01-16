package quizleague.rest.calendar

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import quizleague.data.Storage._
import quizleague.data._
import quizleague.domain._
import java.text.SimpleDateFormat
import quizleague.util.json.codecs.DomainCodecs._
import java.util.Date
import quizleague.conversions.RefConversions._
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import quizleague.domain.TeamCompetition
import quizleague.domain.SingletonCompetition

class CalendarHandler extends HttpServlet{
  val dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
  override def doPost(req: HttpServletRequest, resp: HttpServletResponse) = {}  
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) = {
      val bits = parts(req)
      val head = bits.head
      val id = bits.tail.head
      
      implicit val context = StorageContext
      
      
      
      val contents = head match{
        
        case "team" => makeICal(load[Team](id))
        case _ => ""
        
      }
      
      resp.setContentType("text/calendar")
      resp.getWriter.append(contents)
      resp.getWriter.flush()
    }
    
    private def formatEvent(event:BaseEvent, text:String):String = {
      val now = LocalDateTime.now().format(dateFormat)
      val uidPart = text.replaceAll("\\s", "") 
      val address = event.venue.address.replaceAll("\\n\\r", ",").replaceAll("\\n", ",").replaceAll("\\r", ",")
      s"""
BEGIN:VEVENT
DTSTAMP:$now
UID:${event.date}.$uidPart.chilternquizleague.uk
DESCRIPTION:$text
SUMMARY:$text
DTSTART:${event.date.atTime(event.time).format(dateFormat)}
DTEND:${event.date.atTime(event.time plus event.duration).format(dateFormat)}
LOCATION:${event.venue.name},$address
END:VEVENT
"""

    }
    private def formatFixture(fixture:Fixture, description:String) = {
      
      val text = s"${fixture.home.shortName} - ${fixture.away.shortName} : $description"
      
      val now = LocalDateTime.now().format(dateFormat)
      val uidPart = fixture.home.shortName.replaceAll("\\s", "") 
      val address = fixture.venue.address.replaceAll("\\n\\r", ",").replaceAll("\\n", ",").replaceAll("\\r", ",")
      s"""
BEGIN:VEVENT
DTSTAMP:$now
UID:${fixture.date}.$uidPart.chilternquizleague.uk
DESCRIPTION:$text
SUMMARY:$text
DTSTART:${fixture.date.atTime(fixture.time).format(dateFormat)}
DTEND:${fixture.date.atTime(fixture.time plus fixture.duration).format(dateFormat)}
LOCATION:${fixture.venue.name},$address
END:VEVENT
"""

    }
    private def formatBlankFixtures(fixtures:Fixtures) = {
      
      val now = LocalDateTime.now.format(dateFormat)
      val uidPart = fixtures.description.replaceAll("\\s", "") 
      s"""
BEGIN:VEVENT
DTSTAMP:$now
UID:${fixtures.date}.$uidPart.chilternquizleague.uk
DESCRIPTION:${fixtures.description}
SUMMARY:${fixtures.description}
DTSTART:${fixtures.date.atTime(fixtures.start).format(dateFormat)}
DTEND:${fixtures.date.atTime(fixtures.start plus fixtures.duration).format(dateFormat)}
END:VEVENT
"""

    }

    private def makeICal(team:Team):String = {
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
          builder.append(formatFixture(f, fixtures.description))
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
}