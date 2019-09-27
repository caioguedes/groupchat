package server;

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
      System.out.println(this + " - Conexão estabelecida.");
      this.nome = doUsuario.readLine();
      System.out.println(this + " - Nome definido.");
      ThreadServidor.enviarMensagem(nome + " entrou.", id);
      while (true) {
        String mensagem = doUsuario.readLine();
        if (mensagem == null) {
          System.out.println(this + " - O usuário não foi encontrado.");
          break;
        }
        ThreadServidor.enviarMensagem(mensagem, id, nome);
      }
    } catch (Exception e) {
      System.out.println("Conexão perdida com usuário " + id + ".");
      ThreadServidor.removerUsuario(id);
    }
    ThreadServidor.enviarMensagem(nome + " saiu.", id);
  }

  public int getIdUsuario() {
    return id;
  }

  public Socket getSocket() {
    return socketCliente;
  }

  public String toString() {
    return "[User " + id + ", " + nome + "]";
  }
}
