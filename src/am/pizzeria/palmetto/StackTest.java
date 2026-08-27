package am.pizzeria.palmetto;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class StackTest {

    public static void main(String[] args) {

        Stack objectStack = new Stack(12);

    }

    private static Optional<String> getObject() {

        return ThreadLocalRandom.current().nextBoolean()
                ? Optional.of("Hello")
                : Optional.empty();
    }
}
