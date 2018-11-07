/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientView extends JFrame implements ActionListener {

    private JTextField txtUsername, nick, nick1, message;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnSend,exit;
    private JPanel p_login, p_chat, jPanel1, jPanel2, jPanel3, jPanel4;
    private JTextArea msg, online;

    public void setMsg(String msg) {
        this.msg.append(msg);
    }

    public ClientView() {
        super("TCP Login MVC");
        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        txtPassword.setEchoChar('*');
        btnLogin = new JButton("Login");
        JPanel JFrmChat=new JPanel();
        JFrmChat.setLayout(new FlowLayout());
        JFrmChat.add(new JLabel("ROOM 49"));
        jPanel1 = new JPanel();
        jPanel1.setLayout(new FlowLayout());
        jPanel1.add(new JLabel("Username:"));
        jPanel1.add(txtUsername);
        jPanel1.add(new JLabel("Password:"));
        jPanel1.add(txtPassword);
        jPanel1.add(btnLogin);
        //----------------------------------
        p_chat = new JPanel();
        p_chat.setLayout(new BorderLayout());
        
        jPanel2 = new JPanel();
        jPanel2.setLayout(new BorderLayout());
        msg = new JTextArea(10, 10);
        msg.setEditable(false);
        jPanel2.add(new JScrollPane(msg));
        //---------------------------------
        jPanel3 = new JPanel();
        jPanel3.setLayout(new BorderLayout());
        JPanel p22 = new JPanel();
        p22.setLayout(new FlowLayout(FlowLayout.CENTER));
        p22.add(new JLabel("Danh sách online"));
        jPanel3.add(p22, BorderLayout.NORTH);

        online = new JTextArea(10, 10);
        online.setEditable(false);
        jPanel3.add(new JScrollPane(online), BorderLayout.CENTER);
        //---------------------------------
        jPanel4= new JPanel();
        jPanel4.setBackground(Color.darkGray);
        jPanel4.setLayout(new FlowLayout(FlowLayout.LEFT));
        btnSend = new JButton("Send");
        jPanel4.add(new JLabel("Tin nhắn"));
        message = new JTextField(30);
        jPanel4.add(message);
        jPanel4.add(btnSend);
        //---------------------------------
        p_login = new JPanel();
        p_login.setLayout(new BorderLayout());
        p_login.add(jPanel1, BorderLayout.NORTH);
        p_chat.add(JFrmChat, BorderLayout.NORTH);
        p_chat.add(jPanel2, BorderLayout.CENTER);
        p_chat.add(jPanel3, BorderLayout.EAST);
        p_chat.add(jPanel4, BorderLayout.SOUTH);
        this.add(p_login, BorderLayout.NORTH);
        this.add(p_chat, BorderLayout.CENTER);
        p_chat.setVisible(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setSize(600, 400);

    }

    public String getMessage() {
        return this.message.getText();
    }

    public void setP_login(boolean bl) {
        this.p_login.setVisible(bl);
    }

    public void setP_chat(boolean bl) {
        this.p_chat.setVisible(bl);
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }

    public void actionPerformed(ActionEvent e) {
    }

    public User getUser() {
        User model = new User(txtUsername.getText(),
                txtPassword.getText());
        return model;
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void addLoginListener(ActionListener log) {
        btnLogin.addActionListener(log);
        txtUsername.addActionListener(log);
        txtPassword.addActionListener(log);
    }
    
    public void addSendListener(ActionListener send) {
        btnSend.addActionListener(send);
        message.addActionListener(send);
    }
}
