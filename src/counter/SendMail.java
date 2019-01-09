import counter.ReadProperty;
import java.net.PasswordAuthentication;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
    String host, port, emailid,username, password;
    Properties props = System.getProperties();
    Session l_session = null;

    public SendMail() {
        
        emailid = "vikash100dhn@yahoo.in";
        username = "vikash100dhn@yahoo.in";
        password = "infosys@123";

        emailSettings();
        createSession();
        sendMessage(ReadProperty.getProperty("fromemail"), ReadProperty.getProperty("toEmail"),ReadProperty.getProperty("subject"),ReadProperty.getProperty("emailbody"));
    }

    public void emailSettings() {
        props.put("mail.smtp.host",  ReadProperty.getProperty("host"));
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "false");
        props.put("mail.smtp.port", ReadProperty.getProperty("port"));
        props.put("mail.smtp.ssl.enable","true");
    }

    public void createSession() {
        l_session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(ReadProperty.getProperty("username"), ReadProperty.getProperty("password"));
                    }
                });
        l_session.setDebug(true);
    }

    public boolean sendMessage(String emailFromUser, String toEmail, String subject, String messageText) {
        try {
            MimeMessage message = new MimeMessage(l_session);
            emailid = emailFromUser;
            message.setFrom(new InternetAddress(this.emailid));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.addRecipient(Message.RecipientType.BCC, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setContent(messageText, "text/html");

            Transport.send(message);
            System.out.println("An email has been sent");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public static void main(String args[]) {
    	SendMail s = new SendMail();
    }
}