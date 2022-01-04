package ru.glushets.meetroom.calendar;

import org.springframework.stereotype.Component;
import ru.glushets.meetroom.models.Meeting;
import ru.glushets.meetroom.services.MeetingsService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class WeekMeeting {

    private static final int WORK_DAYS_IN_WEEk = 5;

    private static final int DAYS_IN_WEEK = 7;

    private final MeetingsService meetingsServiceImpl;

    private LocalDate firstDayOfWeek;

    private List<DayMeetings> arrayListDays;

    public WeekMeeting(MeetingsService meetingsService) {
        this.meetingsServiceImpl = meetingsService;
    }

    public void initializationWeeks(LocalDate day) {
        firstDayOfWeek = day;
        while (firstDayOfWeek.getDayOfWeek() != DayOfWeek.MONDAY) {
            firstDayOfWeek = firstDayOfWeek.minusDays(1);
        }

        arrayListDays = new ArrayList<>();
        LocalDate dayWeek = firstDayOfWeek;
        for (int i = 0; i < WORK_DAYS_IN_WEEk; i++) {
            arrayListDays.add(new DayMeetings(getListMeetingAtDate(dayWeek), dayWeek));
            dayWeek = dayWeek.plusDays(1);
        }
    }

    public DayMeetings getDayMeetingByDate(LocalDate day) {
        return arrayListDays.stream().filter(d -> d.getLocalDate().equals(day)).findFirst().orElse(null);
    }

    List<Meeting> getListMeetingAtDate(LocalDate date) {
        return meetingsServiceImpl.findAllMeetingsByDate(date);
    }

    public List<DayMeetings> getArrayListDays() {
        return arrayListDays;
    }

    public LocalDate getDateNextWeek() {
        return firstDayOfWeek.plusDays(DAYS_IN_WEEK);
    }

    public LocalDate getDatePrevWeek() {
        return firstDayOfWeek.minusDays(DAYS_IN_WEEK);
    }
}
