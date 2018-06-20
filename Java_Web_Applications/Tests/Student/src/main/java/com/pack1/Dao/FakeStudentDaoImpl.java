package com.pack1.Dao;

import com.pack1.Entity.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
@Qualifier("FakeStudentDaoImpl")
public class FakeStudentDaoImpl {

    private static Map<Integer,Student> students;

    static{
        students = new HashMap<Integer,Student>(){
            {
                put(1,new Student(1,"Said","Computer Science"));
                put(2,new Student(2,"Abdul","Finance"));
                put(3,new Student(3,"Saif","Math"));
            }
        };
    }

    public Collection<Student> getAllStudents(){
        return this.students.values();
    }

    public Student getStudentsById(int id){
        return this.students.get(id);
    }

    public void removeStudentById(int id){
        this.students.remove(id);
    }

    public void updateStudent(Student student){
        Student s = students.get(student.getId());
        s.setCourse(student.getCourse());
        s.setName(student.getName());
        students.put(student.getId(),s);
    }

    public void insertStudent(Student student){
        students.put(student.getId(),student);
    }

}
