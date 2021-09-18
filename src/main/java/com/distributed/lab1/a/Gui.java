package com.distributed.lab1.a;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Gui {
    private final JFrame frame;
    private JSlider slider;
    private Thread thread1;
    private Thread thread2;

    public Gui() {
        frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                interruptThreads();
            }
        });
        frame.setPreferredSize(new Dimension(200, 100));
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
        addSlider();
    }

    public void runThreads(int priority1, int priority2) {
        thread1 = new Thread(new SliderValueChanger(slider, 10, priority1 * 100));
        thread1.setDaemon(true);
        thread1.start();

        thread2 = new Thread(new SliderValueChanger(slider, 90, priority2 * 100));
        thread2.setDaemon(true);
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
