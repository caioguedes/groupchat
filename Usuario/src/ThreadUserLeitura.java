import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadUserLeitura extends Thread {
  private Socket socketCliente;

  public ThreadUserLeitura(Socket socketCliente) {
    this.socketCliente = socketCliente;
  }

  public void run() {
    while (true) {
      try {
        BufferedReader doServidor =
            new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
        System.out.println(doServidor.readLine());
      } catch (Exception e) {
        break;
      }
    }
    System.out.println("A conexão com o servidor foi encerrada.");
  }
}
