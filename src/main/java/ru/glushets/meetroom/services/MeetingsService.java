package ru.glushets.meetroom.services;

import org.springframework.stereotype.Service;
import ru.glushets.meetroom.calendar.WeekMeeting;
import ru.glushets.meetroom.forms.MeetingForm;
import ru.glushets.meetroom.models.Meeting;
import ru.glushets.meetroom.models.User;

import java.time.LocalDate;
import java.util.List;

@Service
public interface MeetingsService {

    void deleteMeeting(Long meetingId);

    Meeting getMeetingById(Long meetingId);

    void updateMeeting(Long meetingId, MeetingForm form, WeekMeeting week);

    boolean checkPermissionOnEditAndDelete(String id);

    void addMeeting(MeetingForm form, WeekMeeting week);

    List<Meeting> getAllMeetingUser(User userById);

    List<Meeting> findAllMeetingsByDate(LocalDate date);

    String getError();
}
