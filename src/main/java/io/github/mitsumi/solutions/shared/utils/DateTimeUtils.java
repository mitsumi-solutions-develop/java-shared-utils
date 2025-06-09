package io.github.mitsumi.solutions.shared.utils;

import lombok.experimental.UtilityClass;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

/**
 * DateTimeUtils.
 *
 * @author mitsumi.kaneyama
 */
@SuppressWarnings({"PMD.UseExplicitTypes", "PMD.TooManyMethods"})
@UtilityClass
public final class DateTimeUtils {

    /**
     * UTC ZONE ID.
     */
    public static final ZoneId ZONE_ID_UTC = ZoneId.of("UTC");

    /**
     * JST ZONE ID.
     */
    public static final ZoneId ZONE_ID_JST = ZoneId.of("Asia/Tokyo");

    /**
     * Return system utc date time.
     *
     * @return utc date time
     */
    public static LocalDateTime getUtcTime() {
        return LocalDateTime.now(ZONE_ID_UTC);
    }

    /**
     * Return system jst date time.
     *
     * @return jst date time
     */
    public static LocalDateTime getJstTime() {
        return LocalDateTime.now(ZONE_ID_JST);
    }

    /**
     * Return system zoned utc date time.
     *
     * @return system zoned date time
     */
    public static ZonedDateTime getUtcZonedDateTime() {
        return getUtcTime().atZone(ZONE_ID_UTC);
    }

    /**
     * Return system offset date time.
     *
     * @return system offset date time
     */
    public static OffsetDateTime getOffsetDateTime() {
        return OffsetDateTime.now();
    }

    /**
     * Return utc epoch second.
     *
     * @return utc epoch second
     */
    public static long getUtcEpochSecond() {
        return getUtcZonedDateTime().toEpochSecond();
    }

    /**
     * Determine if there is the same.
     *
     * @param source Comparison source
     * @param target Comparison target
     * @return {@code boolean}
     */
    public static boolean isSame(final LocalDateTime source, final LocalDateTime target) {
        return source.equals(target);
    }

    /**
     * Determine if there is the same.
     *
     * @param source Comparison source
     * @param target Comparison target
     * @return {@code boolean}
     */
    public static boolean isSame(final OffsetDateTime source, final LocalDateTime target) {
        return isSame(source.toLocalDateTime(), target);
    }

    /**
     * Determine if there is the same.
     *
     * @param source Comparison source
     * @param target Comparison target
     * @return {@code boolean}
     */
    public static boolean isSameMs(final LocalDateTime source, final LocalDateTime target) {
        final var compareUnit = ChronoUnit.MILLIS;
        return source.truncatedTo(compareUnit).equals(target.truncatedTo(compareUnit));
    }


    /**
     * Determine there are in the range.
     *
     * @param target       target
     * @param fromDateTime from
     * @param endDateTime  end
     * @return {@code boolean}
     */
    public static boolean isBetween(final LocalDateTime target,
                                    final LocalDateTime fromDateTime,
                                    final LocalDateTime endDateTime) {
        return !target.isAfter(fromDateTime) && !target.isBefore(endDateTime);
    }

    /**
     * Determine there are in the range.
     *
     * @param target       target
     * @param fromDateTime from
     * @param endDateTime  end
     * @return {@code boolean}
     */
    public static boolean isBetween(final OffsetDateTime target,
                                    final LocalDateTime fromDateTime,
                                    final LocalDateTime endDateTime) {
        return isBetween(target.toLocalDateTime(), fromDateTime, endDateTime);
    }

