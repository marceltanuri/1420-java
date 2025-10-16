import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer {

    public static void main(String[] args) throws IOException {
        CepRepository cepRepository = new CepRepository();
        CepRestController cepRestController = new CepRestController(cepRepository);
        Router router = new Router(cepRestController);

        ServerSocket serverSocket = new ServerSocket(8081);
        System.out.println("Server listening on port 8081");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String requestLine = in.readLine();
                    router.handleRequest(clientSocket, requestLine);
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
