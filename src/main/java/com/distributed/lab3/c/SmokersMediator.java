package com.distributed.lab3.c;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SmokersMediator {
    public static final CustomSemaphore tableReady = new CustomSemaphore(1);
    public static final CustomSemaphore smokingDone = new CustomSemaphore(1);
    public static final Map<Ingredient, CustomSemaphore> ingredients = new ConcurrentHashMap<>();
    public static final Runnable dealer = () -> {
        while (true) {
            try {
                // Waiting when smoking will end
                smokingDone.acquire();

                // Takes table control
                tableReady.acquire();
            } catch (InterruptedException ignored) {
            }

            int excludedIngredient = (int) (Math.random() * 3);

            for (var ingredient : Ingredient.values()) {
                if (excludedIngredient != ingredient.ordinal()) {
                    System.out.printf("Dealer: Put %s on the table\n", ingredient);

                    // Puts ingredient on the table
                    ingredients.get(ingredient).release();
                }
            }
            tableReady.release();
        }
    };
}
