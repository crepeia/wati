/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.utility;

import java.io.ByteArrayOutputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 *
 * @author hedersb
 */
public class EMailSSL {

    private Properties props;
    private Session session;
    private Authenticator authenticator;

    public EMailSSL() {

        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        
        this.authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("watiufjf", "wati1235");
            }
        };

        session = Session.getInstance(props, this.authenticator);

    }

    public void send(String from, String to, String subject, String body) {

        try{
            
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);
            
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
    
    public void sendAttachment (String from, String to, String subject, String body, ByteArrayOutputStream pdfAttachment){
        try {
            
            
                //text
               MimeBodyPart textBodyPart = new MimeBodyPart();
               textBodyPart.setText(body);

               //pdf
               byte[] bytes = pdfAttachment.toByteArray();
               DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
               MimeBodyPart pdfBodyPart = new MimeBodyPart();
               pdfBodyPart.setDataHandler(new DataHandler(dataSource));
               pdfBodyPart.setFileName("plano.pdf");

               MimeMultipart mimeMultipart = new MimeMultipart();
               mimeMultipart.addBodyPart(textBodyPart);
               mimeMultipart.addBodyPart(pdfBodyPart);

               MimeMessage message = new MimeMessage(session);
               message.setSender(new InternetAddress(from));
               message.setRecipients(Message.RecipientType.TO,
                           InternetAddress.parse(to));
               message.setSubject(subject);
               message.setContent(mimeMultipart);

               Transport.send(message);
           

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
