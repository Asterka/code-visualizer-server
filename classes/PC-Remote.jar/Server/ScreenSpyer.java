package Server;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.ImageIcon;

class ScreenSpyer extends Thread {
  Socket socket = null;
  
  Robot robot = null;
  
  Rectangle rectangle = null;
  
  boolean continueLoop = true;
  
  public ScreenSpyer(Socket socket, Robot robot, Rectangle rect) {
    this.socket = socket;
    this.robot = robot;
    this.rectangle = rect;
    start();
  }
  
  public void run() {
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(this.socket.getOutputStream());
      oos.writeObject(this.rectangle);
    } catch (IOException ex) {
      ex.printStackTrace();
    } 
    while (this.continueLoop) {
      BufferedImage image = this.robot.createScreenCapture(this.rectangle);
      ImageIcon imageIcon = new ImageIcon(image);
      System.out.println("Before sending image");
      oos.writeObject(imageIcon);
      oos.reset();
      System.out.println("New screenshot sent");
      Thread.sleep(100L);
    } 
  }
}
