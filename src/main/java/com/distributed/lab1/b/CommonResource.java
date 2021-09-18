package com.distributed.lab1.b;

import javax.swing.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class CommonResource {
    private final JSlider slider;
    private JLabel messageLabel;
    private volatile int semaphore;

    public CommonResource(JSlider sliderToChange, JLabel messageLabel) {
        this.slider = sliderToChange;
        this.messageLabel = messageLabel;
        this.semaphore = 0;
    }

    public JSlider getSlider() {
        return slider;
    }

    public int getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(int semaphore) {
        this.semaphore = semaphore;
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }
}
