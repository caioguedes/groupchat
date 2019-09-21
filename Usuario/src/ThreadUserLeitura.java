import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadUserLeitura extends Thread {
  private Socket socketCliente;
  private BufferedReader doServidor;

  public ThreadUserLeitura(Socket socketCliente) throws IOException {
    this.socketCliente = socketCliente;
    this.doServidor = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
  }

  public void run() {
    while (true) {
      try {
        String mensagem = doServidor.readLine();
        if (mensagem == null) {
          socketCliente.close();
          break;
        }
        System.out.println(mensagem);
      } catch (Exception e) {
        break;
      }
    }
  }
}
