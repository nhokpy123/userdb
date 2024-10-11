package com.example.userdb.controller;

import com.example.userdb.model.User;
import com.example.userdb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
class UsersController {


    @Autowired
    private UserService userService;


    // Khởi tạo danh sách User
    public UsersController() {
    }


    //1. Get All-Trả về danh sách User
    @GetMapping("/users")
    @ResponseBody
    public List<User> getUserList() {
        return userService.findAll();
    }


    //2. Get by id- Trả về một User cụ thể theo ID
    @ResponseBody
    @GetMapping("/users/{id}")  // Thêm dấu ngoặc kép đóng
    public ResponseEntity<User> getUserById(@PathVariable("id") int userId) {
        // Không cần @ResponseBody vì ResponseEntity đã bao gồm body
        for (User user : userService.findAll()) {
            if (user.getId() == userId) {
                return ResponseEntity.status(200).body(user);
            }
        }
        return ResponseEntity.status(404).body(null);  // Trả về lỗi 404 nếu không tìm thấy
    }
    @DeleteMapping("/users/{id}")
    @ResponseBody
    public List<User> removeUserById(@PathVariable("id") Long userId) {
        userService.delete(userId);
        return  userService.findAll();
    }
    @ResponseBody
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.status(201).body(user);
    }

    @ResponseBody
    @PutMapping("/users/{id}")
    public ResponseEntity<User> udpateUser(@PathVariable("id") Long userId, @RequestBody User updateUser){
        User user = userService.findById(userId);
        if (user != null) {
            user.setCity(updateUser.getName());
            user.setEmail(updateUser.getEmail());
            userService.save(user);
            return ResponseEntity.status(200).body(user);
        }
        return ResponseEntity.status(404).body(null);
    }
}