package com.sda.auction_site.service;


import com.sda.auction_site.model.AppUser;
import com.sda.auction_site.model.Role;
import com.sda.auction_site.repository.UserRepository;
import com.sda.auction_site.security.SecurityConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Set;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    private final PasswordEncoder encoder;

    public UserService(@Lazy PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public AppUser getUserById(Long id){
       return userRepository.findById(id).orElseThrow(()-> new RuntimeException("user with id "+ id+ " not found "));
    }

    public AppUser saveUser(AppUser appUser) {

        return userRepository.save(appUser);
    }
    public AppUser saveNewUser(AppUser appUser) {

        Role role = roleService.getByRoleName("ROLE_USER");
        appUser.setRoleSet(Set.of(role));
        appUser.setPassword(encoder.encode(appUser.getPassword()));

        return userRepository.save(appUser);
    }

    public AppUser updateUser(AppUser appUser) {


        return userRepository.save(appUser);
    }
    public AppUser getUserByEmail(String email){

        return userRepository.findUserByEmail(email);
    }
    public void deleteUserById(Long id){

        userRepository.deleteById(id);
    }



    public AppUser loginUser(String username, String password) {
            AppUser appUser = userRepository.findUserByEmail(username);
            System.out.println(appUser);
            if (appUser.getEmail().isEmpty()){
                return null;
            }
            else {
                if (appUser.getPassword().equals(password)){
                    return appUser;
                }else{
                    return new AppUser();
                }

            }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
