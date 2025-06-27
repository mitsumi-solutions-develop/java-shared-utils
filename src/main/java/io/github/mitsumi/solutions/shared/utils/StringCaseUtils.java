package io.github.mitsumi.solutions.shared.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * StringCaseUtils class.
 *
 * @author mitsumi.solutions
 */
@UtilityClass
public class StringCaseUtils {

    /**
     * Convert snake case to camel case.
     *
     * @param snake snake case.
     * @return camel case.
     */
    public static String snakeToCamel(final String snake) {
        return snakeToCamel(snake, true);
    }

    /**
     * Convert snake case to camel case.
     *
     * @param snake snake case.
     * @param initialLetterLowerCase initial letter lower case
     * @return camel case.
     */
    public static String snakeToCamel(final String snake, final boolean initialLetterLowerCase) {
        if (StringUtils.isEmpty(snake)) {
            return snake;
        }

        final var camel = new StringBuilder(snake.length() + snake.length());
        if (initialLetterLowerCase) {
            camel.append(Character.toLowerCase(snake.charAt(0)));
        }


        for (int i = initialLetterLowerCase ? 1 : 0; i < snake.length(); i++) {
            final char c = snake.charAt(i);

            if (c == '_') {
                camel.append((i + 1) < snake.length() ? Character.toUpperCase(snake.charAt(++i)) : "");
            } else {
                camel.append(camel.isEmpty() ? Character.toUpperCase(c) : Character.toLowerCase(c));
            }

        }
        return camel.toString();
    }


    /**
     * Convert camel case to snake case.
     *
     * @param camel camel case.
     * @return snake case.
     */
    public static String camelToSnake(final String camel) {
        if (StringUtils.isEmpty(camel)) {
            return camel;
        }

        final var snake = new StringBuilder();
        // Append the first character in lowercase
        snake.append(Character.toLowerCase(camel.charAt(0)));

        for (int i = 1; i < camel.length(); i++) {
            char currentChar = camel.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                // Insert an underscore before uppercase letters
                snake.append('_');
                snake.append(Character.toLowerCase(currentChar));
            } else {
                snake.append(currentChar);
            }
        }
        return snake.toString();
    }
}
