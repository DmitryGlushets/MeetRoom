package ru.glushets.meetroom.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.glushets.meetroom.calendar.DayMeetings;
import ru.glushets.meetroom.calendar.WeekMeeting;
import ru.glushets.meetroom.forms.MeetingForm;
import ru.glushets.meetroom.models.Meeting;
import ru.glushets.meetroom.models.User;
import ru.glushets.meetroom.repositories.MeetingsRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingsServiceImpl implements MeetingsService {

    private static final int MINUTE_IN_HOUR = 60;

    private static final int HOUR_IN_DAY = 24;

    private static final int MIN_LENGTH_MINUTES = 30;

    private final MeetingsRepository meetingsRepository;

    private final UsersServiceImpl usersService;

    private String error;

    public String getError(){
        return error;
    }

    public void addMeeting(MeetingForm form, WeekMeeting weekMeeting) {
        LocalDate date;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = (LocalDate.parse(form.getDate(), dtf));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            error = "Select data meeting";
            return;
        }
        weekMeeting.initializationWeeks(date);

        int length = Integer.parseInt(form.getHoursLength()) * MINUTE_IN_HOUR + Integer.parseInt(form.getMinutesLength());
        int start = Integer.parseInt(form.getHoursStart()) * MINUTE_IN_HOUR + Integer.parseInt(form.getMinutesStart());
        error = checkMeeting(start, length, date, weekMeeting, -1L);

        if (error.equals("")) {
            Meeting meeting = new Meeting(date, start, length, form.getDetails());
            meeting.setUser(usersService.getUserById(usersService.getUserId()));
            meetingsRepository.save(meeting);
        }
    }

    public void deleteMeeting(Long id) {
        meetingsRepository.deleteById(id);
    }

    public List<Meeting> getAllMeeting() {
        List<Meeting> meetingsList = new ArrayList<>();
        meetingsRepository.findAll().forEach(meetingsList::add);
        meetingsList.sort(Comparator.comparing(Meeting::getId));
        return meetingsList;
    }

    public List<Meeting> getAllMeetingUser(User user) {
        return meetingsRepository.findAllByUser(user);
    }

    public Meeting getMeetingById(Long id) {
        return meetingsRepository.findById(id).orElse(null);
    }

    public void updateMeeting(Long id, MeetingForm form, WeekMeeting weekMeeting) {
        LocalDate date;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = (LocalDate.parse(form.getDate(), dtf));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            error = "No correct date";
            return;
        }
        weekMeeting.initializationWeeks(date);

        int length = Integer.parseInt(form.getHoursLength()) * MINUTE_IN_HOUR + Integer.parseInt(form.getMinutesLength());
        int start = Integer.parseInt(form.getHoursStart()) * MINUTE_IN_HOUR + Integer.parseInt(form.getMinutesStart());
        Meeting meeting = getMeetingById(id);
        error = checkMeeting(start, length, date, weekMeeting, id);

        if (error.equals("")) {
            meeting.setDate(date);
            meeting.setStart(start);
            meeting.setLength(length);
            meeting.setDetails(form.getDetails());
            meetingsRepository.save(meeting);
        }
    }

    public List<Meeting> findAllMeetingsByDate(LocalDate date) {
        return meetingsRepository.findAllByDate(date);
    }

    public String checkMeeting(int start, int length, LocalDate date, WeekMeeting weekMeeting, Long id) {
        String error = "";

        if (length > (MINUTE_IN_HOUR * HOUR_IN_DAY)) {
            error = error + "The meeting can't last more than 24 hours! ";
        }

        if (length < MIN_LENGTH_MINUTES) {
            error = error + "The meeting can't last less " + MIN_LENGTH_MINUTES + " minutes! ";
        }

        if ((start + length) > (MINUTE_IN_HOUR * HOUR_IN_DAY)) {
            error = error + "The meeting cannot end after midnight! ";
        }

        DayMeetings dm = weekMeeting.getDayMeetingByDate(date);
        if (null != dm && !isNotCollisions(dm, start, length, id)) {
            error = error + "There was a collision! ";
        }
        return error;
    }

    public boolean isNotCollisions(DayMeetings dayMeetings, int start, int length, Long id) {
        return dayMeetings.getListMeeting().stream().filter(meeting -> !meeting.getId().equals(id)).noneMatch(a ->
                isIntersect(a.getStart(), a.getStart() + a.getLength(), start, start + length));
    }

    public boolean isIntersect(int startOneMeeting, int endOneMeting, int startTwoMeeting, int endTwoMeeting) {
        if (startTwoMeeting <= startOneMeeting && startOneMeeting < endTwoMeeting) {
            return true;
        }
        return startOneMeeting <= startTwoMeeting && startTwoMeeting < endOneMeting;
    }

    public boolean checkPermissionOnEditAndDelete(String id) {
        Meeting meeting = getMeetingById(Long.parseLong(id));
        String nameAuthor = meeting.getUser().getUsername();

        if (nameAuthor.equals(usersService.getUserName())) {
            return true;
        }

        User user = (User) usersService.loadUserByUsername(usersService.getUserName());
        return user.getNameRole().equals("ADMIN");
    }
}
