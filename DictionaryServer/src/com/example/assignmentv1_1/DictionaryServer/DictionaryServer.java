package com.example.assignmentv1_1.DictionaryServer;

import javax.net.ServerSocketFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.BindException;
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
            //根据参数列表长度选定。无参数时默认3005端口启动，用同目录下的dict.txt做字典文件
            // 1参数时，识别输入参数是端口号还是字典文件。
            int i = args.length;
            switch (i) {
                case 1:
                    //假定参数0为端口号
                    try {
                        int PotentialPort = Integer.parseInt(args[0]);
                        if (PotentialPort > 65535 || PotentialPort < 1) {
                            throw new NumberFormatException();//端口号不合法，抛NumberFormatException，保持默认端口号
                        } else {
                            port = PotentialPort;//输入的端口号合法，修改静态端口号
                        }
                    } catch (NumberFormatException e) {
                        //如果抛了NumberFormatException说明不是合法端口号，视为文件路径进行尝试
                        file = new File(args[0]);
                        if ((file.exists() && file.isFile())) {
                            Filepath = args[0];//文件路径合法则修改静态Filepath
                        } else {
                            //如果输入的单个参数既不是端口号也不是文件，往上继续抛Exception
                            throw new IllegalArgumentException();
                        }
                    }
                    //最后记得打开文件
                    file = new File(Filepath);
                    //必须break
                    break;
                case 2:
                    //解析端口号 第一个参数不是数字会抛NumberFormatException
                    port = Integer.parseInt(args[0]);
                    //判断参数0代表的端口是否在1-65535之间，不在的话也抛NumberFormatException
                    if (port > 65535 || port < 1) {
                        throw new NumberFormatException();
                    }
                    //把参数1定位文件路径,不break，进入下一步default
                    Filepath = args[1];
                default:
                    //其它情况用默认参数启动
                    file = new File(Filepath);
                    //判断文件是否存在，不存在手工抛 SecurityException，统一处理
                    if (!(file.exists() && file.isFile())) {
                        throw new SecurityException();
                    }
            }
        } catch (NumberFormatException e) {
            System.out.println("Illegal Port Number, it must be an Integer between 1-65535");
            System.exit(0);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal Argument, Please input 0-2 legal arguments");
            System.exit(0);
        } catch (SecurityException e) {
            System.out.println("Unable to access the Dictionary File");
            System.exit(0);
        }

        ServerSocketFactory factory = ServerSocketFactory.getDefault();

        try {
            ServerSocket server = factory.createServerSocket(port);
            //将文件加载到hashmap内存中
            Dictionary.readDicFile(Filepath);

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

        } catch (BindException e) {
            System.out.println("Address already in use");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void serveClient(Socket client) {
        try {
            // Input stream
            DataInputStream input = new DataInputStream(client.getInputStream());
            // Output Stream
            DataOutputStream output = new DataOutputStream(client.getOutputStream());
            String outcome;
            String Operation = input.readUTF();

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
                    outcome = "Connection Success\n";
                    System.out.println("New Connection Detected\n");
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
//            System.out.println("The outcome: " + outcome + " has been sent back to the client!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
