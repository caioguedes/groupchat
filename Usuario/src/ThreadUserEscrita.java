import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadUserEscrita extends Thread {
  private Socket socketCliente;

  public ThreadUserEscrita(Socket socketCliente) {
    this.socketCliente = socketCliente;
  }

  public void run() {
    try {
      System.out.println("Digite seu nome: ");
      lerInput();
      System.out.println("Bem-vindo ao chat! :)");
      while (true) {
        if (socketCliente.isClosed()) break;
        lerInput();
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  private void lerInput() throws IOException {
    DataOutputStream paraServidor = new DataOutputStream(socketCliente.getOutputStream());
    BufferedReader fraseUsuario = new BufferedReader(new InputStreamReader(System.in));
    String mensagem = fraseUsuario.readLine();

    if (mensagem.equalsIgnoreCase("FIM")) {
      socketCliente.close();
    } else {
      paraServidor.writeBytes(fraseUsuario.readLine() + '\n');
    }
  }
}
