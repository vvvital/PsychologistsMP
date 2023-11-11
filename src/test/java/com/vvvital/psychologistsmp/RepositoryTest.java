//package com.vvvital.psychologistsmp;
//
//import com.vvvital.psychologistsmp.model.*;
//import com.vvvital.psychologistsmp.repository.CardRepository;
//import com.vvvital.psychologistsmp.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@SpringBootTest
//public class RepositoryTest {

//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private CardRepository cardRepository;
//
//    @Test
//    public void deleteAll(){
//        cardRepository.deleteAll();
//        userRepository.deleteAll();
//    }
//
//    @Test
//    public void create() {
//        User user = new User("lesia@email.ua", "password", "Lesia", "Ukrainka"
//                , Role.USER, Location.KYIV);
//        userRepository.save(user);
//    }
//
//    @Test
//    public void createClient() {
//        Client client = new Client("bohdan@email.ua", "password", "Bohdan", "Hmelnytckyy"
//                , Role.CLIENT, Location.KYIV);
//        userRepository.save(client);
//    }
//
//    @Test
//    public void getClient(){
//        Client client=(Client) userRepository.findByEmail("bohdan@email.ua").orElse(null);
//        System.out.println(client);
//    }
//
//    @Test
//    public void saveCard() {
//        Set<Categories> categories = Stream.of(Categories.PSYCHOLOGIST_SEXOLOGIST, Categories.CHILD_PSYCHOLOGIST
//                , Categories.ORGANIZATIONAL_PSYCHOLOGIST).collect(Collectors.toSet());
//        PsychologistCard psychologistCard = new PsychologistCard(1000, 5, 6
//                , "description", "photoLink",categories);
//        cardRepository.save(psychologistCard);
//    }
//
//    @Test
//    public void createPsychologist() {
//        Set<Categories> categories = Stream.of(Categories.PSYCHOLOGIST_SEXOLOGIST, Categories.CHILD_PSYCHOLOGIST
//                , Categories.ORGANIZATIONAL_PSYCHOLOGIST).collect(Collectors.toSet());
//
//        PsychologistCard psychologistCard = new PsychologistCard(1000, 5, 6
//                , "description", "photoLink",categories);
//        Psychologist psychologist = new Psychologist("ivan@email.ua", "password", "Ivan", "Franko"
//                , Role.PSYCHOLOGIST, Location.LVIV, psychologistCard);
//        userRepository.save(psychologist);
//    }
//
//    @Test
//    public void getPsych() {
//        Psychologist psychologist = (Psychologist) userRepository.findByEmail("ivan@email.ua").orElse(null);
//        assert psychologist != null;
//        System.out.println();
//        System.out.println(psychologist);
//        System.out.println();
//    }
//
//    @Test
//    public void getCard(){
//        List<PsychologistCard> cards=cardRepository.findAll();
//        cards.forEach(System.out::println);
//    }
//
//    @Test
//    public void getAllUser(){
//        List<User> users=userRepository.findAll();
//        users.forEach(System.out::println);
//    }
//
//    @Test
//    public void getUserByEmail(){
//        User user=userRepository.findByEmail("lesia@email.ua").orElse(null);
//        System.out.println(user);
//    }
//
//}
