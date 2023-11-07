package com.vvvital.psychologistsmp.repository;

import com.vvvital.psychologistsmp.model.PsychologistCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<PsychologistCard,Long> {

    @Override
    List<PsychologistCard> findAll();
}
