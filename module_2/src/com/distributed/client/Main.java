package com.distributed.client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5555);

        var out = new ObjectOutputStream(socket.getOutputStream());
        var in = new ObjectInputStream(socket.getInputStream());

        var ui = new Ui(new RequestSender(in, out, new CustomJsonParser()));
        JFrame frame = new JFrame("Software Client");
        frame.add(ui, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
/*        out.writeInt(1);
        */
    }
}
