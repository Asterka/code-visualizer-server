package Chatexpress;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SDrawInit extends Thread {
  SDrawGUI dg;
  
  public SDrawInit() {
    start();
  }
  
  public void run() {
    try {
      this.dg = new SDrawGUI();
      this.dg.setVisible(true);
      while (true) {
        this.dg.cmessage = this.dg.dis.readUTF();
        this.dg.jTextArea1.append("client:" + this.dg.cmessage + "\n");
      } 
    } catch (IOException ex) {
      Logger.getLogger(CDrawInit.class.getName()).log(Level.SEVERE, (String)null, ex);
      return;
    } 
  }
}
