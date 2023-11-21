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

    @Query(value = "select user_id,email,first_name,last_name,location,password,roles,card_id,description,experience,photo_link,price,rating,c.id,categories from user_roles full join public.users u on u.id = user_roles.user_id join public.card c on c.id = u.card_id join public.psychologist_card_categories pcc on c.id = pcc.psychologist_card_id where roles='PSYCHOLOGIST'",nativeQuery = true)
    Set<User> findAllPsych();

}
