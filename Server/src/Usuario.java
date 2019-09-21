import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Usuario extends Thread {

  private int id;
  private String nome;
  private Socket socketCliente;
  private BufferedReader doUsuario;

  public Usuario(Socket socketCliente, int id) throws IOException {
    this.socketCliente = socketCliente;
    this.id = id;
    this.doUsuario = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
  }

  public void run() {
    try {
      this.nome = doUsuario.readLine();
      while (true) {
        String mensagem = doUsuario.readLine();
        if (mensagem == null) {
          break;
        }
        ThreadServidor.enviarMensagem(mensagem, id, nome);
      }
    } catch (Exception e) {
      ThreadServidor.removerUsuario(id);
    }
    System.out.println("Conexão perdida com usuário " + id + ".");
  }

  public int getIdUsuario() {
    return id;
  }

  public Socket getSocket() {
    return socketCliente;
  }

  public String toString() {
    return id + ", " + nome;
  }
}
