package com.hendisantika.adminlte.service;

import com.hendisantika.adminlte.dto.Dto;
import com.hendisantika.adminlte.model.UserProfiles;
import com.hendisantika.adminlte.model.UserRole;
import com.hendisantika.adminlte.model.Users;
import com.hendisantika.adminlte.repository.UserProfileRepository;
import com.hendisantika.adminlte.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;

    private final UserProfileRepository userProfileRepository;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user =  usersRepository.findByEmail(email);
        System.out.println(user);

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (UserRole.ADMIN.getValue().equals(user.getRoles())) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.MEMBER.getValue()));
        }

        return new User(user.getEmail(), user.getPassword(), authorities);
    }

    @Transactional
    public Long save(Dto.RequestUser payload) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Users user = new Users();
        UserProfiles userProfile = new UserProfiles();

        userProfileRepository.save(userProfile);

        user.setEmail(payload.getEmail());
        user.setPassword(encoder.encode(payload.getPassword()));
        user.setRoles(payload.getRoles());
        user.setUserProfile(userProfile);
        usersRepository.save(user);

        return user.getId();

    }

    public List<Users> findAll(){
        return usersRepository.findAll();
    }

    public Users findById(Long id){
        return usersRepository.findById(id).get();
//        Users user =  usersRepository.findById(id).get();
//        return Users.builder().email(user.getEmail())
//                                        .auth(user.getAuth())
//                                        .userProfile(user.getUserProfile())
//                                        .createdAt(user.getCreatedAt());
    }

    public Users update(Long id, Dto.RequestUpdateUser payload){
        Users user = usersRepository.findById(id).get();
        user.setName(payload.getName());
        user.setUpdatedAt(LocalDateTime.now());
        usersRepository.save(user);

        return user;
    }

    public void delete(Long id){
        Users user = usersRepository.findById(id).get();
        usersRepository.delete(user);
    }
}
