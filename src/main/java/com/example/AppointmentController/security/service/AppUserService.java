package com.example.AppointmentController.security.service;


import com.elijah.doctorsappointmentbookingsystem.exception.DataAlreadyExistException;
import com.elijah.doctorsappointmentbookingsystem.exception.DataNotFoundException;
import com.elijah.doctorsappointmentbookingsystem.security.model.AppUser;
import com.elijah.doctorsappointmentbookingsystem.security.model.Role;
import com.elijah.doctorsappointmentbookingsystem.security.repo.AppUserRepository;
import com.elijah.doctorsappointmentbookingsystem.security.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class AppUserService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username);
        if (Objects.isNull(user)){
            throw new UsernameNotFoundException("Username Not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(user.getUsername(),new BCryptPasswordEncoder().encode(user.getPassword()),authorities);
    }

    public AppUser saveUser(AppUser user) throws DataAlreadyExistException {
        AppUser userFromDb = appUserRepository.findByUsername(user.getUsername());
        if (Objects.nonNull(userFromDb)){
            throw new DataAlreadyExistException("There is a User with this User name Already");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return appUserRepository.save(user);
    }
    public Role saveRole(Role role) throws DataAlreadyExistException {

            if (Objects.nonNull(roleRepository.findByName(role.getName()))){
                throw new DataAlreadyExistException("You have already added this role");
            }

        return roleRepository.save(role);
    }
    public void addRoleToUser(String userName, String roleName) throws DataAlreadyExistException, DataNotFoundException {
        AppUser user = appUserRepository.findByUsername(userName);
        if (Objects.isNull(user)){
            throw new DataNotFoundException("Incorrect Username");
        }
        Role role = roleRepository.findByName(roleName);
        if (Objects.isNull(role)){
            throw new DataNotFoundException("There is no role with Such Name");
        }
        for (Role roles: user.getRoles()){
            if (roles.getName().equalsIgnoreCase(role.getName())){
                throw new DataAlreadyExistException("You have already added this role to this user");
            }
        }
        user.getRoles().add(role);
    }
    public AppUser getUser(String username) throws DataNotFoundException {
        AppUser user = appUserRepository.findByUsername(username);
        if (Objects.isNull(user)){
            throw new DataNotFoundException("There is no user with this Username");
        }
        return appUserRepository.findByUsername(username);
    }
    public List<AppUser> getAllUsers(){
        return appUserRepository.findAll();
    }

}
