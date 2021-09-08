package com.distributed.lab1.a;

record CommonResourceGraduallyChanger(CommonResource resToChange, CommonResource resChangeTo,
                                      int timeToSleep) implements Runnable {

    @Override
    public void run() {
        synchronized (resToChange) {
            var diff = resChangeTo.getValue() > resToChange.getValue() ? 1 : -1;
            try {
                while (resToChange.getValue() != resChangeTo.getValue()) {
                    //noinspection BusyWait
                    Thread.sleep(timeToSleep);
                    resToChange.setValue(resToChange.getValue() + diff);
                    System.out.println(resToChange.getValue());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}