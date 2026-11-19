
package com.example.studentmanagementapi.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.studentmanagementapi.model.Student;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbc;

    public StudentRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private RowMapper<Student> mapper = (rs, rowNum) -> {

        Student s = new Student();

        s.setStudentId(rs.getInt("student_id"));
        s.setStudentName(rs.getString("student_name"));
        s.setEmailAddress(rs.getString("email_address"));
        s.setCourseName(rs.getString("course_name"));
        s.setProgrammingScore(rs.getDouble("programming_score"));
        s.setMathematicsScore(rs.getDouble("mathematics_score"));

        String skills = rs.getString("technical_skills");

        if (skills == null || skills.isEmpty()) {
            s.setTechnicalSkills(Arrays.asList());
        } else {
            s.setTechnicalSkills(Arrays.asList(skills.split(", ")));
        }

        return s;
    };

    // Add student
    public int addStudent(Student s) {

        String sql = "INSERT INTO students VALUES (?, ?, ?, ?, ?, ?, ?)";

        return jdbc.update(sql,
                s.getStudentId(),
                s.getStudentName(),
                s.getEmailAddress(),
                s.getCourseName(),
                s.getProgrammingScore(),
                s.getMathematicsScore(),
                String.join(", ", s.getTechnicalSkills()));
    }

    // Get all students
    public List<Student> getAllStudents() {

        String sql = "SELECT * FROM students";

        return jdbc.query(sql, mapper);
    }

    // Search by ID
    public Student getStudentById(int id) {

        String sql = "SELECT * FROM students WHERE student_id = ?";

        List<Student> list = jdbc.query(sql, mapper, id);

        return list.isEmpty() ? null : list.get(0);
    }

    // Update scores and skills
    public int updateStudent(int id, Student s) {

        String sql = "UPDATE students SET programming_score=?, "
                + "mathematics_score=?, technical_skills=? "
                + "WHERE student_id=?";

        return jdbc.update(sql,
                s.getProgrammingScore(),
                s.getMathematicsScore(),
                String.join(", ", s.getTechnicalSkills()),
                id);
    }

    // Delete student
    public int deleteStudent(int id) {

        String sql = "DELETE FROM students WHERE student_id=?";

        return jdbc.update(sql, id);
    }

    // Merit list
    public List<Student> getMeritList() {

        String sql = "SELECT * FROM students "
                + "ORDER BY (programming_score + mathematics_score) DESC";

        return jdbc.query(sql, mapper);
    }
}
