package com.example.assignmentv1_1.DictionaryClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;

public class DictionaryClient {
    public static int Port = 3005;
    public static String Ip = "localhost";
    public int OpIndex = 0;
    private JButton button1;
    private JComboBox<String> OPlist;
    private JPanel TestForm;
    private JPanel PanelForLabel;
    private JPanel PanelForTextIn;
    private JPanel ForButton;
    private JLabel Label1;

    public DictionaryClient() {


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Op = (String) OPlist.getSelectedItem();
                if (Objects.equals(Op, "Search")) {
                    OpIndex = 0;
                    System.out.println("Given OP = Search");
                    SearchOrRemoveDialog D1 = new SearchOrRemoveDialog(OpIndex);
                    D1.setSize(400, 250);
                    D1.setVisible(true);

                } else if (Objects.equals(Op, "Remove")) {
                    OpIndex = 1;
                    System.out.println("Given OP = Remove");
                    SearchOrRemoveDialog D2 = new SearchOrRemoveDialog(OpIndex);
                    D2.setSize(400, 250);
                    D2.setVisible(true);

                } else if (Objects.equals(Op, "Update")) {
                    OpIndex = 2;
                    System.out.println("Given OP = Update");
                    UpdateOrAddDialog D3 = new UpdateOrAddDialog(OpIndex);
                    D3.setSize(400, 250);
                    D3.setVisible(true);

                } else if (Objects.equals(Op, "Add")) {
                    OpIndex = 3;
                    System.out.println("Given OP = Add");
                    UpdateOrAddDialog D4 = new UpdateOrAddDialog(OpIndex);
                    D4.setSize(400, 250);
                    D4.setVisible(true);
                }
            }
        });
    }


//    private void createUIComponents() {
//         // TODO: place custom component creation code here
//        OPlist.addItem("Search a word");
//        OPlist.addItem("Update a word");
//        OPlist.addItem("Remove a word");
//        OPlist.addItem("Add a word");
//    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("com/example/assignmentv1_1/DictionaryClient");
        frame.setContentPane(new DictionaryClient().TestForm);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        try {
            if (args.length == 2) {
                Ip = args[0];
                Port = Integer.parseInt(args[1]);
                if (Port > 65535 || Port < 1) {
                    throw new IllegalArgumentException();
                }
            }
            Socket socket = new Socket(Ip, Port);
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            output.writeUTF("Connection Detection");
            output.flush();
// 建议把下面所有的错误输出写成消息弹窗输出到GUI上 我不会
        } catch (NumberFormatException e) {
            System.out.println("Wrong Port Number");
            System.exit(0);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal Port Number");
            System.exit(0);
        } catch (ConnectException e) {
//                    e.printStackTrace();
            System.out.println("Connection Refused");
        } catch (UnknownHostException e) {
//                    e.printStackTrace();
            System.out.println("Connection Refused");

        } catch (IOException e) {
//                    e.printStackTrace();
            System.out.println("Can't creat the socket connection");
        }

    }
}
