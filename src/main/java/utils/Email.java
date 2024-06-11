package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Email {
    public static void sendEmail(String to, String subject, String content){
        final String from = "hoangthhek17@gmail.com";
        final String password = "ghvl eyfe jmiq lwzt";

        // Properties :
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // create Authenticator
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };

        //Phien lam viec
        Session session = Session.getInstance(properties, authenticator);

        //Sending email
        MimeMessage message = new MimeMessage(session);

        try {
            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.setFrom(from);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            message.setSubject(subject, "UTF-8");
            message.setSentDate(new Date());
            //message.setReplyTo(InternetAddress.parse(from, false));
            message.setText(content, "UTF-8");
            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println("SendEmail " + e);
        }
    }
    public static void main(String[] args) {
        sendEmail("tuannnahe176267@fpt.edu.vn", "Đêm qua em tuyệt lắm", "abcxyz 696969");
    }
}
