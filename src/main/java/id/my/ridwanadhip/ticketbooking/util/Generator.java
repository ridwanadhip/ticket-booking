package id.my.ridwanadhip.ticketbooking.util;

import java.time.Instant;

public class Generator {
    // TODO: this is placeholder, need to change the generator with random string method
    public static String generateRandomString() {
        var timestamp = Instant.now().getEpochSecond();
        return "%d".formatted(timestamp);
    }

    public static String generateSerial(String prefix, String suffix) {
        return "%s-%s-%s".formatted(prefix, generateRandomString(), suffix);
    }
}
