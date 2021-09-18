package com.distributed.lab1.a;

import javax.swing.*;
import java.awt.*;

public class Gui {
    private final JFrame frame;
    private JSlider slider;
    private Thread thread1;
    private Thread thread2;
    private JButton start;
    private JButton stop;

    public Gui() {
        frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setPreferredSize(new Dimension(200, 100));
        frame.setVisible(true);
        frame.setResizable(false);
        addSlider();
        addButtons();

        frame.pack();
    }

    private void addButtons() {
        start = new JButton("start");
        start.addActionListener(e -> runThreads(10, 1));
        frame.add(start);

        stop = new JButton("stop");
        stop.addActionListener(e -> interruptThreads());
        frame.add(stop);
    }
    

    public void runThreads(int priority1, int priority2) {
        thread1 = new Thread(new SliderValueChanger(slider, 10, 70));
        thread1.setDaemon(true);
        thread1.setPriority(priority1);

        thread2 = new Thread(new SliderValueChanger(slider, 90, 70));
        thread2.setDaemon(true);
        thread2.setPriority(priority2);

        thread1.start();
        thread2.start();
    }

    public void interruptThreads() {
        thread1.interrupt();
        thread2.interrupt();
    }

    private void addSlider() {
        slider = new JSlider(0, 100);
        frame.add(slider);
    }

}
