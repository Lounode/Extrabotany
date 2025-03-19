package io.github.lounode.extrabotany.common.advancements;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import java.math.BigInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;

import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;

public class MinMaxBoundsExtension {
    public static class Longs extends MinMaxBounds<Long> {
        public static final MinMaxBoundsExtension.Longs ANY = new MinMaxBoundsExtension.Longs(null, null);
        @Nullable
        private final BigInteger minSq;
        @Nullable
        private final BigInteger maxSq;

        private static MinMaxBoundsExtension.Longs create(StringReader reader, @Nullable Long min, @Nullable Long max) throws CommandSyntaxException {
            if (min != null && max != null && min > max) {
                throw ERROR_SWAPPED.createWithContext(reader);
            }
            return new MinMaxBoundsExtension.Longs(min, max);
        }

        @Nullable
        private static BigInteger squareOpt(@Nullable Long value) {
            return value == null ? null : BigInteger.valueOf(value).pow(2);
        }

        private Longs(@Nullable Long min, @Nullable Long max) {
            super(min, max);
            this.minSq = squareOpt(min);
            this.maxSq = squareOpt(max);
        }

        public static MinMaxBoundsExtension.Longs exactly(long value) {
            return new MinMaxBoundsExtension.Longs(value, value);
        }

        public static MinMaxBoundsExtension.Longs between(long min, long max) {
            return new MinMaxBoundsExtension.Longs(min, max);
        }

        public static MinMaxBoundsExtension.Longs atLeast(long min) {
            return new MinMaxBoundsExtension.Longs(min, null);
        }

        public static MinMaxBoundsExtension.Longs atMost(long max) {
            return new MinMaxBoundsExtension.Longs(null, max);
        }

        public boolean matches(long value) {
            if (this.min != null && this.min > value) return false;
            return this.max == null || this.max >= value;
        }

        public boolean matchesSqr(long value) {
            BigInteger bigVal = BigInteger.valueOf(value);
            if (this.minSq != null && this.minSq.compareTo(bigVal) > 0) return false;
            return this.maxSq == null || this.maxSq.compareTo(bigVal) >= 0;
        }

        public static MinMaxBoundsExtension.Longs fromJson(@Nullable JsonElement element) {
            return fromJson(element, ANY, GsonHelper::convertToLong, MinMaxBoundsExtension.Longs::new);
        }

        public static MinMaxBoundsExtension.Longs fromReader(StringReader reader) throws CommandSyntaxException {
            return fromReader(reader, val -> val);
        }

        public static MinMaxBoundsExtension.Longs fromReader(StringReader reader, Function<Long, Long> formatter) throws CommandSyntaxException {
            return fromReader(reader,
                    Longs::create,
                    Long::parseLong,
                    CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidLong,
                    formatter);
        }
    }
}
