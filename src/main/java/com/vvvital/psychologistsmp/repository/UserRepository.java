package com.vvvital.psychologistsmp.repository;

import com.vvvital.psychologistsmp.model.Psychologist;
import com.vvvital.psychologistsmp.model.User;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "select * from users where role='PSYCHOLOGIST'",nativeQuery = true)
    List<Psychologist> findAllPsych();

}
