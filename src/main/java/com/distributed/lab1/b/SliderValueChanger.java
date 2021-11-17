package com.distributed.lab1.b;

record SliderValueChanger(CommonResource resToChange, int valueChangeTo, int timeToSleep) implements Runnable {

    @Override
    public void run() {
        if (resToChange.getSemaphore() != 0) {
            resToChange.setMessage("Busy with the thread");
        }

        //noinspection StatementWithEmptyBody
        while (resToChange.getSemaphore() != 0) {
        }
        resToChange.setSemaphore(1);
        try {
            var slider = resToChange.getSlider();

            //noinspection InfiniteLoopStatement
            while (true) {
                synchronized (resToChange) {
                    var diff = Integer.compare(valueChangeTo, slider.getValue());

                    //noinspection BusyWait
                    Thread.sleep(timeToSleep);
                    slider.setValue(slider.getValue() + diff);
                }
            }
        } catch (InterruptedException ignored) {
        }
        resToChange.setSemaphore(0);
        resToChange.setMessage("Thread is released");
    }
}