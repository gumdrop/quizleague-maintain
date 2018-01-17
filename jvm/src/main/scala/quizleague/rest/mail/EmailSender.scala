package quizleague.rest.mail

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
import quizleague.conversions.RefConversions.StorageContext

object EmailSender{
  
  def apply(sender: String, team: Team, text: String) = new EmailSender().sendTeamMail(sender, team, text)
 
  def apply(sender: String, recipientName: String, text: String) = new EmailSender().sendAliasMail(sender, recipientName, text)
  
  def apply(sender: String, text: String, addressees:List[String], recipientType:RecipientType = RecipientType.TO, subject:String = "") = new EmailSender().sendMail(sender, text, applicationContext(),recipientType ,addressees.map(new InternetAddress(_)),subject)
  

}

private class EmailSender {

  val LOG: Logger = Logger.getLogger(classOf[EmailSender].getName);
  implicit val context = StorageContext

  def sendTeamMail(sender: String, team: Team, text: String): Unit = {

    try {

      sendMail(sender, text, applicationContext(), RecipientType.TO, team.users.map(user => new InternetAddress(user.email)).toList)
      return

      LOG.fine("No matching addressees for any recipients");

    } catch {
      case e: Exception => LOG.log(Level.SEVERE, "Failure sending mail", e);
    }
  }
  
  def sendAliasMail(sender: String, recipientName: String, text: String): Unit = {

    try {

      val g = applicationContext()

     
        g.emailAliases.filter(_.alias == recipientName).foreach {
          alias =>
            {
              sendMail(sender, text, g, RecipientType.TO,
                List(new InternetAddress(alias.user.email)));
              return
            }

        }

      LOG.fine("No matching addressees for any recipients");

    } catch {
      case e: Exception => LOG.log(Level.SEVERE, "Failure sending mail", e);
    }
  }

  def sendMail(sender: String, text: String, g: ApplicationContext = applicationContext(), recipientType: RecipientType,
    addresses: List[Address], subject: String = ""): Unit = {

    val props = new Properties();
    val session = Session.getDefaultInstance(props, null);

    try {
      val outMessage = new MimeMessage(session);
      outMessage.addRecipients(recipientType, addresses.toArray);
      outMessage
        .setSender(new InternetAddress(g.senderEmail));
      outMessage.setReplyTo(Array(new InternetAddress(sender)))

      outMessage.setText(text);
      outMessage.setSubject(if (subject.isEmpty()) s"Sent via ${g.leagueName} : From $sender " else s"${g.leagueName} : $subject");

      LOG.fine(s"${outMessage.getFrom()(0)} to ${outMessage.getAllRecipients()(0).toString}");

      Transport.send(outMessage);
    } catch {
      case e: Exception => LOG.log(Level.SEVERE, "Failure sending mail", e);
    }

  }
}