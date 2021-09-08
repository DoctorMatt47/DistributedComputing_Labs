package com.distributed.lab1.a;

public class Main {
    public static void main(String[] args) {
        var resToChange = new CommonResource(50);
        var resChangeTo1 = new CommonResource(10);
        var resChangeTo2 = new CommonResource(90);
        var thread1 = new Thread(new CommonResourceGraduallyChanger(resToChange, resChangeTo1, 200));
        var thread2 = new Thread(new CommonResourceGraduallyChanger(resToChange, resChangeTo2, 50));
        thread1.start();
        thread2.start();
    }
}
