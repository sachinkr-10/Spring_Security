package rc.bootsecurity.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import rc.bootsecurity.model.User;

public class UserPrincipal implements UserDetails {
    private static final long serialVersionUID = -4489699390942794834L;
    @Autowired
     private User user;
    public UserPrincipal(User user){
        this.user=user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities=new ArrayList<>();
        //Extract List of Authorities/permissions(p)
        this.user.getPermissionList().forEach(p->{
            GrantedAuthority authority=new SimpleGrantedAuthority(p);
            authorities.add(authority);
        });
        this.user.getRoleList().forEach(p->{
            GrantedAuthority authority=new SimpleGrantedAuthority("ROLE_"+p);
            authorities.add(authority);
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        
       
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
   
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.getActive()==1;
    }
    
}