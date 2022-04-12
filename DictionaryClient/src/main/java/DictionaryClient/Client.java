package DictionaryClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    // IP and port
    private static String ip = DictionaryClient.Ip;
    private static int port = DictionaryClient.Port;

    public static void main(String[] args) {

        try (Socket socket = new Socket(ip, port);) {
            // Output and Input Stream
            DataInputStream input = new DataInputStream(socket.getInputStream());

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            //String sendData ="I want to connect";
            String sendData = "OP0: Cat"; //0 Search 1 Remove 2 Update 3 add

            output.writeUTF(sendData);
            System.out.println("Data sent to DictionaryServer--> " + sendData);
            output.flush();

            boolean flag = true;
            while (flag) {
                if (input.available() > 0) {
                    String message = input.readUTF();
                    System.out.println(message);
                    flag = false;
                    ;
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
