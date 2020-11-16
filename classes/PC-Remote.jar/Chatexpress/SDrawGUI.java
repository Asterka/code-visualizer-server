package Chatexpress;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SDrawGUI extends JFrame {
  Socket s;
  
  ServerSocket ss;
  
  public DataInputStream dis;
  
  DataOutputStream dos;
  
  public String cmessage;
  
  public String smessage;
  
  private JButton jButton1;
  
  private JButton jButton2;
  
  private JButton jButton3;
  
  private JButton jButton4;
  
  private JFrame jFrame1;
  
  private JPanel jPanel1;
  
  private JScrollPane jScrollPane1;
  
  public JTextArea jTextArea1;
  
  private JTextField jTextField1;
  
  public SDrawGUI() {
    initComponents();
    ImageIcon icon = new ImageIcon(getClass().getResource("/Startup/img/icon.png"));
    setIconImage(icon.getImage());
    try {
      this.ss = new ServerSocket(2600);
      this.s = this.ss.accept();
      System.out.println(this.s);
      this.dis = new DataInputStream(this.s.getInputStream());
      this.dos = new DataOutputStream(this.s.getOutputStream());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void initComponents() {
    this.jFrame1 = new JFrame();
    this.jPanel1 = new JPanel();
    this.jButton1 = new JButton();
    this.jButton2 = new JButton();
    this.jScrollPane1 = new JScrollPane();
    this.jTextArea1 = new JTextArea();
    this.jTextField1 = new JTextField();
    this.jButton4 = new JButton();
    this.jButton3 = new JButton();
    this.jFrame1.setDefaultCloseOperation(3);
    this.jFrame1.setTitle("DrawGUI");
    this.jFrame1.setLocation(new Point(460, 205));
    GroupLayout jFrame1Layout = new GroupLayout(this.jFrame1.getContentPane());
    this.jFrame1.getContentPane().setLayout(jFrame1Layout);
    jFrame1Layout.setHorizontalGroup(jFrame1Layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGap(0, 400, 32767));
    jFrame1Layout.setVerticalGroup(jFrame1Layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGap(0, 300, 32767));
    setDefaultCloseOperation(3);
    setTitle("Server Chatroom");
    setLocation(new Point(460, 205));
    this.jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
    this.jButton1.setBackground(new Color(204, 204, 204));
    this.jButton1.setText("TERMINATE");
    this.jButton1.setBorder(BorderFactory.createLineBorder(new Color(204, 0, 0)));
    this.jButton1.addActionListener((ActionListener)new Object(this));
    this.jButton2.setText("Send File ");
    this.jButton2.addActionListener((ActionListener)new Object(this));
    this.jTextArea1.setEditable(false);
    this.jTextArea1.setColumns(20);
    this.jTextArea1.setRows(5);
    this.jScrollPane1.setViewportView(this.jTextArea1);
    this.jTextField1.setToolTipText("Enter Yout Message Here");
    this.jTextField1.addActionListener((ActionListener)new Object(this));
    this.jButton4.setText("Send Message");
    this.jButton4.addActionListener((ActionListener)new Object(this));
    this.jButton3.setText("Receive File");
    this.jButton3.addActionListener((ActionListener)new Object(this));
    GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
    this.jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(jPanel1Layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
          .addContainerGap()
          .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.jScrollPane1)
            .addComponent(this.jButton1, GroupLayout.Alignment.TRAILING, -1, -1, 32767)
            .addGroup(jPanel1Layout.createSequentialGroup()
              .addComponent(this.jTextField1, -2, 260, -2)
              .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
              .addComponent(this.jButton4, -1, 126, 32767))
            .addGroup(jPanel1Layout.createSequentialGroup()
              .addComponent(this.jButton2, -2, 185, -2)
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
              .addComponent(this.jButton3, -2, 180, -2)))
          .addContainerGap()));
    jPanel1Layout.setVerticalGroup(jPanel1Layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
          .addContainerGap()
          .addComponent(this.jScrollPane1, -2, 283, -2)
          .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
          .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
            .addComponent(this.jTextField1)
            .addComponent(this.jButton4, -1, 42, 32767))
          .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
          .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
            .addComponent(this.jButton2, -1, 33, 32767)
            .addComponent(this.jButton3, -1, -1, 32767))
          .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
          .addComponent(this.jButton1, -1, 33, 32767)
          .addContainerGap()));
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(this.jPanel1, -1, -1, 32767));
    layout.setVerticalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(this.jPanel1, -1, -1, 32767));
    pack();
  }
  
  private void jButton1ActionPerformed(ActionEvent evt) {
    System.exit(0);
  }
  
  private void jButton2ActionPerformed(ActionEvent evt) {
    try {
      FileTransfer fileTransfer = new FileTransfer();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void jTextField1ActionPerformed(ActionEvent evt) {}
  
  private void jButton4ActionPerformed(ActionEvent evt) {
    try {
      this.smessage = this.jTextField1.getText();
      this.dos.writeUTF(this.smessage);
      this.dos.flush();
      this.jTextArea1.append("server:" + this.smessage + "\n");
      this.jTextField1.setText((String)null);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  private void jButton3ActionPerformed(ActionEvent evt) {
    try {
      new SFileReceiver();
    } catch (IOException ex) {
      ex.printStackTrace();
    } 
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
      Logger.getLogger(SDrawGUI.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (InstantiationException ex) {
      Logger.getLogger(SDrawGUI.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(SDrawGUI.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (UnsupportedLookAndFeelException ex) {
      Logger.getLogger(SDrawGUI.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
    EventQueue.invokeLater((Runnable)new Object());
  }
}
