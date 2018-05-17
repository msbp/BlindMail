package main.java.ui;

import main.java.code.*;

// Other imports
import java.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
// Exceptions
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.mail.MessagingException;

public class NewMailScreen{

  // Class variables
  private JFrame mainFrame;
  private JPanel mainPanel;
  private JPanel toPanel;
  private JLabel toLabel;
  private JTextField toTextField;
  private JPanel subjectPanel;
  private JLabel subjectLabel;
  private JTextField subjectTextField;
  private JPanel bodyPanel;
  private JLabel bodyLabel;
  private JTextArea bodyText;
  private JButton sendButton;

  /**
   * Sets up the GUI.
   */
   private void setupGUI() {
     // Main Frame code
     mainFrame = new JFrame("New Mail");
     mainFrame.setSize(800, 600);
     mainFrame.setLocationRelativeTo(null);
     mainPanel = new JPanel();

     // To field
     toLabel = new JLabel("To: ");
     toTextField = new JTextField("example@email.com", 50);
     toPanel = new JPanel();
     toPanel.add(toLabel);
     toPanel.add(toTextField);
     // Subject field
     subjectLabel = new JLabel("Subject: ");
     subjectTextField = new JTextField("Sample Subject", 50);
     subjectPanel = new JPanel();
     subjectPanel.add(subjectLabel);
     subjectPanel.add(subjectTextField);
     // Body field
     bodyLabel = new JLabel("Body: ");
     bodyText = new JTextArea(28, 60);
     bodyPanel = new JPanel();
     bodyPanel.add(bodyLabel);
     bodyPanel.add(bodyText);
     // Send button
     sendButton = new JButton("Send");
     sendButton.addActionListener(new SendEmailListener());

     mainPanel.add(toPanel);
     mainPanel.add(subjectPanel);
     mainPanel.add(bodyPanel);
     mainPanel.add(sendButton);
     mainFrame.add(mainPanel);
     mainFrame.setVisible(true);

     //Window close listener
     mainFrame.addWindowListener(new WindowAdapter(){
       public void windowClosing(WindowEvent windowEvent){
         System.out.println("- NewMailScreen was closed. Application is quitting now.");
         System.exit(0);
       }
     });
   }

   // Class for handling sending email click action
   private class SendEmailListener implements ActionListener {
     // Method that is called when the button is clicked
     public void actionPerformed(ActionEvent e) {
       SendMail test = new SendMail();
       try {
         test.sendTestEmail();
       } catch (IOException | GeneralSecurityException | MessagingException exc){
         System.out.println("There was an IOException.");
         System.out.println(exc);
       }
       System.out.println("Button pressed and test email sent.");
     }
   }

  public static void main(String args[]) {
    System.out.println("Running NewMailScreen.java.");
    NewMailScreen test = new NewMailScreen();
    test.setupGUI();
  }
}
