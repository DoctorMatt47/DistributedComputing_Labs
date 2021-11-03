package com.distributed.lab2.c;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class Tournament extends RecursiveTask<Integer> {
    private final ArrayList<Integer> monkXies;
    private final int start;
    private final int end;

    public Tournament(ArrayList<Integer> monkXies) {
        this.monkXies = monkXies;
        this.start = 0;
        this.end = monkXies.size() - 1;
    }

    private Tournament(ArrayList<Integer> monkXies, int startIndex, int endIndex) {
        this.monkXies = monkXies;
        this.start = startIndex;
        this.end = endIndex;
    }

    @Override
    protected Integer compute() {
        if (end == start)
            return start;

        if (end == start + 1)
            return getWinner(start, end);

        int mid = (start + end) / 2;

        var subTourFirst = new Tournament(monkXies, start, mid);
        var subTourSecond = new Tournament(monkXies, mid + 1, end);

        subTourFirst.fork();
        subTourSecond.fork();
        return getWinner(subTourFirst.join(), subTourSecond.join());
    }

    private int getWinner(int firstMonk, int secondMonk) {
        boolean isFirstWon = monkXies.get(firstMonk) > monkXies.get(secondMonk);
        System.out.printf("Monk %d with Xi: %d %s monk %d with Xi: %d\n",
                firstMonk, monkXies.get(firstMonk),
                isFirstWon ? "wins" : "loses to",
                secondMonk, monkXies.get(secondMonk));

        return isFirstWon ? firstMonk : secondMonk;
    }
}
