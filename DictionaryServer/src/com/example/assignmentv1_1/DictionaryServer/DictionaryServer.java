package com.example.assignmentv1_1.DictionaryServer;

import javax.net.ServerSocketFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DictionaryServer {
    static String Filepath = ".\\dict.txt";
    // Declare the port number
    private static int port = 3005;

    // Identifies the user number connected
    private static int counter = 0;

    public static void main(String[] args) {
        //定义一个file
        File file = null;
        try {
            if (args.length == 2) {
                //解析端口号 第一个参数不是数字会抛NumberFormatException
                port = Integer.parseInt(args[0]);
                //判断端口是否在1-65535之间，不在的话抛IllegalArgumentException
                if (port > 65535 || port < 1) {
                    throw new IllegalArgumentException();
                }
                Filepath = args[1];
            }
            file = new File(Filepath);
            //判断文件是否存在，不存在手工抛 SecurityException 有点多余
            if (!(file.exists() && file.isFile())) {
                throw new SecurityException();
            }

        } catch (NumberFormatException e) {
            System.out.println("Wrong Port Number");
            System.exit(0);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal Port Number, Must between 1-65535");
            System.exit(0);
        } catch (SecurityException e) {
            System.out.println("Illegal Dictionary File");
            System.exit(0);
        }

        ServerSocketFactory factory = ServerSocketFactory.getDefault();

        try (ServerSocket server = factory.createServerSocket(port)) {
            System.out.println("Listen on Port:" + port + ", Dictionary file is " + file.getCanonicalPath());
            System.out.println("Waiting for client connection-");

            // Wait for connections.
            while (true) {
                Socket client = server.accept();

                counter++;
                System.out.println("Client " + counter + ": Applying for connection!");

                // Start a new thread for a connection
                Thread t = new Thread(() -> serveClient(client));
                t.start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void serveClient(Socket client) {
        try (Socket clientSocket = client) {
            // Input stream
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            // Output Stream
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            String outcome;
            String Operation = input.readUTF();
            if (Operation.equals("Connection Detection")) {
                output.writeUTF("Success");
                output.flush();
                output.close();
                input.close();
                System.out.println("New Connection Detected");
                return;
            }

            System.out.println("CLIENT: " + Operation);
            char OP = Operation.charAt(Operation.indexOf("OP") + 2);
            System.out.println("Client's operation: " + OP);


            switch (OP) {
                case ('0')://Search operation requested
                    String ToSearch = Operation.substring(Operation.indexOf("OP") + 5);
                    System.out.println("You are now searching for: " + ToSearch);
                    outcome = SearchWord.LookItUp(ToSearch);
                    break;
                case ('1')://Remove operation requested
                    String ToRemove = Operation.substring(Operation.indexOf("OP") + 5);
                    System.out.println("You are now removing: " + ToRemove);
                    outcome = SearchWord.RemoveAWord(ToRemove);
                    break;
                case ('2')://Operation Update requested
                    System.out.println("div at: " + Operation.indexOf("-"));
                    String ToUpdate = Operation.substring(Operation.indexOf("OP") + 5, Operation.indexOf("-")); //Sample: OP2: Cat-meow meow meow
                    System.out.println("Toupdate: " + ToUpdate);
                    String NewMeaning = Operation.substring(Operation.indexOf("-") + 1);
                    System.out.println("Newmeaning: " + NewMeaning);
                    outcome = SearchWord.UpdateAWord(ToUpdate, NewMeaning);
                    break;
                case ('3')://Add operation requested
                    int div = Operation.indexOf("-");
                    String ToAdd = Operation.substring(Operation.indexOf("OP") + 5, div);//Sample: OP3: Poop-Number2
                    String Meaning = Operation.substring(div + 1);
                    outcome = SearchWord.AddAWord(ToAdd, Meaning);
                    break;

                default://其它一律认为为连接检测
                    outcome = "Connection Success";
                    System.out.println("New Connection Detected");
            }


//            if (OP == '0') {//Search operation requested
//
//                String ToSearch = Operation.substring(Operation.indexOf("OP") + 5);
//                System.out.println("You are now searching for: " + ToSearch);
//                outcome = SearchWord.LookItUp(ToSearch);
//
//            } else if (OP == '1') {//Remove operation requested
//
//                String ToRemove = Operation.substring(Operation.indexOf("OP") + 5);
//                System.out.println("You are now removing: " + ToRemove);
//                outcome = SearchWord.RemoveAWord(ToRemove);
//
//            } else if (OP == '2') {//Operation Update requested
//
//                //char div=Operation.charAt(Operation.indexOf("-")+2);
//                System.out.println("div at: " + Operation.indexOf("-"));
//                String ToUpdate = Operation.substring(Operation.indexOf("OP") + 5, Operation.indexOf("-")); //Sample: OP2: Cat-meow meow meow
//                System.out.println("Toupdate: " + ToUpdate);
//                String NewMeaning = Operation.substring(Operation.indexOf("-") + 1);
//                System.out.println("Newmeaning: " + NewMeaning);
//                outcome = SearchWord.UpdateAWord(ToUpdate, NewMeaning);
//
//            } else if (OP == '3') {//Add operation requested
//                int div = Operation.indexOf("-");
//                String ToAdd = Operation.substring(Operation.indexOf("OP") + 5, div);//Sample: OP3: Poop-Number2
//                String NewMeaning = Operation.substring(div + 1);
//                outcome = SearchWord.AddAWord(ToAdd, NewMeaning);
//            }

            //output.writeUTF("DictionaryServer: Hi Client "+counter+" !!!");
            output.writeUTF(outcome);

            output.flush();
            input.close();
            output.close();
            System.out.println("The outcome: " + outcome + " has been sent back to the client!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
