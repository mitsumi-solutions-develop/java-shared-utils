package io.github.mitsumi.solutions.shared.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtils {

    public static final ZoneId ZONE_ID_UTC = ZoneId.of("UTC");
    public static final ZoneId ZONE_ID_JST = ZoneId.of("Asia/Tokyo");

    /**
     * UTC時間を取得.
     *
     * @return UTC時間
     */
    public static LocalDateTime getUtcTime() {
        return LocalDateTime.now(ZONE_ID_UTC);
    }

    public static LocalDateTime getJstTime() {
        return LocalDateTime.now(ZONE_ID_JST);
    }

    /**
     * UTCのZonedDateTimeを返却.
     *
     * @return UTCのZonedDateTime
     */
    public static ZonedDateTime getUtcZonedDateTime() {
        return getUtcTime().atZone(ZONE_ID_UTC);
    }

    /**
     * システムデフォルトのOffsetDateTimeを返却.
     *
     * @return システムデフォルトのOffsetDateTime
     */
    public static OffsetDateTime getOffsetDateTime() {
        return OffsetDateTime.now();
    }

    /**
     * UTCのEpochSecondを返却.
     *
     * @return UTCのEpochSecond
     */
    public static long getUtcEpochSecond() {
        return getUtcZonedDateTime().toEpochSecond();
    }

    /**
     * 時間を比較.
     *
     * @param t1 比較対象1
     * @param t2 比較対象2
     * @return 時間を比較判定値
     */
    public static boolean isSame(LocalDateTime t1, LocalDateTime t2) {
        return t1.equals(t2);
    }

    /**
     * 時間を比較.
     *
     * @param t1 比較対象1
     * @param t2 比較対象2
     * @return 時間を比較判定値
     */
    public static boolean isSame(OffsetDateTime t1, LocalDateTime t2) {
        return isSame(t1.toLocalDateTime(), t2);
    }

    /**
     * 時間を比較(ms単位まで).
     *
     * @param t1 比較対象1
     * @param t2 比較対象2
     * @return 時間を比較判定値
     */
    public static boolean isSameMs(LocalDateTime t1, LocalDateTime t2) {
        TemporalUnit compareUnit = ChronoUnit.MILLIS;
        return t1.truncatedTo(compareUnit).equals(t2.truncatedTo(compareUnit));
    }


    /**
     * チェック対象が開始時間～終了時間の間かチェック.
     * @param target チェック対象
     * @param from 開始時間
     * @param to 終了時間
     * @return 判定値
     */
    public static boolean isBetween(LocalDateTime target, LocalDateTime from, LocalDateTime to) {
        return !target.isAfter(to) && !target.isBefore(from);
    }

    /**
     * チェック対象が開始時間～終了時間の間かチェック.
     * @param target チェック対象
     * @param from 開始時間
     * @param to 終了時間
     * @return 判定値
     */
    public static boolean isBetween(OffsetDateTime target, LocalDateTime from, LocalDateTime to) {
        return isBetween(target.toLocalDateTime(), from, to);
    }

    public static OffsetDateTime getOffsetDateTimeUtc(int year, int month, int dayOfMonth,
                                                      int hour, int minute, int second) {
        return OffsetDateTime.of(year, month, dayOfMonth, hour, minute, second, 0,
                ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now()))
            .withOffsetSameInstant(ZoneOffset.UTC);
    }

    public static OffsetDateTime getUtcOffsetDateTimeFromJst(int year, int month, int dayOfMonth,
                                                             int hour, int minute, int second) {
        var jstLocalDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);
        var jstZonedDateTime = jstLocalDateTime.atZone(ZONE_ID_JST);
        var utcZonedDateTime = jstZonedDateTime.withZoneSameInstant(ZONE_ID_UTC);

        return utcZonedDateTime.toOffsetDateTime();
    }

    /**
     * Convert from LocalDateTime to OffsetDateTime(Offset:UTC).
     * @param localDateTime value of local date time
     * @return offsetDateTime the result of converted
     */
    public static OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        return toOffsetDateTime(localDateTime, ZoneOffset.UTC);
    }

    public static OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        if (localDateTime == null) {
            return null;
        }
        return OffsetDateTime.of(localDateTime, zoneOffset);
    }

    public static OffsetDateTime toJSTOffsetDateTime(LocalDateTime jstLocalDateTime) {
        var localZonedDateTime = jstLocalDateTime.atZone(ZONE_ID_JST);
        return OffsetDateTime.ofInstant(localZonedDateTime.toInstant(), ZONE_ID_JST);
    }

    public static OffsetDateTime toJSTOffsetDateTime(OffsetDateTime utcOffsetDateTime) {
        var zonedDateTime = utcOffsetDateTime.toZonedDateTime();
        var jstZonedDateTime = zonedDateTime.withZoneSameInstant(ZONE_ID_JST);

        return OffsetDateTime.ofInstant(jstZonedDateTime.toInstant(), ZONE_ID_JST);
    }

    public static OffsetDateTime toJSTOffsetDateTimeWithUtc(LocalDateTime utcLocalDateTime) {
        var utcZonedDateTime = utcLocalDateTime.atZone(ZONE_ID_UTC);
        var jstZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZONE_ID_JST);
        return jstZonedDateTime.toOffsetDateTime();
    }

    public static LocalDateTime toUtcLocalDateTime(LocalDateTime jstLocalDateTime) {
        var jstZonedDateTime = jstLocalDateTime.atZone(ZONE_ID_JST);
        var utcZonedDateTime = jstZonedDateTime.withZoneSameInstant(ZONE_ID_UTC);
        return utcZonedDateTime.toLocalDateTime();
    }

    public static Date getDateSystemDefaultZone(int year, int month, int dayOfMonth, int hour, int minute, int second) {
        return Date.from(
            ZonedDateTime.of(
                year, month, dayOfMonth, hour, minute, second, 0, ZoneId.systemDefault()
            ).toInstant()
        );
    }

    public static OffsetDateTime toOffsetDateTimeUtc(Date date) {
        return date.toInstant().atOffset(ZoneOffset.UTC);
    }

    public static Date toDate(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return null;
        }

        var zonedDateTime = ZonedDateTime.of(localDateTime, ZoneOffset.UTC);
        return Date.from(zonedDateTime.toInstant());
    }

    public static Date toDate(LocalDate localDate) {
        if (Objects.isNull(localDate)) {
            return null;
        }

        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(OffsetDateTime offsetDateTime) {
        if (Objects.isNull(offsetDateTime)) {
            return null;
        }
        return Date.from(offsetDateTime.toInstant());
    }

    /**
     * 文字列データをLocalDateTimeにする.
     * yyyy-MM-dd hh:mm:ss
     *
     * @param dateTimeString 文字列DateTime
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Convert from JST OffsetDateTime to UTC LocalDateTime.
     *
     * @param jstOffsetDateTimeString OffsetDateTime
     * @return LocalDateTime
     */
    public static LocalDateTime toUtcLocalDateTime(String jstOffsetDateTimeString) {
        var jstOffsetDateTime = OffsetDateTime.parse(jstOffsetDateTimeString);
        return toUtcLocalDateTime(jstOffsetDateTime);
    }

    public static LocalDateTime toUtcLocalDateTime(OffsetDateTime jstOffsetDateTime) {
        return ZonedDateTime.ofInstant(jstOffsetDateTime.toInstant(), ZONE_ID_UTC).toLocalDateTime();
    }

    public static LocalDateTime toSystemZoneLocalDateTime(OffsetDateTime offsetDateTime) {
        if (Objects.isNull(offsetDateTime)) {
            throw new IllegalArgumentException("OffsetDateTime cannot be null");
        }

        return offsetDateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }


    public static boolean isBefore(Date time1, LocalDateTime time2) {
        return time1.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isBefore(time2);
    }

    public static boolean isValidDate(int year, int month, int dayOfMonth) {
        try {
            var date = LocalDate.of(year, month, dayOfMonth);

            return year == date.getYear() && month == date.getMonthValue() && dayOfMonth == date.getDayOfMonth();
        } catch (DateTimeException e) {
            return false;
        }
    }

    public static OffsetDateTime dateStringToOffsetDateTimeAtStartOfDay(
        String dateTimeString, DateTimeFormatter dateTimeFormatter, ZoneId zoneId
    ) {
        return LocalDate.parse(dateTimeString, dateTimeFormatter)
            .atStartOfDay(zoneId)
            .toOffsetDateTime();
    }

    public static OffsetDateTime dateStringToOffsetDateTimeAtEndOfDay(
        String dateTimeString, DateTimeFormatter dateTimeFormatter, ZoneId zoneId
    ) {
        return LocalDate.parse(dateTimeString, dateTimeFormatter)
            .atTime(LocalTime.MAX)
            .atZone(zoneId)
            .toOffsetDateTime();
    }
}
