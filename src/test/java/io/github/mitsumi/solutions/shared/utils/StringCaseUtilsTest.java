package io.github.mitsumi.solutions.shared.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StringCaseUtilsTest {

    @ParameterizedTest
    @CsvSource({
        "test_snake_to_camel, testSnakeToCamel"
    })
    public void test_snakeToCamel_initialLetterLower_true(final String snakeCase, final String expected) {
        var actual = StringCaseUtils.snakeToCamel(snakeCase);

        assertThat(actual, is(expected));
    }

    @ParameterizedTest
    @CsvSource({
        "test_snake_to_camel, TestSnakeToCamel"
    })
    public void test_snakeToCamel_initialLetterLower_false(final String snakeCase, final String expected) {
        var actual = StringCaseUtils.snakeToCamel(snakeCase, false);

        assertThat(actual, is(expected));
    }

    @ParameterizedTest
    @CsvSource({
        "testSnakeToCamel, test_snake_to_camel"
    })
    public void test_camelToSnake(final String snakeCase, final String expected) {
        var actual = StringCaseUtils.camelToSnake(snakeCase);

        assertThat(actual, is(expected));
    }
}
