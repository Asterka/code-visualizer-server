package Client;

import Chatexpress.CDrawInit;
import Startup.PCRemoteUI;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ClientInit extends JFrame {
  Socket sc;
  
  JOptionPane jOptionPane;
  
  DataOutputStream dOut;
  
  DataInputStream dIn;
  
  String ip;
  
  String port;
  
  String p;
  
  String user;
  
  String message;
  
  private JTextField ipfield;
  
  private JButton jButton1;
  
  private JButton jButton2;
  
  private JLabel jLabel1;
  
  private JLabel jLabel2;
  
  private JLabel jLabel3;
  
  private JLabel jLabel4;
  
  private JLabel jLabel5;
  
  private JTextField passfield;
  
  private JTextField portfield;
  
  private JLabel status;
  
  private JTextField usernamefield;
  
  public ClientInit() {
    this.sc = null;
    this.jOptionPane = new JOptionPane();
    initComponents();
    ImageIcon icon = new ImageIcon(getClass().getResource("/Startup/img/icon.png"));
    setIconImage(icon.getImage());
    setLocationRelativeTo(null);
  }
  
  public void initialize(String server_ip, int server_port) {
    try {
      System.out.println("Connecting to server......");
      this.sc = new Socket(server_ip, server_port);
      System.out.println("Connection Established.");
      ClientHandler ch = new ClientHandler(this.sc);
      ch.setVisible(true);
      new CDrawInit(server_ip);
      dispose();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  private void initComponents() {
    this.jLabel1 = new JLabel();
    this.jLabel2 = new JLabel();
    this.ipfield = new JTextField();
    this.jLabel3 = new JLabel();
    this.portfield = new JTextField();
    this.jLabel4 = new JLabel();
    this.passfield = new JTextField();
    this.jButton1 = new JButton();
    this.jButton2 = new JButton();
    this.status = new JLabel();
    this.jLabel5 = new JLabel();
    this.usernamefield = new JTextField();
    setDefaultCloseOperation(3);
    setTitle("Remote PC");
    this.jLabel1.setFont(new Font("Times New Roman", 1, 36));
    this.jLabel1.setForeground(new Color(0, 51, 255));
    this.jLabel1.setHorizontalAlignment(0);
    this.jLabel1.setText("Welcome to Remote PC Client");
    this.jLabel2.setFont(new Font("Tahoma", 1, 14));
    this.jLabel2.setForeground(new Color(255, 0, 0));
    this.jLabel2.setHorizontalAlignment(0);
    this.jLabel2.setText("IP Address:");
    this.jLabel3.setFont(new Font("Tahoma", 1, 14));
    this.jLabel3.setForeground(new Color(255, 0, 0));
    this.jLabel3.setHorizontalAlignment(0);
    this.jLabel3.setText("Port No.:");
    this.jLabel4.setFont(new Font("Tahoma", 1, 14));
    this.jLabel4.setForeground(new Color(255, 0, 0));
    this.jLabel4.setHorizontalAlignment(0);
    this.jLabel4.setText("Pass Key:");
    this.jButton1.setText("Connect");
    this.jButton1.addActionListener((ActionListener)new Object(this));
    this.jButton2.setText("Cancel");
    this.jButton2.addActionListener((ActionListener)new Object(this));
    this.status.setFont(new Font("Tahoma", 1, 14));
    this.status.setForeground(new Color(0, 0, 255));
    this.status.setHorizontalAlignment(0);
    this.status.setText("Status: Disconnected!");
    this.jLabel5.setFont(new Font("Tahoma", 1, 14));
    this.jLabel5.setForeground(new Color(255, 0, 0));
    this.jLabel5.setHorizontalAlignment(0);
    this.jLabel5.setText("UserName:");
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.status, -1, -1, 32767)
            .addComponent(this.jLabel1, -1, 499, 32767)
            .addGroup(layout.createSequentialGroup()
              .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                .addComponent(this.jLabel5, GroupLayout.Alignment.LEADING, -1, -1, 32767)
                .addComponent(this.jButton1, GroupLayout.Alignment.LEADING, -1, -1, 32767)
                .addComponent(this.jLabel4, GroupLayout.Alignment.LEADING, -1, -1, 32767)
                .addComponent(this.jLabel3, GroupLayout.Alignment.LEADING, -1, -1, 32767)
                .addComponent(this.jLabel2, GroupLayout.Alignment.LEADING, -1, 230, 32767))
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 39, 32767)
              .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                .addComponent(this.ipfield)
                .addComponent(this.portfield)
                .addComponent(this.passfield)
                .addComponent(this.jButton2, -1, 230, 32767)
                .addComponent(this.usernamefield))))
          .addContainerGap()));
    layout.setVerticalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addComponent(this.jLabel1, -2, 59, -2)
          .addGap(18, 18, 18)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(this.jLabel2)
            .addComponent(this.ipfield, -2, -1, -2))
          .addGap(18, 18, 18)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(this.jLabel3)
            .addComponent(this.portfield, -2, -1, -2))
          .addGap(18, 18, 18)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(this.jLabel4)
            .addComponent(this.passfield, -2, -1, -2))
          .addGap(18, 18, 18)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(this.jLabel5)
            .addComponent(this.usernamefield, -2, -1, -2))
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 14, 32767)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(this.jButton1)
            .addComponent(this.jButton2))
          .addGap(18, 18, 18)
          .addComponent(this.status)
          .addContainerGap()));
    pack();
  }
  
  private void jButton2ActionPerformed(ActionEvent evt) {
    PCRemoteUI pc = new PCRemoteUI();
    pc.setVisible(true);
    dispose();
  }
  
  private void jButton1ActionPerformed(ActionEvent evt) {
    this.ip = this.ipfield.getText().trim();
    this.port = this.portfield.getText().trim();
    this.p = this.passfield.getText().trim();
    this.user = this.usernamefield.getText().trim();
    if (this.ip.isEmpty() || this.port.isEmpty() || this.p.isEmpty() || this.user.isEmpty()) {
      this.message = "Please enter all values";
    } else if (authenticate("connect")) {
      initialize(this.ipfield.getText(), Integer.parseInt(this.portfield.getText()));
      this.message = "Connected!";
    } else {
      this.message = "Password entered is incorrect.";
    } 
    JOptionPane.showMessageDialog(this, this.message);
  }
  
  boolean authenticate(String btn) {
    boolean result = false;
    try {
      this.sc = new Socket(this.ipfield.getText(), Integer.parseInt(this.portfield.getText()));
      String s = this.passfield.getText();
      this.dOut = new DataOutputStream(this.sc.getOutputStream());
      this.dIn = new DataInputStream(this.sc.getInputStream());
      if (btn.equals("connect")) {
        this.dOut.writeByte(1);
        this.dOut.writeUTF(s);
        this.dOut.flush();
      } 
      byte type = this.dIn.readByte();
      if (type == 1) {
        result = true;
      } else if (type == 2) {
        result = false;
      } 
      this.dIn.close();
      this.dOut.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    } 
    return result;
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
      Logger.getLogger(ClientInit.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (InstantiationException ex) {
      Logger.getLogger(ClientInit.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(ClientInit.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (UnsupportedLookAndFeelException ex) {
      Logger.getLogger(ClientInit.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
    EventQueue.invokeLater((Runnable)new Object());
  }
}
