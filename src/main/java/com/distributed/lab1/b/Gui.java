package com.distributed.lab1.b;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Gui {
    private final JFrame frame;
    private JSlider slider;
    private final CommonResource commonResource;
    private JLabel message;
    private Thread thread1;
    private Thread thread2;

    public Gui() {
        frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //interruptThreads();
            }
        });
        frame.setLayout(new FlowLayout());
        frame.setPreferredSize(new Dimension(600, 120));
        frame.setResizable(false);

        addSlider();
        addButtons();
        addLabel();

        frame.setVisible(true);
        frame.pack();

        commonResource = new CommonResource(slider, message);
    }

    private void addLabel() {
        message = new JLabel("Message placeholder");
        message.setVisible(true);
        frame.add(message);
    }

    public JSlider getSlider() {
        return slider;
    }

    private void addSlider() {
        slider = new JSlider(0, 100);
        frame.add(slider);
    }

    private void addButtons() {
        var start1 = new JButton("Start1");
        var start2 = new JButton("Start2");
        var stop1 = new JButton("Stop1");
        var stop2 = new JButton("Stop2");
        stop1.setEnabled(false);
        stop2.setEnabled(false);

        start1.addActionListener(e -> {
            start(1);
            start1.setEnabled(false);
            stop1.setEnabled(true);
        });

        start2.addActionListener(e -> {
            start(2);
            start2.setEnabled(false);
            stop2.setEnabled(true);
        });

        stop1.addActionListener(e -> {
            stop(1);
            start1.setEnabled(true);
            stop1.setEnabled(false);
        });

        stop2.addActionListener(e -> {
            stop(2);
            start2.setEnabled(true);
            stop2.setEnabled(false);
        });

        frame.add(start1);
        frame.add(start2);
        frame.add(stop1);
        frame.add(stop2);
    }

    private void stop(int id) {
        if (id == 1) {
            thread1.interrupt();
        }
        if (id == 2) {
            thread2.interrupt();
        }
    }

    public void start(int id) {
        if (id == 1) {
            thread1 = new Thread(new SliderValueChanger(commonResource, 10, 100));
            thread1.setDaemon(true);
            thread1.setPriority(1);
            thread1.start();
        }
        else if (id == 2) {
            thread2 = new Thread(new SliderValueChanger(commonResource, 90, 100));
            thread2.setDaemon(true);
            thread2.setPriority(10);
            thread2.start();
        }
    }
}
