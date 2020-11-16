package Client;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JPanel;

class ClientCommandsSender implements KeyListener, MouseMotionListener, MouseListener {
  private Socket cSocket = null;
  
  private JPanel cPanel = null;
  
  private PrintWriter writer = null;
  
  private Rectangle clientScreenDim = null;
  
  ClientCommandsSender(Socket s, JPanel p, Rectangle r) {
    this.cSocket = s;
    this.cPanel = p;
    this.clientScreenDim = r;
    this.cPanel.addKeyListener(this);
    this.cPanel.addMouseListener(this);
    this.cPanel.addMouseMotionListener(this);
    try {
      this.writer = new PrintWriter(this.cSocket.getOutputStream());
    } catch (IOException ex) {
      ex.printStackTrace();
    } 
  }
  
  public void mouseDragged(MouseEvent e) {}
  
  public void mouseMoved(MouseEvent e) {
    double xScale = this.clientScreenDim.getWidth() / this.cPanel.getWidth();
    System.out.println("xScale: " + xScale);
    double yScale = this.clientScreenDim.getHeight() / this.cPanel.getHeight();
    System.out.println("yScale: " + yScale);
    System.out.println("Mouse Moved");
    this.writer.println(EnumCommands.MOVE_MOUSE.getAbbrev());
    this.writer.println((int)(e.getX() * xScale));
    this.writer.println((int)(e.getY() * yScale));
    this.writer.flush();
  }
  
  public void mouseClicked(MouseEvent e) {}
  
  public void mousePressed(MouseEvent e) {
    System.out.println("Mouse Pressed");
    this.writer.println(EnumCommands.PRESS_MOUSE.getAbbrev());
    int button = e.getButton();
    int xButton = 16;
    if (button == 3)
      xButton = 4; 
    this.writer.println(xButton);
    this.writer.flush();
  }
  
  public void mouseReleased(MouseEvent e) {
    System.out.println("Mouse Released");
    this.writer.println(EnumCommands.RELEASE_MOUSE.getAbbrev());
    int button = e.getButton();
    int xButton = 16;
    if (button == 3)
      xButton = 4; 
    this.writer.println(xButton);
    this.writer.flush();
  }
  
  public void mouseEntered(MouseEvent e) {}
  
  public void mouseExited(MouseEvent e) {}
  
  public void keyTyped(KeyEvent e) {}
  
  public void keyPressed(KeyEvent e) {
    System.out.println("Key Pressed");
    this.writer.println(EnumCommands.PRESS_KEY.getAbbrev());
    this.writer.println(e.getKeyCode());
    this.writer.flush();
  }
  
  public void keyReleased(KeyEvent e) {
    System.out.println("Mouse Released");
    this.writer.println(EnumCommands.RELEASE_KEY.getAbbrev());
    this.writer.println(e.getKeyCode());
    this.writer.flush();
  }
}
