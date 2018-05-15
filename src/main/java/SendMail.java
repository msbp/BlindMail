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

// Other imports
import javax.mail.Session;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;

import java.util.Properties;
import java.util.Base64;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.io.File;
import java.lang.SecurityException;

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
   * @return A Message object (from google) with encoded email
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


  public static void main (String args[]){
    System.out.println("Running SendMail.java.");
  }
}
