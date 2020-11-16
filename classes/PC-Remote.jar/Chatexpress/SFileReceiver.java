package Chatexpress;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class SFileReceiver extends Thread {
  Socket s;
  
  String server_ip;
  
  DataInputStream dis;
  
  String file;
  
  JFrame f;
  
  ServerSocket ss;
  
  public SFileReceiver() throws IOException {
    try {
      this.ss = new ServerSocket(2800);
      this.s = this.ss.accept();
      this.dis = new DataInputStream(this.s.getInputStream());
      DataOutputStream dout = new DataOutputStream(this.s.getOutputStream());
      int a = JOptionPane.showConfirmDialog(this.f = new JFrame(), "Do you want to receive file?");
      if (a == 0) {
        dout.writeInt(8);
        dout.flush();
        String s2 = this.dis.readUTF();
        this.file = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\" + s2;
        start();
        JOptionPane.showMessageDialog(null, "File recieved at:\n" + this.file);
      } 
      if (a == 1) {
        this.f.dispose();
        dout.writeInt(7);
        dout.flush();
      } 
    } catch (Exception e) {
      System.out.println(e);
    } 
  }
  
  public void run() {
    try {
      InputStream in = null;
      OutputStream out = null;
      try {
        in = this.s.getInputStream();
      } catch (IOException ex) {
        System.out.println("Can't get socket input stream. ");
      } 
      try {
        out = new FileOutputStream(this.file);
      } catch (FileNotFoundException ex) {
        System.out.println("File not found.");
      } 
      byte[] bytes = new byte[8192];
      int count;
      while ((count = in.read(bytes)) > 0)
        out.write(bytes, 0, count); 
      out.close();
      in.close();
      this.s.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
