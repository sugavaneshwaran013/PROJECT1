package com.studentportal.studentportal.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.studentportal.studentportal.exception.UserNotFoundException;
import com.studentportal.studentportal.model.User;
import com.studentportal.studentportal.repository.UserRepo;

import java.util.List;

@RestController
// @RequestMapping("api/v1/user")
// @CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/users")
    public User newUser(@RequestBody User newUser){
        System.out.println(newUser.getName());
        User savedUser = userRepo.save(newUser);
        if(savedUser != null) {
            System.out.println("User saved successfully: " + savedUser);
        } else {
            System.err.println("Failed to save user: " + newUser);
        }
        return savedUser;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id){
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
  
    
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail());

        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok("Login successful"+"["+user.getName()+","+user.getId()+"]");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id){
        return userRepo.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    user.setPassword(newUser.getPassword());
                    return userRepo.save(user);
                }).orElseThrow(()->new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userRepo.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepo.deleteById(id); 
        return "user with id "+id+" has been deleted success.";
    }

    @PatchMapping("/user/{id}")

    public ResponseEntity<User> patchUser(@RequestBody User partialUser, @PathVariable Long id) {
        return userRepo.findById(id)
                .map(user -> {
                    if (partialUser.getName() != null) {
                        user.setName(partialUser.getName());
                    }
                    if (partialUser.getEmail() != null) {
                        user.setEmail(partialUser.getEmail());
                    }
                    if (partialUser.getPassword() != null) {
                        user.setPassword(partialUser.getPassword());
                    }
                    return ResponseEntity.ok(userRepo.save(user));
                }).orElseThrow(() -> new UserNotFoundException(id));
    }
public ResponseEntity<User> patchUser(@RequestBody User partialUser, @PathVariable Long id) {
    return userRepo.findById(id)
            .map(user -> {
                if (partialUser.getName() != null) {
                    user.setName(partialUser.getName());
                }
                if (partialUser.getEmail() != null) {
                    user.setEmail(partialUser.getEmail());
                }
                if (partialUser.getPassword() != null) {
                    user.setPassword(partialUser.getPassword());
                }
                return ResponseEntity.ok(userRepo.save(user));
            }).orElseThrow(() -> new UserNotFoundException(id));
}
}
