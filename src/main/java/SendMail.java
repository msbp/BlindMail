// Google imports
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

// javax.mail package imports
import javax.mail.Session;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;

// Other imports
import java.util.Properties;
import java.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;


public class SendMail {

  /**
   * Creates a Mime formatted email.
   * @param to Recipient address.
   * @param from From address.
   * @param subject Subject of email.
   * @param body Body of email.
   * @return A MimeMessage object carrying the parameters given.
   * @throws MessagingException
   */
  private static MimeMessage createMimeEmail(String to, String from, String subject, String body) throws MessagingException {
    Properties p = new Properties();
    Session s = Session.getDefaultInstance(p, null);

    MimeMessage email = new MimeMessage(s);

    email.setFrom(new InternetAddress(from));
    email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
    email.setSubject(subject);
    email.setText(body);
    return email;
  }

  /**
   * Encodes Mime email to Base64 and creates a Message object.
   * @param email MimeMessage email created.
   * @return A Message object (from google) with encoded email.
   * @throws MessagingException
   * @throws IOException
   */
  private static Message encodeEmail(MimeMessage email) throws MessagingException, IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    email.writeTo(buffer);
    byte[] bytes = buffer.toByteArray();
    String encodedEmail = Base64.getEncoder().encodeToString(bytes);
    Message message = new Message();
    message.setRaw(encodedEmail);
    return message;
  }

  /**
   * Creates a Mime formatted email.
   * @param service An authenticated service.
   * @param userId User's email address. "me" can be used to refer to authenticated user.
   * @param emailContent email to be sent.
   * @return The message sent. This can be used to get more information on the message sent.
   * @throws MessagingException
   * @throws IOException
   */
   private static Message sendMessage(Gmail service, String userId, MimeMessage emailContent) throws MessagingException, IOException {
      Message message = encodeEmail(emailContent);
      message = service.users().messages().send(userId, message).execute();

      System.out.println("Message id: " + message.getId());
      System.out.println(message.toPrettyString());
      return message;
   }

  public static void main (String args[]) throws IOException, GeneralSecurityException, MessagingException{
    System.out.println("Running SendMail.java.");

    // Testing sending email service
    //
    System.out.println("Start of test.");
    LogIn test = new LogIn();
    test.buildService();
    MimeMessage m = createMimeEmail("mau.browne@hotmail.com", "mauricio.browne@gmail.com", "Testing service", "This is the body of the message! :)");
    sendMessage(test.getService(), "me", m);
    System.out.println("End of test.");
    //
    // End of test
  }
}
