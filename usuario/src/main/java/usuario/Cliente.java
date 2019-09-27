package usuario;

import java.net.ConnectException;
import java.net.Socket;

import front.Front;

public class Cliente {
  private static final String HOST = "172.19.34.44";
  private static final int PORT = 6789;
  private static Socket socketCliente;

  public static void main(String argv[]) throws Exception {
    Socket socketCliente = realizarConexao();
    Front.start();
    new Thread(new ThreadUserLeitura(socketCliente)).start();
    new Thread(new ThreadUserEscrita(socketCliente)).start();
  }

  private static Socket realizarConexao() throws Exception {
    while (true) {
      try {
        socketCliente = new Socket(HOST, PORT);
        break;
      } catch (ConnectException conexao) {
        System.out.println("Tentando conexão...");
        Thread.sleep(5000);
      }
    }
    return socketCliente;
  }
}
