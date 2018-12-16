package com.shopstantlyinventory.helpers;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Properties;

/**
 * Credits to: https://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/ AND
 * https://kodejava.org/how-do-i-send-an-email-with-attachment/
 * for the SSL Snippet.
 */
public class MailHelper {
    private static final String MAIL_HOST = "smtp.gmail.com", USERNAME = "hydragroup123@gmail.com", PASSWORD = "hydra123", FROM = "hydragroup123@gmail.com";
    private static final Integer MAIL_PORT = 465;

    /**
     * Send an email with the passed content to the recipient
     * by using the predefined hydragroup@gmail.com address as the sender.
     *
     * @param to      email address from the recipient
     * @param subject subject line of the email
     * @param text    content/body of the mail
     * @param attachment
     * @return true/false if the send was successful
     */
    public static boolean SendMail(String to, String subject, String text, ByteArrayOutputStream attachment, String attachmentName) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", MAIL_HOST);
        properties.put("mail.smtp.socketFactory.port", MAIL_PORT);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", MAIL_PORT);

        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(FROM));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            // Set the email msg text.
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText(getMailText(subject, text), "UTF-8", "html");

            // Set the email attachment file
            DataSource aAttachment = new ByteArrayDataSource(attachment.toByteArray(), "application/octet-stream");

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.setDataHandler(new DataHandler(aAttachment));
            attachmentPart.setFileName(attachmentName);

            // Create Multipart E-Mail.
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messagePart);
            multipart.addBodyPart(attachmentPart);

            msg.setContent(multipart);

            // Send the msg. Don't forget to set the username and password
            // to authenticate to the mail server.
            Transport.send(msg);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Creates the mail content based on our predefined mail theme
     *
     * @param subject the mails subject
     * @param text    the mails text
     * @return html document of the email
     */
    private static String getMailText(String subject, String text) {
        return "\n" +
                "    <!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "    <title>" + subject + "</title>\n" +
                "</head>\n" +
                "<body paddingwidth=\"0\" paddingheight=\"0\" bgcolor=\"#d1d3d4\"  style=\"padding-top: 0; padding-bottom: 0; padding-top: 0; padding-bottom: 0; background-repeat: repeat; width: 100% !important; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-font-smoothing: antialiased;\" offset=\"0\" toppadding=\"0\" leftpadding=\"0\">\n" +
                "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "    <tbody>\n" +
                "    <tr>\n" +
                "        <td>\n" +
                "            <table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" bgcolor=\"#ffffff\" style=\"font-family:helvetica, sans-serif;\" class=\"MainContainer\">\n" +
                "                <tbody>\n" +
                "                <tr>\n" +
                "                    <td width=\"40\">&nbsp;</td>\n" +
                "                    <td width=\"520\">\n" +
                "                        <!-- =============== START HEADER =============== -->\n" +
                "                        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                            <tbody>\n" +
                "                            <tr>\n" +
                "                                <td height=\"15\"></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                    <tbody>\n" +
                "                                    <tr>\n" +
                "                                        <td valign=\"top\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                            <tbody>\n" +
                "                                            <tr>\n" +
                "                                                <td valign=\"top\" align=\"center\" width=\"250\">\n" +
                "                                                    <img src=\"https://raw.githubusercontent.com/flywheelsports/hydra/HEAD/hydra.png\" alt=\"Hydra Logo\" title=\"Logo\" width=\"250\" height=\"100\" data-max-width=\"100\"></td>\n" +
                "                                            </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                    </tbody>\n" +
                "                                </table></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td height='15'></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td ><hr style='height:1px;background:#DDDDDD;border:none;'></td>\n" +
                "                            </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                        <!-- =============== END HEADER =============== -->\n" +
                "                        <!-- =============== START BODY =============== -->\n" +
                "\n" +
                "                        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                            <tbody>\n" +
                "                            <tr>\n" +
                "                                <td height=\"40\"></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td>\n" +
                "                                    <strong>" + subject + "</strong>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td height=\"20\"></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td>\n" +
                text +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td height=\"40\"></td>\n" +
                "                            </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "\n" +
                "                    </td>\n" +
                "                    <td width=\"40\">&nbsp;</td>\n" +
                "                </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>\n";
    }
}
