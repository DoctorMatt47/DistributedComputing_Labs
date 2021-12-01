package com.distributed.lab8;


import com.distributed.lab8.infrastructure.container.DependencyContainer;

public class Main {
    public static void main(String[] args) {
        var container = new DependencyContainer();
        container.buildController();
    }
}
