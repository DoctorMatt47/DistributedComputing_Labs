package com.distributed.lab3.c;

public class Smoker implements Runnable {
    private final Ingredient myIngredient;

    public Smoker(Ingredient hasIngredient) {
        this.myIngredient = hasIngredient;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Takes table control
                SmokersMediator.tableReady.acquire();
            } catch (InterruptedException ignored) {
            }

            boolean canMakeCigarette = true;
            for (var ingredient : Ingredient.values()) {
                if (ingredient != myIngredient
                        && SmokersMediator.ingredients.get(ingredient).availablePermits() == 0) {
                    canMakeCigarette = false;
                    break;
                }
            }

            if (!canMakeCigarette) {
                SmokersMediator.tableReady.release();
                continue;
            }

            for (var ingredientType : Ingredient.values()) {
                if (ingredientType != myIngredient) {
                    try {
                        SmokersMediator.ingredients.get(ingredientType).acquire();
                    } catch (InterruptedException ignored) {
                    }
                }
            }
            SmokersMediator.tableReady.release();

            System.out.printf("Smoker with %s: Got ingredients, starting to smoke\n", myIngredient);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }

            System.out.printf("Smoker with %s: Finished smoking.\n", myIngredient);
            SmokersMediator.smokingDone.release();
        }
    }
}