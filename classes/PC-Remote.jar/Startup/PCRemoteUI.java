package Startup;

import Client.ClientInit;
import Server.ServerInit;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PCRemoteUI extends JFrame {
  private JButton clientBtn;
  
  private JLabel clientLabel;
  
  private JLabel jLabel1;
  
  private JPanel jPanel1;
  
  private JButton serverBtn;
  
  private JLabel serverLabel;
  
  public PCRemoteUI() {
    initComponents();
    setLocationRelativeTo(null);
    setTitle("Remote PC");
    ImageIcon icon = new ImageIcon(getClass().getResource("/Startup/img/icon.png"));
    setIconImage(icon.getImage());
  }
  
  private void initComponents() {
    this.jPanel1 = new JPanel();
    this.clientBtn = new JButton();
    this.serverLabel = new JLabel();
    this.clientLabel = new JLabel();
    this.serverBtn = new JButton();
    this.jLabel1 = new JLabel();
    setDefaultCloseOperation(3);
    setTitle("PC Remote");
    setBackground(new Color(102, 102, 102));
    setCursor(new Cursor(0));
    setFont(new Font("Arial", 1, 14));
    setForeground(new Color(204, 204, 204));
    setResizable(false);
    setType(Window.Type.POPUP);
    this.jPanel1.setBackground(new Color(204, 204, 204));
    this.jPanel1.setBorder(BorderFactory.createLineBorder(new Color(102, 102, 102), 2));
    this.jPanel1.setForeground(new Color(102, 102, 102));
    this.clientBtn.setFont(new Font("Tahoma", 1, 13));
    this.clientBtn.setText("CLIENT");
    this.clientBtn.setAlignmentX(25.0F);
    this.clientBtn.setAlignmentY(25.0F);
    this.clientBtn.addActionListener((ActionListener)new Object(this));
    this.serverLabel.setIcon(new ImageIcon(getClass().getResource("/Startup/img/server.png")));
    this.serverLabel.setText("jLabel1");
    this.serverLabel.addMouseListener((MouseListener)new Object(this));
    this.clientLabel.setIcon(new ImageIcon(getClass().getResource("/Startup/img/client.png")));
    this.clientLabel.setText("b");
    this.clientLabel.addMouseListener((MouseListener)new Object(this));
    this.serverBtn.setFont(new Font("Tahoma", 1, 13));
    this.serverBtn.setText("SERVER");
    this.serverBtn.addActionListener((ActionListener)new Object(this));
    this.jLabel1.setFont(new Font("Tahoma", 1, 14));
    this.jLabel1.setForeground(new Color(255, 0, 0));
    this.jLabel1.setHorizontalAlignment(0);
    this.jLabel1.setText("Choose your purpose!");
    GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
    this.jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(jPanel1Layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
          .addGap(49, 49, 49)
          .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
            .addComponent(this.jLabel1, -1, -1, 32767)
            .addGroup(jPanel1Layout.createSequentialGroup()
              .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                .addComponent(this.serverBtn, -1, -1, 32767)
                .addComponent(this.serverLabel, -2, 110, -2))
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 71, 32767)
              .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                .addComponent(this.clientLabel, -2, 110, -2)
                .addComponent(this.clientBtn, -1, -1, 32767))))
          .addGap(56, 56, 56)));
    jPanel1Layout.setVerticalGroup(jPanel1Layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
          .addContainerGap(46, 32767)
          .addComponent(this.jLabel1)
          .addGap(18, 18, 18)
          .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.serverLabel, -2, 135, -2)
            .addComponent(this.clientLabel))
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.serverBtn)
            .addComponent(this.clientBtn))
          .addGap(67, 67, 67)));
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(this.jPanel1, -1, -1, 32767));
    layout.setVerticalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
          .addGap(0, 0, 32767)
          .addComponent(this.jPanel1, -2, -1, -2)));
    pack();
  }
  
  private void serverBtnActionPerformed(ActionEvent evt) {
    ServerInit si = new ServerInit();
    si.setVisible(true);
    dispose();
  }
  
  private void clientBtnActionPerformed(ActionEvent evt) {
    ClientInit ci = new ClientInit();
    ci.setVisible(true);
    dispose();
  }
  
  private void serverLabelMouseClicked(MouseEvent evt) {
    this.serverBtn.doClick();
  }
  
  private void clientLabelMouseClicked(MouseEvent evt) {
    this.clientBtn.doClick();
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
      Logger.getLogger(PCRemoteUI.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (InstantiationException ex) {
      Logger.getLogger(PCRemoteUI.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(PCRemoteUI.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (UnsupportedLookAndFeelException ex) {
      Logger.getLogger(PCRemoteUI.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
    EventQueue.invokeLater((Runnable)new Object());
  }
}
