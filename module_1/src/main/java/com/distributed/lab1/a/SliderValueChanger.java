package com.distributed.lab1.a;

import javax.swing.*;

record SliderValueChanger(JSlider slider, int valueChangeTo, int timeToSleep) implements Runnable {

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (slider) {
                    var diff = Integer.compare(valueChangeTo, slider.getValue());

                    //noinspection BusyWait
                    Thread.sleep(timeToSleep);
                    slider.setValue(slider.getValue() + diff);
                }
            }
        } catch (InterruptedException ignored) {
        }
    }
}