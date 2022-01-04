package ru.glushets.meetroom.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "table_meetings")
public class Meeting {

    @Transient
    private static final int ONE_HOUR_IN_MINUTES = 60;

    @Transient
    private static final int MAX_LENGTH_DETAILS = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate date;

    @Column
    private Integer start;

    @Column
    private Integer length;

    @Column
    private String details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    public Meeting(LocalDate date, int start, int length, String details) {
        this.date = date;
        this.start = start;
        this.length = length;
        this.details = details.substring(0, Math.min(details.length(), MAX_LENGTH_DETAILS));
    }

    public String getTimeStartToString() {
        return String.format("%02d:%02d", start / ONE_HOUR_IN_MINUTES, start % ONE_HOUR_IN_MINUTES);
    }

    public String getLengthToString() {
        return String.format("%02d:%02d", length / ONE_HOUR_IN_MINUTES, length % ONE_HOUR_IN_MINUTES);
    }

    public List<Integer> getTimeForEditPage() {
        List<Integer> list = new ArrayList<>();
        list.add(start / ONE_HOUR_IN_MINUTES);
        list.add(start % ONE_HOUR_IN_MINUTES);
        list.add(length / ONE_HOUR_IN_MINUTES);
        list.add(length % ONE_HOUR_IN_MINUTES);
        return list;
    }
}
