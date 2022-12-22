package com.irfan.atm.utils;

import java.util.Random;

public class CodeGenerator {

    public CodeGenerator(){}

    public String generateAccountNumber() {
        long seed = System.currentTimeMillis();

        Random rng = new Random(seed);
        long number = (rng.nextLong() % 9000000000000L) + 1000000000000L;

        return String.valueOf(Math.abs(number));
    }


}
