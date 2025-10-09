package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.UserRegistrationDTO;
//import com.internshipProject1.LearningPLatform.Entity.Course;
import com.internshipProject1.LearningPLatform.Entity.Course;
import com.internshipProject1.LearningPLatform.Entity.Login;
import com.internshipProject1.LearningPLatform.Entity.Users;
import com.internshipProject1.LearningPLatform.Repository.LoginRepository;
import com.internshipProject1.LearningPLatform.Repository.UserRepository;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users addUser(UserRegistrationDTO userRegistrationDTO) throws IllegalAccessException {

        if(loginRepository.findByUsername(userRegistrationDTO.getUsername()).isPresent()){
                throw new IllegalAccessException("Username already exists");
        }

        Login login = new Login();
        login.setUsername(userRegistrationDTO.getUsername());
        login.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        login.setAccountStatus("ACTIVE");
        login.setRole(userRegistrationDTO.getRole());
        loginRepository.save(login);

        Users users = new Users();
        users.setLogin(login);
        users.setFirstName(userRegistrationDTO.getFirstName());
        users.setMiddleName(userRegistrationDTO.getMiddleName());
        users.setLastName(userRegistrationDTO.getLastName());
        users.setUserPhone(userRegistrationDTO.getPhoneNum());
        users.setUserDOB(userRegistrationDTO.getUserDOB());
        users.setAddress(userRegistrationDTO.getAddress());
        users.setEmail(userRegistrationDTO.getEmail());
        users.setGender(userRegistrationDTO.getGender());

        return userRepository.save(users);

    }

    @Override
    public List<Users> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Users updateUser(Long userId, UserRegistrationDTO userRegistrationDTO) {
        Users users = userRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("User does not exist"));

        users.setFirstName(userRegistrationDTO.getFirstName());
        users.setMiddleName(userRegistrationDTO.getMiddleName());
        users.setLastName(userRegistrationDTO.getLastName());
        users.setUserPhone(userRegistrationDTO.getPhoneNum());
        users.setUserDOB(userRegistrationDTO.getUserDOB());
        users.setAddress(userRegistrationDTO.getAddress());
        users.setEmail(userRegistrationDTO.getEmail());
        users.setGender(userRegistrationDTO.getGender());

        return userRepository.save(users);
    }

    @Override
    public void deactivateUser(Long loginId) {
        Login login = loginRepository.findById(loginId).orElseThrow(()->new UsernameNotFoundException("User does not exist"));
        login.setAccountStatus("INACTIVE");
        loginRepository.save(login);

    }

    @Override
    public void activateUser(Long loginId) {
        Login login = loginRepository.findById(loginId).orElseThrow(()->new UsernameNotFoundException("User does not exist"));
        login.setAccountStatus("ACTIVE");
        loginRepository.save(login);
    }

    @Override
    public List<Course> viewCourses(Long userId) {
        Users user = userRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("Username not found"));
        return user.getCourses();
    }

//    @Override
//    public List<StudentEnrolled> viewEnrolledCourses(Long userId) {
//        Users users = userRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("User does not exist"));
//        return users.getCourses();
//    }
}
