
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author HOANGBAO
 */
public class ClientControl {

    private ClientView view;
    private String serverHost = "localhost";
    private int serverPort = 8888;

    private Socket client;
    private DataOutputStream dos;
    private DataInputStream dis;

    public ClientControl(ClientView view) {
        try {
            this.view = view;
            client = new Socket(serverHost, serverPort);
            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());
            this.view.addLoginListener(new LoginListener());
            this.view.addSendListener(new SendListener());
            Thread IncomingReader = new Thread(new IncomingReader());
            IncomingReader.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public class IncomingReader implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    view.setMsg(dis.readUTF());
                }
            } catch (IOException e) {
            }
        }
    }

    /*------------Xử lí Login-------------*/
    class LoginListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String s = view.getUser().getUserName();
            if(!s.isEmpty())
            try {
                dos.writeUTF(s);
                view.setP_login(false);
                view.setP_chat(true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /*------------Xử lí CHAT-------------*/
    class SendListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String s = view.getMessage();
            if (!s.isEmpty()) {
                try {
                    view.setMessage("");
                    dos.writeUTF(s + "\n");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
