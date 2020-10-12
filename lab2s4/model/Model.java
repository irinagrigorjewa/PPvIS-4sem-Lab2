package ppvis.lab2s4.model;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Student> studentList;


    public Model() {

        studentList = new ArrayList<>();

    }


    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }


}
