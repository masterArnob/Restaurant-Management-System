package restaurant.management.system;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;

public class sendEmail {

    public void email(String Username, String Email, String Text) {
        final String senderEmail = "javatesbot1@gmail.com";
        final String senderPassword = "zfgcaacakmiguvox";

        String recipientEmail = Email;

        String subject = "Welcome to Arnob's Project - TestBot2";

        String message = Text;
                //"Dear " + Username + ",\n\nI would like to extend a warm welcome to Arnob's project on behalf of Testbot2. My name is TestBot2, and I am here to assist you in any way I can.\n\nThank you\n\nBest regards,\nTestBot2";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {

            Message msg = new MimeMessage(session);
            // Set the sender, recipient, subject, and message text
            msg.setFrom(new InternetAddress(senderEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            msg.setSubject(subject);
            msg.setText(message);
            // Send the message
            javax.mail.Transport.send(msg);
            System.out.println("Email sent successfully.");
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }
}
