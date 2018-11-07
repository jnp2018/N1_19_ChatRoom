
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HOANGBAO
 */
public class ServerControl {

    private ServerView view;
    private ServerSocket myServer;
    private final List<ClientHandler> workers = new ArrayList<>();
    private final List<String> users = new ArrayList<>();
    private Socket clientSocket;
    private int serverPort = 8888;

    public ServerControl(ServerView view) {
        this.view = view;
        openServer(serverPort);
        view.showMessage("TCP server is running...");
        while (true) {
            listenning();

        }
    }

    private void openServer(int portNumber) {
        try {
            myServer = new ServerSocket(portNumber);
        } catch (IOException e) {
            view.showMessage(e.toString());
        }
    }

    private void listenning() {
        try {
            clientSocket = myServer.accept();

            // create a new thread object 
            ClientHandler t = new ClientHandler(clientSocket);
            addWorker(t);

            // Invoking the start() method 
            t.start();

        } catch (Exception e) {
            view.showMessage(e.toString());
        }
    }

    private void addWorker(ClientHandler worker) {
        synchronized (this) {
            workers.add(worker);
        }
    }

    private void removeWorker(ClientHandler worker) {
        synchronized (this) {
            workers.remove(worker);
            worker.close();
        }
    }

    private void broadcastMessage(ClientHandler from, String message) throws IOException {
        synchronized (this) {
            String msg = String.format("%s: %s", from.username, message);
            for (int i = 0; i < workers.size(); i++) {
                ClientHandler worker = workers.get(i);
                if (!worker.equals(from)) {
                    try {
                        worker.send(msg);
                    } catch (IOException e) {
                        workers.remove(i--);
                        worker.close();
                    }
                } else {
                    worker.send(String.format("%s: %s", "Me", message));
                }
            }
        }
    }

    private boolean checkUser(String user) throws Exception {

        return true;
    }

    class ClientHandler extends Thread {

        private final Socket socket;
        private DataOutputStream dos;
        private DataInputStream dis;
        private String username = null;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        }

        private void send(String message) throws IOException {
            dos.writeUTF(message);
        }

        @Override
        public void run() {
            try {
                username = dis.readUTF();
                while (true) {
                    String msg = dis.readUTF();
                    broadcastMessage(this, msg);
                }
            } catch (IOException e) {
            }
            removeWorker(this);
        }

        private void close() {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }
}
