package io.github.lounode.extrabotany.mixinutils;

import java.math.BigInteger;

public class ManaAccumulator {
    private static final ThreadLocal<BigInteger> totalMana = ThreadLocal.withInitial(() -> BigInteger.valueOf(0));
    private static final ThreadLocal<BigInteger> totalMaxMana = ThreadLocal.withInitial(() -> BigInteger.valueOf(0));

    public static void accumulate(BigInteger mana, BigInteger max) {
        totalMana.set(totalMana.get().add(mana));
        totalMaxMana.set(totalMaxMana.get().add(max));
    }

    public static BigInteger getTotalMana() {
        return totalMana.get();
    }

    public static BigInteger getTotalMaxMana() {
        return totalMaxMana.get();
    }

    public static void reset() {
        totalMana.set(BigInteger.valueOf(0));
        totalMaxMana.set(BigInteger.valueOf(0));
    }
}