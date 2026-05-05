package com.microservice.userservice.controller;

import com.microservice.userservice.dto.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id) {
        return new Student(1, "John Smith", "john@student.com");
    }
}
