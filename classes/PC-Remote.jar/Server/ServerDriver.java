package Server;

import java.awt.Robot;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class ServerDriver extends Thread {
  Robot robot = null;
  
  boolean loop = true;
  
  Socket socket = null;
  
  public ServerDriver(Socket socket, Robot robot) {
    this.socket = socket;
    this.robot = robot;
    start();
  }
  
  public void run() {
    Scanner scanner = null;
    try {
      System.out.println("Starting InStream");
      scanner = new Scanner(this.socket.getInputStream());
      while (this.loop) {
        System.out.println("Waiting...");
        int command = scanner.nextInt();
        System.out.println("New command: " + command);
        switch (command) {
          case 1:
            this.robot.mousePress(scanner.nextInt());
          case 2:
            this.robot.mouseRelease(scanner.nextInt());
          case 3:
            this.robot.keyPress(scanner.nextInt());
          case 4:
            this.robot.keyRelease(scanner.nextInt());
          case 5:
            this.robot.mouseMove(scanner.nextInt(), scanner.nextInt());
        } 
      } 
    } catch (IOException e) {
      System.out.println(e);
    } 
  }
}
