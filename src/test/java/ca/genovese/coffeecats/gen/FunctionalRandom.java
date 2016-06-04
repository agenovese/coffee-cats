package ca.genovese.coffeecats.gen;

import ca.genovese.coffeecats.data.tuple.Tuple2;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicReference;

public class FunctionalRandom {
  private static final int DEFAULT_SEED_LENGTH = 20;
  private final int seedLength;
  private final SecureRandom rnd;
  private final AtomicReference<Tuple2<Integer, FunctionalRandom>> value = new AtomicReference<>(null);

  public FunctionalRandom() {
    this(DEFAULT_SEED_LENGTH, new SecureRandom());
  }

  public FunctionalRandom(final int seedLength, final SecureRandom rnd) {
    this.seedLength = seedLength;
    this.rnd = rnd;
  }

  public Tuple2<Integer, FunctionalRandom> nextInt() {
    return value.updateAndGet((v) -> {
      if (v != null) return v;
      else {
        byte[] seed = new byte[seedLength];
        rnd.nextBytes(seed);
        return new Tuple2<>(rnd.nextInt(), new FunctionalRandom(seedLength, new SecureRandom(seed)));
      }
    });
  }

}
