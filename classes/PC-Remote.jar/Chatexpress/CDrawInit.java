package Chatexpress;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CDrawInit extends Thread {
  String server_ip = null;
  
  CDrawGUI dg;
  
  public CDrawInit(String server_ip) {
    this.server_ip = server_ip;
    start();
  }
  
  public void run() {
    try {
      this.dg = new CDrawGUI(this.server_ip);
      this.dg.setVisible(true);
      while (true) {
        String smessage = this.dg.dis.readUTF();
        this.dg.jTextArea1.append("server:" + smessage + "\n");
      } 
    } catch (IOException ex) {
      Logger.getLogger(CDrawInit.class.getName()).log(Level.SEVERE, (String)null, ex);
      return;
    } 
  }
}
