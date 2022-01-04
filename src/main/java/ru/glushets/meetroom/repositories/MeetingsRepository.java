package ru.glushets.meetroom.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.glushets.meetroom.models.Meeting;
import ru.glushets.meetroom.models.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MeetingsRepository extends CrudRepository<Meeting, Long> {

    List<Meeting> findAllByDate(LocalDate date);

    List<Meeting> findAllByUser(User user);
}
