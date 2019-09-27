import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ConnectException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Front {

  private static String appName = "Chat";
  Front mainGUI;
  public static JFrame newFrame = new JFrame(appName);
  JButton sendMessage;
  public static JTextField messageBox;
  public static JTextArea chatBox;
  JTextField usernameChooser;
  JFrame preFrame;

  private static final String HOST = "191.4.197.154";
  private static final int PORT = 6789;
  private static Socket socketCliente;

  public static void main(String[] args) throws Exception {
    Socket socketCliente = realizarConexao();
    new Thread(new ThreadUserLeitura(socketCliente)).start();
    new Thread(new ThreadUserEscrita(socketCliente)).start();

    SwingUtilities.invokeLater(
        new Runnable() {
          @Override
          public void run() {
            try {
              UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
              e.printStackTrace();
            }
            Front mainGUI = new Front();
            mainGUI.preDisplay();
          }
        });
  }

  private static Socket realizarConexao() throws Exception {
    while (true) {
      try {
        socketCliente = new Socket(HOST, PORT);
        break;
      } catch (ConnectException connection) {
        System.out.println("Tentando conexão...");
        Thread.sleep(5000);
      }
    }
    return socketCliente;
  }

  public void preDisplay() {
    newFrame.setVisible(false);
    preFrame = new JFrame(appName);
    usernameChooser = new JTextField(15);
    JLabel chooseUsernameLabel = new JLabel("Digite seu nome:");

    JButton enterServer = new JButton("Entrar");
    enterServer.addActionListener(new enterServerButtonListener());
    JPanel prePanel = new JPanel(new GridBagLayout());

    GridBagConstraints preRight = new GridBagConstraints();
    preRight.insets = new Insets(0, 0, 0, 10);
    preRight.anchor = GridBagConstraints.EAST;
    GridBagConstraints preLeft = new GridBagConstraints();
    preLeft.anchor = GridBagConstraints.WEST;
    preLeft.insets = new Insets(0, 10, 0, 10);
    // preRight.weightx = 2.0;
    preRight.fill = GridBagConstraints.HORIZONTAL;
    preRight.gridwidth = GridBagConstraints.REMAINDER;

    prePanel.add(chooseUsernameLabel, preLeft);
    prePanel.add(usernameChooser, preRight);
    preFrame.add(BorderLayout.CENTER, prePanel);
    preFrame.add(BorderLayout.SOUTH, enterServer);
    preFrame.setSize(300, 300);
    preFrame.setVisible(true);
  }

  public void display() {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    JPanel southPanel = new JPanel();
    southPanel.setBackground(Color.BLUE);
    southPanel.setLayout(new GridBagLayout());

    messageBox = new JTextField(30);
    messageBox.requestFocusInWindow();

    sendMessage = new JButton("Send Message");
    sendMessage.addActionListener(new SendMessageListener());

    chatBox = new JTextArea();
    chatBox.setEditable(false);
    chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
    chatBox.setLineWrap(true);

    mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

    GridBagConstraints left = new GridBagConstraints();
    left.anchor = GridBagConstraints.LINE_START;
    left.fill = GridBagConstraints.HORIZONTAL;
    left.weightx = 512.0D;
    left.weighty = 1.0D;

    GridBagConstraints right = new GridBagConstraints();
    right.insets = new Insets(0, 10, 0, 0);
    right.anchor = GridBagConstraints.LINE_END;
    right.fill = GridBagConstraints.NONE;
    right.weightx = 1.0D;
    right.weighty = 1.0D;

    southPanel.add(messageBox, left);
    southPanel.add(sendMessage, right);

    mainPanel.add(BorderLayout.SOUTH, southPanel);

    newFrame.add(mainPanel);
    newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    newFrame.setSize(470, 300);
    newFrame.setVisible(true);
  }

  String username;

  class enterServerButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      username = usernameChooser.getText();
      if (username.length() < 1) {
        System.out.println("No!");
      } else {
        preFrame.setVisible(false);
        display();
      }
    }
  }
}
