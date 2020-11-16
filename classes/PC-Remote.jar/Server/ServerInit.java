package Server;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public final class ServerInit extends JFrame {
  int port;
  
  private int pass;
  
  ServerHandler handler;
  
  private JTextArea ipfield;
  
  private JLabel jLabel1;
  
  private JLabel jLabel2;
  
  private JLabel jLabel3;
  
  private JLabel jLabel4;
  
  private JLabel jLabel5;
  
  private JLabel jLabel6;
  
  private JLabel jLabel7;
  
  private JScrollPane jScrollPane1;
  
  private JLabel passfield;
  
  private JTextField portfield;
  
  public ServerInit() {
    initComponents();
    ImageIcon icon = new ImageIcon(getClass().getResource("/Startup/img/icon.png"));
    setIconImage(icon.getImage());
    setLocationRelativeTo(null);
    this.pass = 1000 + (int)(Math.random() * 9000.0D);
    String ip = new String();
    this.port = 2500;
    try {
      ip = getLocalHostAddresses();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    this.ipfield.setText(ip);
    this.portfield.setText("2500");
    this.passfield.setText(String.valueOf(this.pass));
    this.handler = new ServerHandler(this.port, this.pass);
    this.portfield.setEditable(false);
  }
  
  public int getPort() {
    return this.port;
  }
  
  public String getLocalHostAddresses() {
    ArrayList<String> addresses = new ArrayList<>();
    System.setProperty("java.net.preferIPv4Stack", "true");
    try {
      Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
      while (e.hasMoreElements()) {
        NetworkInterface iface = e.nextElement();
        if (iface.isLoopback() || !iface.isUp() || iface.isVirtual() || iface.isPointToPoint())
          continue; 
        Enumeration<InetAddress> e2 = iface.getInetAddresses();
        for (InetAddress address : Collections.<InetAddress>list(iface.getInetAddresses())) {
          if (address instanceof java.net.Inet4Address)
            addresses.add(iface.getName() + address.toString()); 
        } 
      } 
      URL u = new URL("http://checkip.amazonaws.com/");
      BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
      addresses.add("InternetIP/" + in.readLine());
      in.close();
    } catch (Exception ignore) {
      System.out.println(ignore);
    } 
    StringBuilder sb = new StringBuilder();
    for (String s : addresses) {
      sb.append(s);
      sb.append("\n");
    } 
    return sb.toString();
  }
  
  private void initComponents() {
    this.jLabel1 = new JLabel();
    this.jLabel2 = new JLabel();
    this.jLabel3 = new JLabel();
    this.jLabel4 = new JLabel();
    this.jLabel5 = new JLabel();
    this.jLabel7 = new JLabel();
    this.passfield = new JLabel();
    this.jScrollPane1 = new JScrollPane();
    this.ipfield = new JTextArea();
    this.portfield = new JTextField();
    this.jLabel6 = new JLabel();
    setDefaultCloseOperation(3);
    setTitle("Remote PC");
    this.jLabel1.setFont(new Font("Times New Roman", 1, 36));
    this.jLabel1.setForeground(new Color(0, 51, 255));
    this.jLabel1.setHorizontalAlignment(0);
    this.jLabel1.setText("Welcome to Remote PC Server");
    this.jLabel2.setFont(new Font("Tahoma", 0, 18));
    this.jLabel2.setForeground(new Color(255, 51, 0));
    this.jLabel2.setHorizontalAlignment(0);
    this.jLabel2.setText("Following is your Login Information");
    this.jLabel3.setFont(new Font("Tahoma", 1, 14));
    this.jLabel3.setForeground(new Color(255, 0, 0));
    this.jLabel3.setHorizontalAlignment(0);
    this.jLabel3.setText("IP Addresses:");
    this.jLabel4.setFont(new Font("Tahoma", 0, 14));
    this.jLabel4.setForeground(new Color(0, 0, 255));
    this.jLabel4.setHorizontalAlignment(0);
    this.jLabel5.setFont(new Font("Tahoma", 1, 14));
    this.jLabel5.setForeground(new Color(255, 0, 0));
    this.jLabel5.setHorizontalAlignment(0);
    this.jLabel5.setText("Port No.:");
    this.jLabel7.setFont(new Font("Tahoma", 1, 14));
    this.jLabel7.setForeground(new Color(255, 0, 0));
    this.jLabel7.setHorizontalAlignment(0);
    this.jLabel7.setText("Pass Key:");
    this.passfield.setFont(new Font("Tahoma", 0, 14));
    this.passfield.setForeground(new Color(0, 0, 255));
    this.passfield.setHorizontalAlignment(0);
    this.ipfield.setEditable(false);
    this.ipfield.setBackground(new Color(240, 240, 240));
    this.ipfield.setColumns(20);
    this.ipfield.setFont(new Font("Tahoma", 0, 14));
    this.ipfield.setForeground(new Color(0, 0, 255));
    this.ipfield.setLineWrap(true);
    this.ipfield.setRows(5);
    this.jScrollPane1.setViewportView(this.ipfield);
    this.portfield.setBackground(new Color(240, 240, 240));
    this.portfield.setFont(new Font("Tahoma", 0, 14));
    this.portfield.setForeground(new Color(0, 0, 244));
    this.portfield.setHorizontalAlignment(0);
    this.portfield.addActionListener((ActionListener)new Object(this));
    this.jLabel6.setHorizontalAlignment(0);
    this.jLabel6.setText("Use above information to login from client");
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
              .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                .addGroup(layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    .addComponent(this.jLabel5, GroupLayout.Alignment.LEADING, -1, -1, 32767)
                    .addComponent(this.jLabel3, GroupLayout.Alignment.LEADING, -1, 216, 32767)
                    .addComponent(this.jLabel7, -1, -1, 32767))
                  .addGap(53, 53, 53)
                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(this.portfield, -2, 315, -2)
                    .addComponent(this.passfield, -2, 315, -2)))
                .addComponent(this.jLabel6, -1, -1, 32767))
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
              .addComponent(this.jLabel4, -1, -1, 32767))
            .addGroup(layout.createSequentialGroup()
              .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(this.jScrollPane1, -2, 315, -2)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                  .addComponent(this.jLabel1, GroupLayout.Alignment.LEADING, -1, 584, 32767)
                  .addComponent(this.jLabel2, GroupLayout.Alignment.LEADING, -1, -1, 32767)))
              .addGap(0, 7, 32767)))
          .addContainerGap()));
    layout.setVerticalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addComponent(this.jLabel1, -2, 59, -2)
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(this.jLabel2)
          .addGap(63, 63, 63)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.jLabel4)
            .addComponent(this.jScrollPane1, -1, 115, 32767)
            .addComponent(this.jLabel3, -2, 48, -2))
          .addGap(18, 18, 18)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(this.jLabel5)
            .addComponent(this.portfield, -2, -1, -2))
          .addGap(18, 18, 18)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(this.passfield)
            .addComponent(this.jLabel7))
          .addGap(18, 18, 18)
          .addComponent(this.jLabel6)
          .addGap(14, 14, 14)));
    pack();
  }
  
  private void portfieldActionPerformed(ActionEvent evt) {}
  
  public static void main(String[] args) {
    try {
      for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        } 
      } 
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(ServerInit.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (InstantiationException ex) {
      Logger.getLogger(ServerInit.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(ServerInit.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (UnsupportedLookAndFeelException ex) {
      Logger.getLogger(ServerInit.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
    EventQueue.invokeLater((Runnable)new Object());
  }
}
