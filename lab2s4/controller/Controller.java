package ppvis.lab2s4.controller;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import ppvis.lab2s4.model.Model;
import ppvis.lab2s4.model.FullName;
import ppvis.lab2s4.model.Student;

public class Controller {
    final String BECAUSE_OF_DISEASE = "По болезни",
            FOR_OTHER_REASON = "По другой причине",
            WITHOUT_VALID_REASON = "Без уважительной причины";
    private Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public List<Student> getStudentList() {
        return model.getStudentList();
    }


    public void newDoc() {
        this.model = new Model();
    }

    public void addStudent(String surname, String name,
                           String patronym,
                           String group,
                           int passBecauseOfDisease,
                           int passForOtherReason,
                           int passWithout,
                           int total) {
        model.addStudent(
                new Student(new FullName(surname, name, patronym), group, passBecauseOfDisease, passForOtherReason, passWithout, total)
        );
    }

    public void openDoc(File file) {
        try {
            model.setStudentList(DocOpener.openDoc(file));
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }


    public List search(String selectedItem, List<String> criteriaList) {
        final String SURNAME = criteriaList.get(0),
                CRITERIA_1 = "Фамилия и вид пропуска",
                CRITERIA_2 = "Фамилия и номер группы",
                CRITERIA_3 = "Фамилия и кол-во пропусков по виду";
        final String GROUP = criteriaList.get(1);
        List<Student> studentList = getStudentList();
        List resultList;

        resultList = new ArrayList<Student>();

        switch (selectedItem) {
            case CRITERIA_1:

                Integer quantity = 0;


                for (Student student : studentList) {

                    if (criteriaList.get(2).equals(BECAUSE_OF_DISEASE)) {

                        quantity = student.getPassBecauseOfDisease();
                    }
                    if (criteriaList.get(2).equals(FOR_OTHER_REASON)) {

                        quantity = student.getPassForOtherReason();
                    }
                    if (criteriaList.get(2).equals(WITHOUT_VALID_REASON)) {

                        quantity = student.getPassForOtherReason();
                    }
//
                    if (student.getSurname().equals(SURNAME) && quantity > 0) {
                        resultList.add(student);
                    }
                }
                break;
            case CRITERIA_2:


                for (Student student : studentList) {
                    if (student.getSurname().equals(SURNAME) && student.getGroup().equals(GROUP)) {
                        resultList.add(student);
                    }
                }
                break;
            case CRITERIA_3:
                final String CRITERIA = criteriaList.get(2);
                final Integer MIN = Integer.valueOf(criteriaList.get(3));
                final Integer MAX = Integer.valueOf(criteriaList.get(4));


                boolean pass = false;

                for (Student student : studentList) {
                    if (CRITERIA.equals(BECAUSE_OF_DISEASE)) {
                        if (student.getPassBecauseOfDisease() >= MIN && student.getPassBecauseOfDisease() <= MAX) {
                            pass = true;

                        }
                    }
                    if (CRITERIA.equals(FOR_OTHER_REASON)) {
                        if (student.getPassForOtherReason() >= MIN && student.getPassForOtherReason() <= MAX) {
                            pass = true;

                        }
                    }
                    if (CRITERIA.equals(WITHOUT_VALID_REASON)) {
                        if (student.getPassWithout() >= MIN && student.getPassWithout() <= MAX) {
                            pass = true;

                        }
                    }
                    if (student.getSurname().equals(SURNAME) && pass) {
                        resultList.add(student);
                    }
                }
                break;
        }

        return resultList;
    }

    public void delete(List<Student> indexList) {
        for (Student student : indexList) {
            getStudentList().remove(student);
        }
    }

    @FXML
    void i() {

    }
}
