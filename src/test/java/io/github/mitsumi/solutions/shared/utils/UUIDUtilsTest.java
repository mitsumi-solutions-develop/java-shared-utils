package io.github.mitsumi.solutions.shared.utils;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UUIDUtilsTest {

    @Test
    public void test_uuid() {
        var actual = UUIDUtils.uuid();

        assertThat(actual.length(), is(36));
    }

    @Test
    public void test_uuid32() {
        var actual = UUIDUtils.uuid32();

        assertThat(actual.length(), is(32));
    }

    @Test
    public void test_uuid64() {
        var actual = UUIDUtils.uuid64();

        assertThat(actual.length(), is(64));
    }
}
