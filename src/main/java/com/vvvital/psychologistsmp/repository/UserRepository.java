package com.vvvital.psychologistsmp.repository;


import com.vvvital.psychologistsmp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "select * from users u where u.id in (select user_id from user_roles where roles = 'PSYCHOLOGIST')", nativeQuery = true)
    List<User> findAllPsych();

}
