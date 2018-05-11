/*
* Most of the code here was adapted from google's Gmail API documentation
*/

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
import com.google.api.services.gmail.GmailScopes;

// Other imports
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class LogIn {

  private static final String APPLICATION_NAME = "BlindMail";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String CREDENTIALS_FOLDER = "Credentials"; // Used to store credentials

  private static final List<String> SCOPES = Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM); //Scope to read, send, delete and manage emails
  private static final String CLIENT_SECRET_DIR = "client_secret.json";

  private static Credential c = null;

  /**
   * Creates an authorized Credential object.
   * @param HTTP_TRANSPORT The network HTTP Transport.
   * @return An authorized Credential object.
   * @throws IOException If there is no client_secret.
   */
  private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
      // Load client secrets.
      InputStream in = LogIn.class.getResourceAsStream(CLIENT_SECRET_DIR);
      GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

      // Build flow and trigger user authorization request.
      GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
              HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
              .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_FOLDER)))
              .setAccessType("offline")
              .build();
      c = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
      return c;
  }

  public static void main (String args[]){
    System.out.println("LogIn.java has been compiled and ran.");
  }

}
