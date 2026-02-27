package id.my.ridwanadhip.ticketbooking.util;

import java.time.Instant;

public class StringGenerator {
    // TODO: this is placeholder, need to change the generator with random string method
    public static String randomString() {
        var timestamp = Instant.now().getEpochSecond();
        return "%d".formatted(timestamp);
    }

    public static String randomSerial(String prefix, String suffix) {
        return "%s-%s-%s".formatted(prefix, randomString(), suffix);
    }
}
