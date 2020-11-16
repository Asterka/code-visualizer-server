package Client;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ClientHandler extends JFrame {
  Socket cSocket;
  
  private JPanel cPanel;
  
  public ClientHandler(Socket cSocket) {
    initComponents();
    ImageIcon icon = new ImageIcon(getClass().getResource("/Startup/img/icon.png"));
    setIconImage(icon.getImage());
    this.cSocket = cSocket;
    run();
  }
  
  public void run() {
    Rectangle clientScreenDim = null;
    System.out.println("In Thread");
    ObjectInputStream ois = null;
    try {
      ois = new ObjectInputStream(this.cSocket.getInputStream());
      clientScreenDim = (Rectangle)ois.readObject();
    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    } 
    new ClientScreenReciever(ois, this.cPanel);
    new ClientCommandsSender(this.cSocket, this.cPanel, clientScreenDim);
  }
  
  private void initComponents() {
    this.cPanel = new JPanel();
    setDefaultCloseOperation(3);
    setTitle("Remote PC");
    GroupLayout cPanelLayout = new GroupLayout(this.cPanel);
    this.cPanel.setLayout(cPanelLayout);
    cPanelLayout.setHorizontalGroup(cPanelLayout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGap(0, 801, 32767));
    cPanelLayout.setVerticalGroup(cPanelLayout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGap(0, 524, 32767));
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(this.cPanel, -1, -1, 32767));
    layout.setVerticalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(this.cPanel, -1, -1, 32767));
    pack();
  }
  
  public static void main(String[] args) {
    try {
      for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        } 
      } 
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (InstantiationException ex) {
      Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (UnsupportedLookAndFeelException ex) {
      Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
    EventQueue.invokeLater((Runnable)new Object());
  }
}
