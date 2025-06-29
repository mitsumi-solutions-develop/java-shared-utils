package io.github.mitsumi.solutions.shared.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * UUID Utils class.
 *
 * @author mitsumi.solutions
 */
@UtilityClass
public class UUIDUtils {

    /**
     * Generate 36 digit uuid.
     *
     * @return uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate 32 digit uuid.
     *
     * @return uuid
     */
    public static String uuid32() {
        return uuid().replace("-", "");
    }

    /**
     * Generate 64 digit uuid.
     *
     * @return uuid
     */
    public static String uuid64() {
        return "%s%s".formatted(uuid32(), uuid32());
    }
}
