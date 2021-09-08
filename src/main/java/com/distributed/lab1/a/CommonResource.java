package com.distributed.lab1.a;

import javax.swing.*;

public class CommonResource {
    private final JSlider slider;
    private Boolean isCompleted = false;

    public CommonResource(JSlider sliderToChange) {
        this.slider = sliderToChange;
    }

    public JSlider getSlider() {
        return slider;
    }

    public Boolean isCompleted() {
        return isCompleted;
    }

    public void complete() {
        isCompleted = true;
    }
}
