/*
 * Created by JFormDesigner on Sat Apr 02 00:21:19 GMT+08:00 2022
 */

package src.main;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import org.jdesktop.beansbinding.*;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

/**
 * @author Yuebo Luo
 */
public class MainWindow extends JFrame {
    public MainWindow() {
        initComponents();
    }

    private void createUIComponents() {
        // TODO: add custom component creation code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Yuebo Luo
        panel1 = new JPanel();
        label1 = new JLabel();
        panel2 = new JPanel();
        OperationList = new JComboBox<>();

        //======== this ========
        setTitle("Assignment 1 test");
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border.
            EmptyBorder( 0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border. TitledBorder. CENTER, javax. swing
            . border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ),
            java. awt. Color. red) ,panel1. getBorder( )) ); panel1. addPropertyChangeListener (new java. beans. PropertyChangeListener( )
            { @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062ord\u0065r" .equals (e .getPropertyName () ))
            throw new RuntimeException( ); }} );
            panel1.setLayout(new BorderLayout());

            //---- label1 ----
            label1.setText("please choose the operation you intend to perfrom");
            panel1.add(label1, BorderLayout.NORTH);
        }
        contentPane.add(panel1, BorderLayout.NORTH);

        //======== panel2 ========
        {
            panel2.setLayout(new BorderLayout());

            //---- OperationList ----
            OperationList.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            panel2.add(OperationList, BorderLayout.CENTER);
        }
        contentPane.add(panel2, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Yuebo Luo
    private JPanel panel1;
    private JLabel label1;
    private JPanel panel2;
    String str1="Search";
    String str2="Update";
    String str3="Remove";
    String str4="Add";
    public JComboBox<String> OperationList;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
