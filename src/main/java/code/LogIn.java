package main.java.code;
/*
* Some of the code here was adapted from google's Gmail API documentation
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
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;

import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;

// Other imports
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.io.File;
import java.lang.SecurityException;

public class LogIn {

  private static final String APPLICATION_NAME = "BlindMail";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String CREDENTIALS_FOLDER = "Credentials"; // Used to store credentials

  private static final List<String> SCOPES = Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM); //Scope to read, send, delete and manage emails
  private static final String CLIENT_SECRET_DIR = "client_secret.json";

  private static Credential c = null;
  private static Gmail service = null;

  /**
   * Creates an authorized Credential object.
   * @param HTTP_TRANSPORT The network HTTP Transport.
   * @return An authorized Credential object.
   * @throws IOException If there is no client_secret.
   */
  private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
      // Load client secrets.
      InputStream in = LogIn.class.getClassLoader().getResourceAsStream(CLIENT_SECRET_DIR);
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

  /**
   * Deletes the credential file if it exists.
   * @return True if deleted or does not exist, false otherwise.
   */
   private static boolean deleteCredentials(){
     String credentialsPath = "./Credentials/StoredCredential";
     File f = new File(credentialsPath);
     if (!f.exists()){
       System.out.println("Attempting to delete stored credential.\n File removed.");
       return true;
     }
     try {
       return f.delete();
     } catch (SecurityException e){
       System.out.println("Access denied when trying to delete stored credentials.");
       return false;
     }
   }

  /**
   * Builds a new authorized API client service.
   * @return A newly built authorized API client service.
   * @throws IOException
   * @throws GeneralSecurityException
   */
   public static Gmail buildService() throws IOException, GeneralSecurityException {
     final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
     service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
     return service;
   }

   /**
    * Logs user out of account if they were authorized. This happens by deleting the stored credentials.
    * @return True if it succeeds, false otherwise.
    */
    public static boolean logoutUser(){
      return deleteCredentials();
    }

  /**
   * Getter for the Credential object.
   * @return An authorized Credential object.
   */
   public static Credential getC(){
     if (c == null){
       System.out.println("Credential object was not initialized.");
       return null;
     }
     return c;
   }

   /**
    * Getter for the Gmail service object.
    * @return An authorized API client service.
    */
    public static Gmail getService(){
      if (service == null){
        System.out.println("Gmail service object was not initialized.");
        return null;
      }
      return service;
    }

  public static void main (String args[]) throws IOException, GeneralSecurityException {
    System.out.println("LogIn.java has been compiled and ran.");

    // Beginning of test code
    buildService();
    String user = "me";
    ListLabelsResponse listResponse = service.users().labels().list(user).execute();
    List<Label> labels = listResponse.getLabels();
    System.out.println("Labels:");
    for (Label label : labels){
      System.out.printf("- %s\n", label.getName());
    }
    // End of test code

  }
}
