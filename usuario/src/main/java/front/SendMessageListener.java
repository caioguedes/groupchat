package front;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import usuario.ThreadUserEscrita;

public class SendMessageListener implements ActionListener {
  public void actionPerformed(ActionEvent event) {
    if (Front.messageBox.getText().length() < 1) {
      // do nothing
    } else if (Front.messageBox.getText().equals(".clear")) {
      Front.chatBox.setText("Cleared all messages\n");
      Front.messageBox.setText("");
    } else {
      // chatBox.append(username + ":  " + messageBox.getText() + "\n");
      ThreadUserEscrita.lerInput(Front.messageBox.getText());
      Front.messageBox.setText("");
    }
    Front.messageBox.requestFocusInWindow();
  }
}
