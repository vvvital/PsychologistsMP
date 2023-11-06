package com.vvvital.psychologistsmp;

import com.vvvital.psychologistsmp.model.*;
import com.vvvital.psychologistsmp.repository.CardRepository;
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
    @Autowired
    private CardRepository cardRepository;


    @Test
    public void create(){
        User user = new User("lesia@email.ua","password","Lesia","Ukrainka"
                , Role.USER, Location.KYIV);
        userRepository.save(user);
    }

    @Test
    public void createClient(){
        Client client=new Client("bohdan@email.ua","password","Bohdan","Hmelnytckyy"
                , Role.CLIENT, Location.KYIV);
        userRepository.save(client);
    }

    @Test
    public void saveCard(){
        PsychologistCard psychologistCard=new PsychologistCard(1000,5,6
                ,"description","photoLink");
        cardRepository.save(psychologistCard);
    }

    @Test
    public void createPsychologist(){
        Set<Categories> categories = Stream.of(Categories.PSYCHOLOGIST_SEXOLOGIST,Categories.CHILD_PSYCHOLOGIST
                ,Categories.ORGANIZATIONAL_PSYCHOLOGIST).collect(Collectors.toSet());

        PsychologistCard psychologistCard=new PsychologistCard(1000,5,6
                ,"description","photoLink");
        Psychologist psychologist=new Psychologist("ivan@email.ua","password","Ivan","Franko"
                , Role.PSYCHOLOGIST, Location.LVIV,psychologistCard);
        userRepository.save(psychologist);
    }

}
