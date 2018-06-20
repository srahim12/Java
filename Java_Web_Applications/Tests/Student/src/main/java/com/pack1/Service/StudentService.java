package com.pack1.Service;

import com.pack1.Dao.MongoDBStudentDao;
import com.pack1.Dao.StudentDaoInter;
import com.pack1.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudentService {

    @Autowired
    @Qualifier("MongoDBStudentDao")
    private StudentDaoInter studentDao;

    public Collection<Student> getAllStudents(){
        return studentDao.getAllStudents();
    }

    public Student getStudentById(int id){
        return this.studentDao.getStudentsById(id);
    }

    public void removeStudentById(int id){
        this.studentDao.removeStudentById(id);
    }

    public void updateStudent(Student student){
        this.studentDao.updateStudent(student);
    }

    public void insertStudent(Student student){
        this.studentDao.insertStudent(student);
    }

}
