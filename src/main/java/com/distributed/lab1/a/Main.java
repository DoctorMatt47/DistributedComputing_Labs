package com.distributed.lab1.a;

public class Main {

    public static void main(String[] args) {
        var gui = new Gui();

        var commonResources = new CommonResource(gui.getSlider());
        new Thread(new SliderValueChanger(commonResources, 10, 100)).start();
        new Thread(new SliderValueChanger(commonResources, 90, 200)).start();
    }
}
