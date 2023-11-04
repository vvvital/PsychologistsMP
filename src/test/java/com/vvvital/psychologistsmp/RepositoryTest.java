package com.vvvital.psychologistsmp;

import com.vvvital.psychologistsmp.model.*;
import com.vvvital.psychologistsmp.repository.UserRepository;
import com.vvvital.psychologistsmp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void create(){
        User user = new User("lesia@email.ua","password","Lesia","Ukrainka"
                , Role.USER, Location.KYIV);
        userRepository.save(user);
    }

    @Test
    public void createPsychologist(){
        Set<Categories> categories = Stream.of(Categories.PSYCHOLOGIST_SEXOLOGIST,Categories.CHILD_PSYCHOLOGIST
                ,Categories.ORGANIZATIONAL_PSYCHOLOGIST).collect(Collectors.toSet());

        PsychologistCard psychologistCard=new PsychologistCard(1000,5,6
                ,"description","photoLink",categories);
        Psychologist psychologist=new Psychologist("ivan@email.ua","password","Ivan","Franko"
                , Role.PSYCHOLOGIST, Location.LVIV, psychologistCard);
        userRepository.save(psychologist);
    }

}
