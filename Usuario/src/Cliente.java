import java.net.ConnectException;
import java.net.Socket;

public class Cliente {
  private static final String HOST = "127.0.0.1";
  private static final int PORT = 6789;
  private static Socket socketCliente;

  public static void main(String argv[]) throws Exception {
    Socket socketCliente = realizarConexao();
    new Thread(new ThreadUserLeitura(socketCliente)).start();
    new Thread(new ThreadUserEscrita(socketCliente)).start();
  }

  private static Socket realizarConexao() throws Exception {
    while (true) {
      try {
        socketCliente = new Socket(HOST, PORT);
        break;
      } catch (ConnectException connection) {
        System.out.println("Tentando conexão...");
        Thread.sleep(5000);
      }
    }
    return socketCliente;
  }
}
