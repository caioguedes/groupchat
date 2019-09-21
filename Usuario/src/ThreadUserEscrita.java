import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadUserEscrita extends Thread {
  private Socket socketCliente;
  private DataOutputStream paraServidor;
  private BufferedReader fraseUsuario;

  public ThreadUserEscrita(Socket socketCliente) throws IOException {
    this.socketCliente = socketCliente;
    this.paraServidor = new DataOutputStream(socketCliente.getOutputStream());
    this.fraseUsuario = new BufferedReader(new InputStreamReader(System.in));
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
    String mensagem = fraseUsuario.readLine();

    if (mensagem.equalsIgnoreCase("FIM")) {
      socketCliente.close();
    } else {
      paraServidor.writeBytes(mensagem + '\n');
    }
  }
}
