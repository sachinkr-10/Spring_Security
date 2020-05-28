package rc.bootsecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rc.bootsecurity.db.UserRepository;
import rc.bootsecurity.model.User;

@RestController
@RequestMapping("api/public")
public class PublicRestApiController {

    public PublicRestApiController(){}
      
    @Autowired
     UserRepository userRepository;

    @GetMapping("test1")
    public String test1(){
        return "API Test 1";
    }

    @GetMapping("test2")
    public String test2(){
        return "API Test 2";
    }
    @GetMapping("users")
    public List<User> getAllUser(){
        return this.userRepository.findAll();
    }

}
