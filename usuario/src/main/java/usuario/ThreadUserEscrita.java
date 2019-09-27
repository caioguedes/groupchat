package usuario;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadUserEscrita extends Thread {
  private static Socket socketCliente;
  private static DataOutputStream paraServidor;

  public ThreadUserEscrita(Socket socketCliente) throws IOException {
    ThreadUserEscrita.socketCliente = socketCliente;
    ThreadUserEscrita.paraServidor = new DataOutputStream(socketCliente.getOutputStream());
  }

  public void run() {
    while (true) {
      if (socketCliente.isClosed()) break;
    }
  }

  public static void lerInput(String mensagem) {
    try {
      if (mensagem.equalsIgnoreCase("FIM")) {
        socketCliente.close();
      } else {
        paraServidor.writeBytes(mensagem + '\n');
      }
    } catch (IOException ioe) {
    }
  }
}
