package ru.glushets.meetroom.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.glushets.meetroom.models.User;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

    User findByUsername(String userName);

}

