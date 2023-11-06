package com.vvvital.psychologistsmp.repository;

import com.vvvital.psychologistsmp.model.PsychologistCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<PsychologistCard,Long> {

    //PsychologistCard save(PsychologistCard card,int user_id);
}
