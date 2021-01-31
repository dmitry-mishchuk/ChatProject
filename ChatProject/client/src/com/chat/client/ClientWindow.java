package com.chat.client;

import com.network.TCPConnection;
import com.network.TCPConnectionLisener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectionLisener {

    private static final int PORT = 8189;
    private static final String IP_ADRESS = "localhost";
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
            }
        });
    }
    private final JTextArea log  = new JTextArea();
    private final JTextField fieldNickName = new JTextField("alex");
    private final JTextField fieldInput = new JTextField();
    private TCPConnection connection;

    private ClientWindow(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        log.setEditable(false);
        log.setLineWrap(true);
        add(log, BorderLayout.CENTER);

        fieldInput.addActionListener(this);
        add(fieldInput, BorderLayout.SOUTH);
        add(fieldNickName, BorderLayout.NORTH);

        setVisible(true);
        try {
            connection = new TCPConnection(this, IP_ADRESS, PORT);
        } catch (IOException e) {
            prinMsg("Connection exeption: " + e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg  = fieldInput.getText();
        if(msg.equals("")) return;
        fieldInput.setText(null);
        connection.sendString(fieldNickName.getText() + ": " + msg);
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        prinMsg("Connection ready...");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        prinMsg(value);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        prinMsg("Connection close...");
    }

    @Override
    public void onExeption(TCPConnection tcpConnection, Exception e) {
        prinMsg("Connection exeption: " + e);
    }

    private synchronized void prinMsg (String msg){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }
}

