package Client;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

class ClientScreenReciever extends Thread {
  private ObjectInputStream cObjectInputStream = null;
  
  private JPanel cPanel = null;
  
  private boolean continueLoop = true;
  
  public ClientScreenReciever(ObjectInputStream ois, JPanel p) {
    this.cObjectInputStream = ois;
    this.cPanel = p;
    start();
  }
  
  public void run() {
    try {
      while (this.continueLoop) {
        ImageIcon imageIcon = (ImageIcon)this.cObjectInputStream.readObject();
        System.out.println("New image recieved");
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(this.cPanel.getWidth(), this.cPanel.getHeight(), 2);
        Graphics graphics = this.cPanel.getGraphics();
        graphics.drawImage(image, 0, 0, this.cPanel.getWidth(), this.cPanel.getHeight(), this.cPanel);
      } 
    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    } 
  }
}
