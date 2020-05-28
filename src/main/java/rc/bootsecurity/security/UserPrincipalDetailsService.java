package rc.bootsecurity.security;

// import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import rc.bootsecurity.db.UserRepository;
import rc.bootsecurity.model.User;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

     @Autowired
     UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=this.userRepository.findByUsername(username); 
        // user.orElseThrow(()->new UsernameNotFoundException("Not Found: "+username));
        UserPrincipal userPrincipal=new UserPrincipal(user);
        return userPrincipal;
    }
    
}