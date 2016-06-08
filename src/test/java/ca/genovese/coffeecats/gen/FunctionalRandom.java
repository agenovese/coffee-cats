package ca.genovese.coffeecats.gen;

import ca.genovese.coffeecats.data.option.Option;
import ca.genovese.coffeecats.data.tuple.Tuple2;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A referentially transparent Random Number Generator.
 */
public final class FunctionalRandom {
  /**
   * the default length of the seed for the next random number generator.
   */
  private static final int DEFAULT_SEED_LENGTH = 20;
  /**
   * the length of the seed for the next random number generator.
   */
  private final int seedLength;
  /**
   * The underlying SecureRandom implementation.
   */
  private final SecureRandom rnd;
  /**
   * The memoized "next" value.
   */
  private final AtomicReference<Option<Tuple2<FunctionalRandom, Integer>>> value = new AtomicReference<>(Option.none());

  /**
   * Convenience constructor that generates a Random new SecureRandom to start.
   */
  public FunctionalRandom() {
    this(DEFAULT_SEED_LENGTH, new SecureRandom());
  }

  /**
   * Constructor.
   *
   * @param seedLength the length of the seed for the next random number generator.
   * @param rnd The underlying SecureRandom implementation.
   */
  public FunctionalRandom(final int seedLength, final SecureRandom rnd) {
    this.seedLength = seedLength;
    this.rnd = rnd;
  }

  /**
   * The next value from this FunctionalRandom.
   *
   * @return Return the next FunctionalRandom and a random Integer.
   */
  public Tuple2<FunctionalRandom, Integer> nextInt() {
    return value.updateAndGet((v) -> {
      if (v.isDefined()) {
        return v;
      } else {
        final byte[] seed = new byte[seedLength];
        rnd.nextBytes(seed);
        return Option.some(new Tuple2<>(
            new FunctionalRandom(seedLength, new SecureRandom(seed)),
            rnd.nextInt()));
      }
    }).get();
  }

}
