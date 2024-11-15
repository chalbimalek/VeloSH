package com.shirazmsalmi.runbackend.ServiceImp;


import com.shirazmsalmi.runbackend.Entity.User;
import com.shirazmsalmi.runbackend.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl {


    private final UserRepo userRepository;


    public long getUserIdFromUsername(String username) {
        User user = userRepository.findIdByUsername(username);
        if (user != null) {
            return user.getId();
        } else {
            // Handle case when user is not found
            return -1; // Or throw an exception, depending on your requirements
        }
    }


}

