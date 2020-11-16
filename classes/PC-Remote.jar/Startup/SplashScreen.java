package Startup;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SplashScreen extends JFrame implements Runnable {
  int value;
  
  private JLabel img;
  
  private JProgressBar progressBar;
  
  public void run() {
    Random rand = new Random();
    int a = rand.nextInt(30) + 20;
    int b = rand.nextInt(35) + 60;
    while (true) {
      this.value++;
      this.progressBar.setValue(this.value);
      if (this.value == 100) {
        dispose();
        PCRemoteUI pr = new PCRemoteUI();
        pr.setVisible(true);
        break;
      } 
      try {
        if (this.value == b || this.value == a) {
          Thread.sleep(300L);
          continue;
        } 
        Thread.sleep(25L);
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      } 
    } 
  }
  
  public SplashScreen() throws InterruptedException {
    initComponents();
    setTitle("Remote PC");
    ImageIcon icon = new ImageIcon(getClass().getResource("/Startup/img/icon.png"));
    setIconImage(icon.getImage());
    getContentPane().setBackground(Color.white);
    setLocationRelativeTo(null);
    this.progressBar.setForeground(Color.BLACK);
    Thread th = new Thread(this);
    th.start();
  }
  
  private void initComponents() {
    this.img = new JLabel();
    this.progressBar = new JProgressBar();
    setDefaultCloseOperation(0);
    setBackground(new Color(255, 255, 255));
    setUndecorated(true);
    setResizable(false);
    this.img.setIcon(new ImageIcon(getClass().getResource("img/splashscreen.png")));
    this.progressBar.setBackground(new Color(255, 255, 255));
    this.progressBar.setBorderPainted(false);
    this.progressBar.setStringPainted(true);
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(this.img, -1, -1, 32767)
        .addComponent(this.progressBar, -1, -1, 32767));
    layout.setVerticalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
          .addComponent(this.img)
          .addGap(0, 0, 0)
          .addComponent(this.progressBar, -2, 20, -2)));
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
      Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (InstantiationException ex) {
      Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (UnsupportedLookAndFeelException ex) {
      Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
    EventQueue.invokeLater((Runnable)new Object());
  }
}
