/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.utility;

import com.google.common.base.Charsets;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.context.FacesContext;
import javax.mail.Address;
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
import javax.servlet.ServletContext;

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

        try {

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

    public void send(String from, String to, String subject, String body, ByteArrayOutputStream pdfAttachment) {
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

    /*A message with HTML, text and an a attachment can be viewed hierarchically like this:
     mainMultipart (content for message, subType="related")
     ->htmlAndTextBodyPart (bodyPart1 for mainMultipart)
     ->htmlAndTextMultipart (content for htmlAndTextBodyPart, subType="alternative")
     ->htmlBodyPart (bodyPart1 for htmlAndTextMultipart)
     ->html (content for htmlBodyPart)
     ->textBodyPart (bodyPart2 for the htmlAndTextMultipart)
     ->text (content for textBodyPart)
     ->fileBodyPart1 (bodyPart2 for the mainMultipart)
     ->FileDataHandler (content for fileBodyPart1 )
     */
    public void send(String from, String to, String subject, String text, String html, ByteArrayOutputStream pdfAttachment) {
        try {
            //Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setSentDate(new Date());

            MimeMultipart mainMultipart = new MimeMultipart("related");
            MimeMultipart htmlAndTextMultipart = new MimeMultipart("alternative");

            //Text
            if (text != null) {
                MimeBodyPart textBodyPart = new MimeBodyPart();
                textBodyPart.setText(text);
                htmlAndTextMultipart.addBodyPart(textBodyPart);
            }

            //HTML
            if (html != null) {
                MimeBodyPart htmlBodyPart = new MimeBodyPart();
                htmlBodyPart.setContent(html, "text/html");
                htmlAndTextMultipart.addBodyPart(htmlBodyPart);
            }

            MimeBodyPart htmlAndTextBodyPart = new MimeBodyPart();
            htmlAndTextBodyPart.setContent(htmlAndTextMultipart);
            mainMultipart.addBodyPart(htmlAndTextBodyPart);

            //PDF Attachment
            if (pdfAttachment != null) {
                MimeBodyPart pdfBodyPart = new MimeBodyPart();
                byte[] bytes = pdfAttachment.toByteArray();
                DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
                pdfBodyPart.setDataHandler(new DataHandler(dataSource));
                pdfBodyPart.setFileName("plano.pdf");
                mainMultipart.addBodyPart(pdfBodyPart);
            }

            message.setContent(mainMultipart);

            Transport.send(message);

        } catch (MessagingException ex) {
            Logger.getLogger(EMailSSL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String readTemplateToString(String relativePath) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String absolutePath = servletContext.getRealPath(relativePath);
        byte[] encoded = Files.readAllBytes(Paths.get(absolutePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    public String fillTemplate(String template, String tag[], String content[]) {
        String filledTemplate = template;
        for (int i = 0; i < tag.length; i++) {
            filledTemplate = filledTemplate.replace(tag[i], content[i]);
        }
        System.out.println("TEMPLATE:/n" + filledTemplate);
        return filledTemplate;
    }

    public String[] getDefaultTags() {
        String[] tags = {"#title", "#subtitle#", "#text#", "#footer#"};
        return tags;
    }

}
