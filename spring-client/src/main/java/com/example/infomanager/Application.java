package com.example.infomanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    @Autowired
//    CommandLineRunner init(UserRepository userRepository){
//        return args -> {
//            ObjectMapper mapper = new ObjectMapper();
//            TypeReference<List<User>> typeReference = new TypeReference<List<User>>(){};
//            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/MOCK_DATA.json");
//            try{
//                List<User> users = mapper.readValue(inputStream, typeReference);
//                userRepository.saveAll(users);
//                System.out.println("Users saved.");
//            }
//            catch (IOException e){
//                System.out.println("Unable to save users: " + e.getMessage());
//            }
//        };
//    }

}