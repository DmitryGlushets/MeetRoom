package ru.glushets.meetroom.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.glushets.meetroom.MeetRoomApplicationTests;
import ru.glushets.meetroom.calendar.DayMeetings;
import ru.glushets.meetroom.calendar.WeekMeeting;
import ru.glushets.meetroom.forms.MeetingForm;
import ru.glushets.meetroom.models.Meeting;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MeetingsServiceImplTest extends MeetRoomApplicationTests {

    @Autowired
    MeetingsServiceImpl meetingsService;

    @Autowired
    WeekMeeting weekMeeting;

    @Autowired
    UsersServiceImpl usersService;

    private MeetingForm getMeetingFormTest() {
        MeetingForm meetingFormTest = new MeetingForm();
        meetingFormTest.setDate("2021-10-10");
        meetingFormTest.setDetails("test");
        meetingFormTest.setHoursStart("1");
        meetingFormTest.setMinutesStart("0");
        meetingFormTest.setHoursLength("1");
        meetingFormTest.setMinutesLength("0");
        return meetingFormTest;
    }

    @Test
    @Transactional
    void testGetAllMeeting() {
        List<Meeting> testMeetingList = meetingsService.getAllMeeting();
        assertNotNull(testMeetingList);
    }

    @Test
    @Transactional
    void testAddMeeting() {
        LocalDate date = null;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = (LocalDate.parse("2021-10-10", dtf));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        meetingsService.addMeeting(getMeetingFormTest(), weekMeeting);
        Meeting meeting = meetingsService.findAllMeetingsByDate(date).get(0);
        assertNotNull(meeting);
        assertEquals("test", meeting.getDetails());
        assertEquals(60, meeting.getStart());
        assertEquals(60, meeting.getLength());
        assertEquals(date, meeting.getDate());
    }

    @Test
    @Transactional
    void testDeleteMeeting() {
        LocalDate date = null;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = (LocalDate.parse("2021-10-10", dtf));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        meetingsService.addMeeting(getMeetingFormTest(), weekMeeting);
        Meeting meeting = meetingsService.findAllMeetingsByDate(date).get(0);
        assertNotNull(meeting);
        meetingsService.deleteMeeting(meeting.getId());
        assertEquals(0, meetingsService.findAllMeetingsByDate(date).size());
    }

    @Test
    @Transactional
    void testGetMeetingById() {
        LocalDate date = null;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = (LocalDate.parse("2021-10-10", dtf));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        meetingsService.addMeeting(getMeetingFormTest(), weekMeeting);
        Meeting meeting = meetingsService.findAllMeetingsByDate(date).get(0);
        meeting = meetingsService.getMeetingById(meeting.getId());
        assertNotNull(meeting);
    }

    @Test
    @Transactional
    void testUpdateMeeting() {
        LocalDate date = null;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = (LocalDate.parse("2021-10-10", dtf));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        meetingsService.addMeeting(getMeetingFormTest(), weekMeeting);
        Meeting meeting = meetingsService.findAllMeetingsByDate(date).get(0);
        MeetingForm meetingForm = getMeetingFormTest();
        meetingForm.setDetails("test test");
        meetingsService.updateMeeting(meeting.getId(), meetingForm, weekMeeting);
        meeting = meetingsService.findAllMeetingsByDate(date).get(0);
        assertEquals("test test", meeting.getDetails());
    }

    @Test
    void testFindAllMeetingsByDate() {
        LocalDate date = null;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = (LocalDate.parse("2021-11-11", dtf));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        List<Meeting> testMeetingList = meetingsService.findAllMeetingsByDate(date);
        assertNotNull(testMeetingList);
    }

    @Test
    void testCheckMeeting() {
        LocalDate date = null;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = (LocalDate.parse("2021-11-11", dtf));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        weekMeeting.initializationWeeks(date);
        String error = meetingsService.checkMeeting(1, 50, date, weekMeeting, 1L);
        assertEquals("", error);
        error = meetingsService.checkMeeting(1, 1, date, weekMeeting, 1L);
        assertNotEquals("", error);
        error = meetingsService.checkMeeting(1, 99999999, date, weekMeeting, 1L);
        assertNotEquals("", error);
    }

    @Test
    void testIsIntersect() {
        assertTrue(meetingsService.isIntersect(1, 2, 1, 2));
        assertTrue(meetingsService.isIntersect(1, 4, 2, 3));
        assertTrue(meetingsService.isIntersect(1, 4, 2, 3));
        assertTrue(meetingsService.isIntersect(2, 3, 1, 4));
        assertTrue(meetingsService.isIntersect(1, 3, 2, 4));
        assertTrue(meetingsService.isIntersect(2, 4, 1, 3));
        assertTrue(meetingsService.isIntersect(1, 2, 1, 3));
        assertTrue(meetingsService.isIntersect(2, 3, 1, 3));
        assertTrue(meetingsService.isIntersect(1, 3, 1, 2));
        assertTrue(meetingsService.isIntersect(1, 3, 2, 3));
        assertFalse(meetingsService.isIntersect(1, 2, 3, 4));
        assertFalse(meetingsService.isIntersect(3, 4, 1, 2));
        assertFalse(meetingsService.isIntersect(2, 3, 1, 2));
        assertFalse(meetingsService.isIntersect(1, 2, 2, 3));
    }

    @Test
    void testIsNotCollisions() {
        ArrayList<Meeting> list = new ArrayList<>();
        Meeting meetingOne = new Meeting(LocalDate.now(), 3, 1, "");
        meetingOne.setId(1L);
        Meeting meetingTwo = new Meeting(LocalDate.now(), 3, 1, "");
        meetingTwo.setId(2L);
        list.add(meetingOne);
        list.add(meetingTwo);
        DayMeetings day = new DayMeetings(list, LocalDate.now());
        assertTrue(meetingsService.isNotCollisions(day, 2, 1, -1L));
    }
}