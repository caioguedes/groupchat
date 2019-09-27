import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadUserEscrita extends Thread {
  private static Socket socketCliente;
  private static DataOutputStream paraServidor;
  private BufferedReader fraseUsuario;

  public ThreadUserEscrita(Socket socketCliente) throws IOException {
    this.socketCliente = socketCliente;
    this.paraServidor = new DataOutputStream(socketCliente.getOutputStream());
    this.fraseUsuario = new BufferedReader(new InputStreamReader(System.in));
  }

  public void run() {
    // System.out.println("Digite seu nome: ");
    // System.out.println("Bem-vindo ao chat! :)");
    while (true) {
      // lerInput();
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
      // socketCliente.close();
    }
  }
}

  //  private void lerInput() throws IOException {
  //    String mensagem = fraseUsuario.readLine();
  //
  //    if (mensagem.equalsIgnoreCase("FIM")) {
  //      socketCliente.close();
  //    } else {
  //      paraServidor.writeBytes(mensagem + '\n');
  //    }
  //  }
