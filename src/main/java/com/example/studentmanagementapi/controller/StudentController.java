
package com.example.studentmanagementapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.studentmanagementapi.model.Student;
import com.example.studentmanagementapi.repository.StudentRepository;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    // 1. Add student
    @PostMapping
    public ResponseEntity<String> addStudent(
            @RequestBody Student student) {

        repository.addStudent(student);

        return ResponseEntity.ok("Student added successfully");
    }

    // 2. Get all students
    @GetMapping
    public List<Student> getAllStudents() {

        return repository.getAllStudents();
    }

    // 3. Search student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(
            @PathVariable int id) {

        Student student = repository.getStudentById(id);

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(student);
    }

    // 4. Update scores and skills
    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudent(
            @PathVariable int id,
            @RequestBody Student student) {

        int result = repository.updateStudent(id, student);

        if (result == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Student updated successfully");
    }

    // 5. Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable int id) {

        int result = repository.deleteStudent(id);

        if (result == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Student deleted successfully");
    }

    // 6. Merit list
    @GetMapping("/merit-list")
    public List<Student> getMeritList() {

        return repository.getMeritList();
    }
}
