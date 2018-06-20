package com.pack1.Dao;

import com.pack1.Entity.Student;

import java.util.Collection;

public interface StudentDaoInter {
    Collection<Student> getAllStudents();

    Student getStudentsById(int id);

    void removeStudentById(int id);

    void updateStudent(Student student);

    void insertStudent(Student student);
}