    /**
     * Get OffsetDateTime of UTC Zone.
     *
     * @param year year
     * @param month month
     * @param dayOfMonth day of month
     * @param hour hour
     * @param minute minute
     * @param second second
     * @return {@code OffsetDateTime}
     */
    public static OffsetDateTime getOffsetDateTimeUtc(final int year,
                                                      final int month,
                                                      final int dayOfMonth,
                                                      final int hour,
                                                      final int minute,
                                                      final int second) {
        return OffsetDateTime.of(
            year, month, dayOfMonth, hour, minute, second, 0,
            ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now())
        ).withOffsetSameInstant(ZoneOffset.UTC);
    }

    /**
     * Get OffsetDateTime of JST Zone.
     *
     * @param year year
     * @param month month
     * @param dayOfMonth dayOfMonth
     * @param hour hour
     * @param minute minute
     * @param second second
     * @return {@code OffsetDateTime}
     */
    public static OffsetDateTime getUtcOffsetDateTimeFromJst(final int year,
                                                             final int month,
                                                             final int dayOfMonth,
                                                             final int hour,
                                                             final int minute,
                                                             final int second) {
        final var jstLocalDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);
        final var jstZonedDateTime = jstLocalDateTime.atZone(ZONE_ID_JST);
        final var utcZonedDateTime = jstZonedDateTime.withZoneSameInstant(ZONE_ID_UTC);

        return utcZonedDateTime.toOffsetDateTime();
    }

    /**
     * Convert from LocalDateTime to OffsetDateTime(Offset:UTC).
     *
     * @param localDateTime value of local date time
     * @return offsetDateTime the result of converted
     */
    public static OffsetDateTime toOffsetDateTime(final LocalDateTime localDateTime) {
        return toOffsetDateTime(localDateTime, ZoneOffset.UTC);
    }

    /**
     * Convert from LocalDateTime to OffsetDateTime(Specified Zone).
     *
     * @param localDateTime localDateTime
     * @param zoneOffset zoneOffset
     * @return offsetDateTime the result of converted
     */
    public static OffsetDateTime toOffsetDateTime(final LocalDateTime localDateTime,
                                                  final ZoneOffset zoneOffset) {
        if (Objects.isNull(localDateTime)) {
            throw new IllegalArgumentException("specified localDateTime cannot be null.");
        }

        return OffsetDateTime.of(localDateTime, zoneOffset);
    }

    /**
     * Convert from LocalDateTime to OffsetDateTime(Offset:JST).
     *
     * @param jstLocalDateTime value of local date time
     * @return offsetDateTime the result of converted
     */
    public static OffsetDateTime toJSTOffsetDateTime(final LocalDateTime jstLocalDateTime) {
        final var zonedDateTime = jstLocalDateTime.atZone(ZONE_ID_JST);

        return OffsetDateTime.ofInstant(zonedDateTime.toInstant(), ZONE_ID_JST);
    }

    /**
     * Convert UTC offset date time to JST offset date time.
     *
     * @param utcOffsetDateTime UTC offset date time
     * @return JST offset date time
     */
    public static OffsetDateTime toJSTOffsetDateTime(final OffsetDateTime utcOffsetDateTime) {
        final var zonedDateTime = utcOffsetDateTime.toZonedDateTime();
        final var jstZonedDateTime = zonedDateTime.withZoneSameInstant(ZONE_ID_JST);

        return OffsetDateTime.ofInstant(jstZonedDateTime.toInstant(), ZONE_ID_JST);
    }

    /**
     * Convert UTC Local date time to JST offset date time.
     *
     * @param utcLocalDateTime UTC Local date time
     * @return JST offset date time
     */
    public static OffsetDateTime toJSTOffsetDateTimeWithUtc(final LocalDateTime utcLocalDateTime) {
        final var utcZonedDateTime = utcLocalDateTime.atZone(ZONE_ID_UTC);
        final var jstZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZONE_ID_JST);

        return jstZonedDateTime.toOffsetDateTime();
    }

    /**
     * Convert JST Local date time to UTC Local date time.
     *
     * @param jstLocalDateTime JST Local date time
     * @return UTC Local date time
     */
    public static LocalDateTime toUtcLocalDateTime(final LocalDateTime jstLocalDateTime) {
        final var jstZonedDateTime = jstLocalDateTime.atZone(ZONE_ID_JST);
        final var utcZonedDateTime = jstZonedDateTime.withZoneSameInstant(ZONE_ID_UTC);

        return utcZonedDateTime.toLocalDateTime();
    }

    /**
     * Get date by specified arguments on system default zone.
     *
     * @param year year
     * @param month month
     * @param dayOfMonth dayOfMonth
     * @param hour hour
     * @param minute minute
     * @param second second
     * @return {@code Date}
     */
    public static Date getDateSystemDefaultZone(final int year,
                                                final int month,
                                                final int dayOfMonth,
                                                final int hour,
                                                final int minute,
                                                final int second) {
        return Date.from(
            ZonedDateTime.of(
                year, month, dayOfMonth, hour, minute, second, 0, ZoneId.systemDefault()
            ).toInstant()
        );
    }

    /**
     * Convert date to offset date time on utc zone.
     *
     * @param date date
     * @return offset date time on utc zone
     */
    public static OffsetDateTime toOffsetDateTimeUtc(final Date date) {
        return date.toInstant().atOffset(ZoneOffset.UTC);
    }

    /**
     * Convert Local date time to Date.
     * @param localDateTime local date time
     * @return Date
     */
    public static Date toDate(final LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            throw new IllegalArgumentException("specified localDateTime cannot be null.");
        }

        final var zonedDateTime = ZonedDateTime.of(localDateTime, ZoneOffset.UTC);

        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * Convert Local date to Date.
     *
     * @param localDate local date
     * @return Date
     */
    public static Date toDate(final LocalDate localDate) {
        if (Objects.isNull(localDate)) {
            throw new IllegalArgumentException("specified localDate cannot be null.");
        }

        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Convert offset date time to date.
     *
     * @param offsetDateTime offset date time
     * @return date
     */
    public static Date toDate(final OffsetDateTime offsetDateTime) {
        if (Objects.isNull(offsetDateTime)) {
            throw new IllegalArgumentException("specified offsetDateTime cannot be null.");
        }
        return Date.from(offsetDateTime.toInstant());
    }

    /**
     * String (format: yyyy-MM-dd hh:mm:ss) to local date time
     *
     * @param dateTimeString string
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(final String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Convert from JST OffsetDateTime to UTC LocalDateTime.
     *
     * @param source OffsetDateTime
     * @return LocalDateTime
     */
    public static LocalDateTime toUtcLocalDateTime(final String source) {
        final var jstOffsetDateTime = OffsetDateTime.parse(source);

        return toUtcLocalDateTime(jstOffsetDateTime);
    }

    /**
     * Convert JST offset date time to UTC Local date time.
     *
     * @param jstOffsetDateTime JST offset date time
     * @return UTC Local date time
     */
    public static LocalDateTime toUtcLocalDateTime(final OffsetDateTime jstOffsetDateTime) {
        return ZonedDateTime.ofInstant(jstOffsetDateTime.toInstant(), ZONE_ID_UTC).toLocalDateTime();
    }

    /**
     * Convert offset date time on system default zone to Local date time.
     *
     * @param offsetDateTime offset date time on system default zone
     * @return Local date time
     */
    public static LocalDateTime toSystemZoneLocalDateTime(final OffsetDateTime offsetDateTime) {
        if (Objects.isNull(offsetDateTime)) {
            throw new IllegalArgumentException("OffsetDateTime cannot be null");
        }

        return offsetDateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Determine specified time1 (type: Date) isBefore than
     * specified time2 (type: LocalDateTime)
     * @param time1 Date
     * @param time2 LocalDateTime
     * @return Determine result
     */
    public static boolean isBefore(final Date time1, final LocalDateTime time2) {
        return time1.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isBefore(time2);
    }

    /**
     * Determine specified arguments is valid date.
     *
     * @param year year
     * @param month month
     * @param dayOfMonth dayOfMonth
     * @return Determine result
     */
    @SuppressWarnings("PMD.OnlyOneReturn")
    public static boolean isValidDate(final int year, final int month, final int dayOfMonth) {
        try {
            final var date = LocalDate.of(year, month, dayOfMonth);

            return year == date.getYear() && month == date.getMonthValue() && dayOfMonth == date.getDayOfMonth();
        } catch (DateTimeException e) {
            return false;
        }
    }

}
