package ru.glushets.meetroom.calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.glushets.meetroom.models.Meeting;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Getter
@AllArgsConstructor
public class DayMeetings {

    private List<Meeting> listMeeting;

    private LocalDate localDate;

    public String getWeekDayStringForMeetingCalendar(String lang) {
        DayOfWeek dayOfWeek = this.localDate.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.FULL, new Locale(lang));
    }

    public String getDateStringForMeetingCalendar(String lang) {
        return getDateToString(lang, this.localDate);
    }

    private String getDateToString(String lang, LocalDate date) {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(new Locale(lang))
                .format(date);
    }
}
