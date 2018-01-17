package quizleague.rest.mail

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.Properties
import javax.mail.Session
import javax.mail.internet.MimeMessage
import javax.mail.internet.InternetAddress
import quizleague.domain._
import javax.mail.Address
import java.util.logging.Logger
import java.util.logging.Level
import javax.mail.Message.RecipientType
import javax.mail.Transport
import quizleague.data.Storage._
import quizleague.conversions.RefConversions._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.data._


class MailHandler extends HttpServlet {

  val LOG: Logger = Logger.getLogger(classOf[MailHandler].getName());

  override def doPost(req: HttpServletRequest, resp: HttpServletResponse): Unit = {

    val props: Properties = new Properties;
    val session: Session = Session.getDefaultInstance(props, null);

    try {
      val message = new MimeMessage(session,
        req.getInputStream());

      val globaldata = applicationContext()
      val recipientParts = req.getPathInfo().replaceFirst("/", "").split("@")

      val recipientName = recipientParts(0);

      globaldata.emailAliases.filter(_.alias == recipientName).foreach { alias => sendMail(message, globaldata, new InternetAddress(alias.user.email)); return }

      list[Team].filter(_.emailName == recipientName).foreach { team:Team =>

        val addresses: Array[Address] = team.users.map { a => new InternetAddress(a.email) }.toArray

        sendMail(message, globaldata, addresses: _*)

        return

      }

      LOG.fine("No matching addressees for any recipients");

    } catch {
      case e: Exception => LOG.log(Level.SEVERE, "Failure recieving mail", e);
    }

  }


  def sendMail(message: MimeMessage, globaldata: ApplicationContext,
    addresses: Address*): Unit =
    {

      try {

        val from = message.getFrom
        
        message.setSender(new InternetAddress(globaldata.senderEmail))
        
        message.setReplyTo(from)
        
        message.setRecipients(RecipientType.TO, addresses.toArray);

        message.setSubject(s"Sent via ${globaldata.leagueName} : ${message.getSubject}");

        LOG.fine(s"${message.getFrom()(0)} to ${message.getAllRecipients()(0).toString}");

        Transport.send(message);
      } catch {

        case e: Exception => {
          LOG.log(Level.SEVERE, "Failure sending mail", e);

          val session = Session.getDefaultInstance(new Properties(), null);

          val notification = new MimeMessage(session);
          notification.addRecipient(RecipientType.TO, message.getFrom()(0));
          notification.setSender(new InternetAddress(globaldata.senderEmail));
          notification.setSubject(s"${globaldata.leagueName} : Message delivery failed");
          notification.setText("Message delivery failed, probably due to an attachment.\nThis mail service does not allow attachments.  Try resending as text only.");

          Transport.send(notification);
        }
      }
    }
}


