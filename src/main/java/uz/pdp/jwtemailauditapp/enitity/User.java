package uz.pdp.jwtemailauditapp.enitity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id; //userning idsi

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Email
    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updateAt;

    @ManyToMany
    private Set<Role> roles;

    private String emailCode;

    private boolean accountNonExpired=true;
    private boolean accountNonLocked=true; //bu user blocklanmagan
    private boolean credentialsNonExpired=true; //bu user sroki o'tmagan
    private boolean enabled; //bu user yoniqmi



///--------------USER DETAILS METHODLARI-----------------///
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }


    //ACCOUNT  BLOCKLANGANLIGI HOLATINI QAYTARADI
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }


    //ACCOUNT  srogi o'tmaganlik HOLATINI QAYTARADI
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }


    //ACCountning o'chiqligini qaytaradi
    @Override
    public boolean isEnabled() {
        return true;
    }

}
