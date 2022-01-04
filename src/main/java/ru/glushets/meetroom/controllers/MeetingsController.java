package ru.glushets.meetroom.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.glushets.meetroom.avatar.Avatar;
import ru.glushets.meetroom.calendar.WeekMeeting;
import ru.glushets.meetroom.forms.MeetingForm;
import ru.glushets.meetroom.models.Meeting;
import ru.glushets.meetroom.services.MeetingsService;
import ru.glushets.meetroom.services.UsersService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MeetingsController {

    private final UsersService usersServiceImpl;

    private final MeetingsService meetingsServiceImpl;

    private final WeekMeeting week;

    @PostMapping("/meetingPage/{meeting-id}/delete")
    public String deleteMeetingWithAdminPage(@PathVariable("meeting-id") Long meetingId) {
        LocalDate date = meetingsServiceImpl.getMeetingById(meetingId).getDate();
        meetingsServiceImpl.deleteMeeting(meetingId);
        return "redirect:/meetingCalendar?date="+date;
    }

    @GetMapping("/meeting/{meeting-id}")
    public String getMeetingPageForUpdate(Model model, @PathVariable("meeting-id") Long meetingId) {
        Meeting meeting = meetingsServiceImpl.getMeetingById(meetingId);
        model.addAttribute("meeting", meeting);
        return "/meeting/meeting";
    }

    @PostMapping("/meeting/{meeting-id}/update")
    public String updateMeeting(@PathVariable("meeting-id") Long meetingId, MeetingForm form,
                                RedirectAttributes redirectAttributes) {
        meetingsServiceImpl.updateMeeting(meetingId, form, week);
        String error = meetingsServiceImpl.getError();
        redirectAttributes.addFlashAttribute("error", error);
        if (!error.equals("")) {
            return "redirect:/meeting/" + meetingId;
        }
        return "redirect:/meetingCalendar?date="+form.getDate();
    }

    @GetMapping(value = "/meetingCalendar")
    public String getMeetingCalendar(@RequestParam(name = "date", required = false, defaultValue = "-") String date, Model model) {
        LocalDate startWeek = LocalDate.now();

        if (!date.equals("-")) {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                startWeek = LocalDate.parse(date, dtf);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        week.initializationWeeks(startWeek);
        model.addAttribute("week", week);
        model.addAttribute("avatar", Avatar.getAvatar(usersServiceImpl.getUserName()));
        return "meeting/meetingCalendar";
    }

    @GetMapping(value = "/meetingDetails")
    public String getMeetingDetailsOnCalendar(@RequestParam(name = "id", required = false, defaultValue = "-") String id,
                                              @RequestParam(name = "user", required = false, defaultValue = "-") String username,
                                              @RequestParam(name = "date", required = false, defaultValue = "-") String date,
                                              Model model) {
        Meeting meeting = meetingsServiceImpl.getMeetingById(Long.parseLong(id));
        model.addAttribute("meeting", meeting);
        model.addAttribute("user", username);
        model.addAttribute("date", date);
        model.addAttribute("check", meetingsServiceImpl.checkPermissionOnEditAndDelete(id));
        return "/meeting/meetingDetails";
    }

    @GetMapping(value = "/newMeeting")
    public String getPageForAddNewMeeting(Model model, MeetingForm form) {
        model.addAttribute("date", form.getDate());
        return "meeting/newMeeting";
    }

    @PostMapping("/newMeeting")
    public String addMeeting(MeetingForm form, RedirectAttributes redirectAttributes) {
        meetingsServiceImpl.addMeeting(form, week);
        String error = meetingsServiceImpl.getError();
        redirectAttributes.addFlashAttribute("error", error);
        if (!error.equals("")) {
            return "redirect:/newMeeting?date=" + form.getDate();
        }
        return "redirect:/meetingCalendar";
    }
}