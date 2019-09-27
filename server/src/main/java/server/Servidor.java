package server;

import java.net.ServerSocket;

public class Servidor {
  private static final int PORT = 6789;

  public static void main(String[] args) throws Exception {
    System.out.println("Servidor iniciado.");
    ServerSocket chat = new ServerSocket(PORT);
    new Thread(new ThreadServidor(chat)).start();
  }
}
