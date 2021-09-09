package com.distributed.lab1.a;

import javax.swing.*;
import java.awt.*;

public class Gui {
    private final JFrame frame;
    private final JSlider slider;

    public Gui() {
        frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(200, 100));
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
        slider = new JSlider(0, 100);
        frame.add(slider);
    }

    public JSlider getSlider() {
        return slider;
    }
}
