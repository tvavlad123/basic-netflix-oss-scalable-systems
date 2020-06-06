package com.ubb.cloud.security.service;

import com.ubb.cloud.security.model.Role;
import com.ubb.cloud.security.model.User;
import com.ubb.cloud.security.model.UserDetails;
import com.ubb.cloud.security.payload.SignUpRequest;
import com.ubb.cloud.security.repository.RoleRepository;
import com.ubb.cloud.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUser(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseGet(User::new);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseGet(User::new);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User saveUserSignUp(SignUpRequest signUpRequest) {
        User user = modelMapper.map(signUpRequest, User.class);
        log.info("Saving user with email {} to database.", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> roles = signUpRequest.getRoles().stream()
                .map(r -> roleRepository.findByRoleName("ROLE_" + r.getRoleName())).collect(Collectors.toList());
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public UserDetails toUserDetails(User user) {
        UserDetails userDetails = new UserDetails();
        userDetails.setId(user.getId());
        userDetails.setFirstName(user.getFirstName());
        userDetails.setLastName(user.getLastName());
        userDetails.setAffiliation(user.getAffiliation());
        return userDetails;
    }
}