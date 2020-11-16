package Server;

import Chatexpress.SDrawInit;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ServerHandler extends Thread {
  private ServerSocket sc = null;
  
  private Socket client = null;
  
  private int port;
  
  DataInputStream dIn;
  
  DataOutputStream dOut;
  
  boolean receive = true;
  
  int pass;
  
  String pswd;
  
  public ServerHandler(int port, int pass) {
    this.port = port;
    this.pass = pass;
    start();
  }
  
  public void run() {
    Robot robot = null;
    Rectangle rect = null;
    try {
      System.out.println("Connecting to server......");
      this.sc = new ServerSocket(this.port);
      int clients = 0;
      while (clients == 0) {
        this.client = this.sc.accept();
        if (this.client != null) {
          System.out.println("Client Recieved!");
          this.dIn = new DataInputStream(this.client.getInputStream());
          this.dOut = new DataOutputStream(this.client.getOutputStream());
          byte type = this.dIn.readByte();
          System.out.println("Read Byte");
          if (type == 1) {
            this.pswd = this.dIn.readUTF();
            System.out.println("Password Recieved:" + this.pswd);
            if (this.pswd.equals(String.valueOf(this.pass))) {
              System.out.println("Password Matched!");
              this.dOut.writeByte(1);
              this.dOut.flush();
              this.dIn.close();
              this.dOut.close();
              this.client = this.sc.accept();
              clients++;
              continue;
            } 
            this.dOut.writeByte(2);
            this.dOut.flush();
            this.dIn.close();
            this.dOut.close();
            this.client = null;
            clients = 0;
          } 
        } 
      } 
      System.out.println("Connection Established.");
      GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice gd = gEnv.getDefaultScreenDevice();
      Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
      rect = new Rectangle(dimen);
      robot = new Robot(gd);
      new SDrawInit();
      new ScreenSpyer(this.client, robot, rect);
      new ServerDriver(this.client, robot);
    } catch (AWTException e) {
      e.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    } 
  }
}
