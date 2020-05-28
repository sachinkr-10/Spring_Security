package rc.bootsecurity.db;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import rc.bootsecurity.model.User;

//commandLineRunner is used so that data is get inserted into db right after our app starts

@Service
public class DbInit implements CommandLineRunner{

    @Autowired
    private UserRepository userRepository;
   
    private PasswordEncoder passwordEncoder;
    public DbInit(PasswordEncoder passwordEncoder){
            this.passwordEncoder=passwordEncoder;
    }
    @Override
    public void run(String... args) throws Exception {
        this.userRepository.deleteAll();
        // Create Users
        User shivam=new User("shivam",passwordEncoder.encode("shivam123"),"USER","");
        User sachin=new User("sachin",passwordEncoder.encode("sachin123"),"ADMIN","ACCESS_TEST1,ACCESS_TEST2");
        User manager=new User("manager",passwordEncoder.encode("manager123"),"MANAGER","ACCESS_TEST1");
        List<User> users=Arrays.asList(shivam,sachin,manager);
        // This is used to save in Db
        this.userRepository.saveAll(users);
    }
    
}