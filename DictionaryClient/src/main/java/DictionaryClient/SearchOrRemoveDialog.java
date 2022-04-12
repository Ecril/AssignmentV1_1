package DictionaryClient;

import javax.swing.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SearchOrRemoveDialog extends JDialog {
    private static String ip = DictionaryClient.Ip;
    private static int port = DictionaryClient.Port;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField TxtForSearchOrRemove;
    private JTextPane FromServer;

    public SearchOrRemoveDialog(int OP) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                onOK(OP);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // 点击 X 时调用 onCancel()
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // 遇到 ESCAPE 时调用 onCancel()
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public SearchOrRemoveDialog() {

    }

    public static void main(String[] args) {
        SearchOrRemoveDialog dialog = new SearchOrRemoveDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onOK(int OP) {///在此处添加您的代码
        // IP and port
        try (Socket socket = new Socket(ip, port);) {
            // Output and Input Stream
            DataInputStream input = new DataInputStream(socket.getInputStream());

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            String op = null;
            if (OP == 0) {
                op = "OP0: ";
            } else if (OP == 1) {
                op = "OP1: ";
            }//
            String wd = TxtForSearchOrRemove.getText();
            String sendData = op + wd;

            output.writeUTF(sendData);
            System.out.println("Data sent to DictionaryServer--> " + sendData);
            output.flush();
            buttonOK.getDisabledIcon();
            boolean flag = true;
            while (flag) {
                if (input.available() > 0) {
                    String message = input.readUTF();
                    FromServer.setText(message);
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


        // dispose();
    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }
}
