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



  private void setupGUI(){
      mainFrame = new JFrame("Log in");
      mainFrame.setSize(800,600);
      mainFrame.setLocationRelativeTo(null);

      loginButton = new JButton("Login");
      mainFrame.add(loginButton, BorderLayout.CENTER);


      mainFrame.setVisible(true);
  }

  public static void main (String args[]){
    LogInScreen test = new LogInScreen();
    test.setupGUI();
  }

}
