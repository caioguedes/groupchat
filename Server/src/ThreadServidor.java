import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ThreadServidor extends Thread {
  private static List<Usuario> listaUsuarios;
  private ServerSocket socketRecepcao;
  private int id;

  public ThreadServidor(ServerSocket socketRecepcao) {
    this.socketRecepcao = socketRecepcao;
    listaUsuarios = new ArrayList<Usuario>();
  }

  public void run() {
    try {
      while (true) {
        Socket socketConexao = socketRecepcao.accept();

        listaUsuarios.add(new Usuario(socketConexao, id++));
        new Thread(listaUsuarios.get(listaUsuarios.size() - 1)).start();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void enviarMensagem(String mensagem, int id, String nome) {
    DataOutputStream paraUsuario;
    for (int i = 0; i < listaUsuarios.size(); i++) {
      try {
        System.out.println(
            "Tentando enviar mensagem " + mensagem + " para " + listaUsuarios.get(i));
        paraUsuario = new DataOutputStream(listaUsuarios.get(i).getSocket().getOutputStream());
        paraUsuario.writeBytes(nome + ": " + mensagem + "\n");
      } catch (Exception e) {
        System.out.println(listaUsuarios.get(i) + " - Problema na conexão.");
        listaUsuarios.remove(i);
        i--;
      }
    }
  }

  public static void enviarMensagem(String mensagem, int id) {
    DataOutputStream paraUsuario;
    for (int i = 0; i < listaUsuarios.size(); i++) {
      try {
        paraUsuario = new DataOutputStream(listaUsuarios.get(i).getSocket().getOutputStream());
        paraUsuario.writeBytes(mensagem + "\n");
      } catch (Exception e) {
        System.out.println(listaUsuarios.get(i) + " - Problema na conexão.");
        listaUsuarios.remove(i);
        i--;
      }
    }
  }

  public static void removerUsuario(int id) {
    for (int i = 0; i < listaUsuarios.size(); i++) {
      if (listaUsuarios.get(i).getIdUsuario() == id) {
        listaUsuarios.remove(i);
        break;
      }
    }
  }
}
