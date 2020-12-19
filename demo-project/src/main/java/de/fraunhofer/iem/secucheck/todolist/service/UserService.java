package de.fraunhofer.iem.secucheck.todolist.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import de.fraunhofer.iem.secucheck.todolist.model.Role;
import de.fraunhofer.iem.secucheck.todolist.model.User;
import de.fraunhofer.iem.secucheck.todolist.repository.RoleRepository;
import de.fraunhofer.iem.secucheck.todolist.repository.UserRepository;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}
    
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUserDefault(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
    
    public void saveUserPlain(User user) {
        userRepository.save(user);
    }
}