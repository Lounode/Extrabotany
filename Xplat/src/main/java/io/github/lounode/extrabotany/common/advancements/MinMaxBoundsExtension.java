package io.github.lounode.extrabotany.common.advancements;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;

import net.minecraft.advancements.critereon.MinMaxBounds;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Function;

public class MinMaxBoundsExtension {
	public record Longs(Optional<Long> min, Optional<Long> max, Optional<BigInteger> minSq, Optional<BigInteger> maxSq) implements MinMaxBounds<Long> {
		public static final Longs ANY = new Longs(Optional.empty(), Optional.empty());
		public static final Codec<Longs> CODEC = MinMaxBounds.<Long, Longs>createCodec(Codec.LONG, Longs::new);

		public Longs(Optional<Long> min, Optional<Long> max) {
			this(min, max, min.map(Longs::square), max.map(Longs::square));
		}

		private static Longs create(StringReader reader, Optional<Long> min, Optional<Long> max) throws CommandSyntaxException {
			if (min.isPresent() && max.isPresent() && min.get() > max.get()) {
				throw ERROR_SWAPPED.createWithContext(reader);
			}
			return new Longs(min, max);
		}

		private static BigInteger square(long value) {
			return BigInteger.valueOf(value).pow(2);
		}

		public static Longs exactly(long value) {
			return new Longs(Optional.of(value), Optional.of(value));
		}

		public static Longs between(long min, long max) {
			return new Longs(Optional.of(min), Optional.of(max));
		}

		public static Longs atLeast(long min) {
			return new Longs(Optional.of(min), Optional.empty());
		}

		public static Longs atMost(long max) {
			return new Longs(Optional.empty(), Optional.of(max));
		}

		public boolean matches(long value) {
			return this.min.map(minValue -> minValue <= value).orElse(true)
					&& this.max.map(maxValue -> maxValue >= value).orElse(true);
		}

		public boolean matchesSqr(long value) {
			BigInteger bigVal = BigInteger.valueOf(value);
			return this.minSq.map(minValue -> minValue.compareTo(bigVal) <= 0).orElse(true)
					&& this.maxSq.map(maxValue -> maxValue.compareTo(bigVal) >= 0).orElse(true);
		}

		public static Longs fromReader(StringReader reader) throws CommandSyntaxException {
			return fromReader(reader, val -> val);
		}

		public static Longs fromReader(StringReader reader, Function<Long, Long> formatter) throws CommandSyntaxException {
			return MinMaxBounds.fromReader(reader,
					Longs::create,
					Long::parseLong,
					CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidLong,
					formatter);
		}
	}
}
