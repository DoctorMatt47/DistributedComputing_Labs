package com.distributed.lab1.a;

record SliderValueChanger(CommonResource resToChange, int valueChangeTo, int timeToSleep) implements Runnable {

    @Override
    public synchronized void run() {
        var slider = resToChange.getSlider();
        var diff = valueChangeTo > slider.getValue() ? 1 : -1;
        while (slider.getValue() != valueChangeTo) {
            try {
                //noinspection BusyWait
                Thread.sleep(timeToSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (resToChange.isCompleted()) return;
            slider.setValue(slider.getValue() + diff);
        }
        resToChange.complete();
    }
}