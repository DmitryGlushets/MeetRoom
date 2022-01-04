package ru.glushets.meetroom.forms;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MeetingForm {

    private String date = "-";

    private String details = "-";

    private String hoursStart = "-";

    private String minutesStart = "-";

    private String hoursLength = "-";

    private String minutesLength = "-";
}
