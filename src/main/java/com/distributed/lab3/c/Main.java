package com.distributed.lab3.c;

public class Main {
    public static void main(String[] args) {
        SmokersMediator.ingredients.put(Ingredient.TOBACCO, new CustomSemaphore(0));
        SmokersMediator.ingredients.put(Ingredient.PAPER, new CustomSemaphore(0));
        SmokersMediator.ingredients.put(Ingredient.MATCHES, new CustomSemaphore(0));

        new Thread(SmokersMediator.dealer).start();
        new Thread(new Smoker(Ingredient.TOBACCO)).start();
        new Thread(new Smoker(Ingredient.PAPER)).start();
        new Thread(new Smoker(Ingredient.MATCHES)).start();
    }
}
