package com.swapnil.bookstore.app.service;

import com.swapnil.bookstore.app.entity.User;
import com.swapnil.bookstore.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUserById(long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public void deleteUserById(long id) throws Exception {
        try {
            userRepository.delete(userRepository.getReferenceById(id));
        }catch (Exception e){
           throw new Exception(e.getMessage());
        }
    }
}
