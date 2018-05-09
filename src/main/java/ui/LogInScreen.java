import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LogInScreen {

  //Class variables
  private JFrame mainFrame;
  private JButton loginButton;

  // Setting up the Frame and its contents
  private void setupGUI(){
      // Main Frame code
      mainFrame = new JFrame("Log in");
      mainFrame.setSize(800,600);
      mainFrame.setLocationRelativeTo(null);

      // Button code
      loginButton = new JButton("Login");
      loginButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
          System.out.println("Button was pressed.");
        }
      });
      mainFrame.add(loginButton, BorderLayout.CENTER);

      //Window close listener
      mainFrame.addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent windowEvent){
          System.out.println("- Login was closed. Application is quitting now.");
          System.exit(0);
        }
      });
  }

  public void displayGUI(){
    mainFrame.setVisible(true);
  }

  public static void main (String args[]){
    LogInScreen test = new LogInScreen();
    test.setupGUI();
    test.displayGUI();
  }

}
