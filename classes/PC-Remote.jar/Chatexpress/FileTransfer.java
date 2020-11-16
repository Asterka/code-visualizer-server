package Chatexpress;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class FileTransfer extends JFrame {
  Socket s;
  
  DataOutputStream dout;
  
  String s1 = new String();
  
  File f;
  
  String s2 = "";
  
  ServerSocket ss;
  
  JFileChooser jfc;
  
  private JButton jButton1;
  
  private JButton jButton2;
  
  private JFrame jFrame1;
  
  private JPanel jPanel1;
  
  private JScrollPane jScrollPane1;
  
  private JTextArea jTextArea1;
  
  public FileTransfer() {
    initComponents();
    ImageIcon icon = new ImageIcon(getClass().getResource("/Startup/img/icon.png"));
    setIconImage(icon.getImage());
    try {
      this.ss = new ServerSocket(2700);
      JOptionPane.showMessageDialog(new JFrame(), "waiting for client to accept request");
      this.s = this.ss.accept();
      this.dout = new DataOutputStream(this.s.getOutputStream());
      DataInputStream dis = new DataInputStream(this.s.getInputStream());
      int i = dis.readInt();
      if (i == 7)
        JOptionPane.showMessageDialog(new JFrame(), "Client rejected to accept file"); 
      if (i == 8)
        setVisible(true); 
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
    GroupLayout jFrame1Layout = new GroupLayout(this.jFrame1.getContentPane());
    this.jFrame1.getContentPane().setLayout(jFrame1Layout);
    jFrame1Layout.setHorizontalGroup(jFrame1Layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGap(0, 400, 32767));
    jFrame1Layout.setVerticalGroup(jFrame1Layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGap(0, 300, 32767));
    setDefaultCloseOperation(2);
    setTitle("File Transfer");
    setLocation(new Point(525, 280));
    this.jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
    this.jButton1.setText("Choose File");
    this.jButton1.addActionListener((ActionListener)new Object(this));
    this.jButton2.setText("Send");
    this.jButton2.addActionListener((ActionListener)new Object(this));
    this.jTextArea1.setColumns(8);
    this.jTextArea1.setRows(3);
    this.jTextArea1.setTabSize(5);
    this.jScrollPane1.setViewportView(this.jTextArea1);
    GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
    this.jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(jPanel1Layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
          .addContainerGap()
          .addComponent(this.jButton1)
          .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
          .addComponent(this.jScrollPane1, -1, 169, 32767)
          .addContainerGap())
        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
          .addContainerGap(-1, 32767)
          .addComponent(this.jButton2, -2, 96, -2)
          .addGap(45, 45, 45)));
    jPanel1Layout.setVerticalGroup(jPanel1Layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
          .addGap(13, 13, 13)
          .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.jButton1, -2, 36, -2)
            .addComponent(this.jScrollPane1, -2, 89, -2))
          .addGap(18, 18, 18)
          .addComponent(this.jButton2)
          .addContainerGap(23, 32767)));
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(this.jPanel1, GroupLayout.Alignment.TRAILING, -1, -1, 32767));
    layout.setVerticalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(this.jPanel1, -1, -1, 32767));
    pack();
  }
  
  private void jButton1ActionPerformed(ActionEvent evt) {
    JFileChooser jfc = new JFileChooser();
    int x = jfc.showOpenDialog(null);
    if (x == 0) {
      this.f = jfc.getSelectedFile();
      String path = this.f.getPath();
      this.s1 = this.f.getName();
      this.jTextArea1.setText(path);
    } 
  }
  
  private void jButton2ActionPerformed(ActionEvent evt) {
    fileTransfer(this.s1);
    dispose();
  }
  
  public void fileTransfer(String s1) {
    try {
      this.dout.writeUTF(s1);
      this.dout.flush();
      this.s2 = this.f.getAbsolutePath();
      byte[] bytes = new byte[8192];
      InputStream in = new FileInputStream(this.f);
      OutputStream out = this.s.getOutputStream();
      int count;
      while ((count = in.read(bytes)) > 0)
        out.write(bytes, 0, count); 
      out.close();
      in.close();
      this.s.close();
      JOptionPane.showMessageDialog(new JFrame(), "File Sent");
    } catch (HeadlessException|java.io.IOException e) {
      e.printStackTrace();
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
      Logger.getLogger(FileTransfer.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (InstantiationException ex) {
      Logger.getLogger(FileTransfer.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(FileTransfer.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (UnsupportedLookAndFeelException ex) {
      Logger.getLogger(FileTransfer.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
    EventQueue.invokeLater((Runnable)new Object());
  }
}
