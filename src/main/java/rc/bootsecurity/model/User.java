package rc.bootsecurity.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="User")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @Column(nullable=false)
    private String username;
    @Column(nullable=false)
    private String password;
    private String roles="";
    private String permissions="";
    private int active;
   
   public User(String username,String password,String roles,String permissions){
       this.username=username;
       this.password=password;
       this.roles=roles;
       this.permissions=permissions;
       this.active=1;

   }

   //It is used to convert given string of roles to List Of Roles
   public List<String> getRoleList(){
       if(this.roles.length()>0){
           return Arrays.asList(this.roles.split(","));       
   }
   return new ArrayList<>();
}

//List of Authorities
public List<String> getPermissionList(){
    if(this.permissions.length()>0){
        return Arrays.asList(this.permissions.split(","));
}
return new ArrayList<>();

}

}