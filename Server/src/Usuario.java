import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class Usuario extends Thread {

  private int id;
  private String nome;
  private Socket socketCliente;

  public Usuario(Socket socketCliente, int id) {
    this.socketCliente = socketCliente;
    this.id = id;
  }

  public void run() {
    try {
      BufferedReader doUsuario =
          new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
      this.nome = doUsuario.readLine();
      while (true) {
        doUsuario = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
        if (doUsuario != null) {
          ThreadServidor.enviarMensagem(doUsuario.readLine(), id, nome);
        }
      }
    } catch (SocketException socket) {
      System.out.println("Conexão perdida com usuário " + id + ".");
      ThreadServidor.removerUsuario(id);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int getIdUsuario() {
    return id;
  }

  public Socket getSocket() {
    return socketCliente;
  }
}
