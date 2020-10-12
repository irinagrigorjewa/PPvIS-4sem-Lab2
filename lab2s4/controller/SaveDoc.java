package ppvis.lab2s4.controller;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ppvis.lab2s4.model.*;
import ppvis.lab2s4.controller.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

import java.lang.reflect.Modifier;
import java.util.List;

public class SaveDoc {


    public static void saveDoc(File file, List<Student> studentList) {

        Element students,
                student,
                snp,
                group,
                pass,

                passBecauseOfDisease,
                passWithoutValidReason,
                passForOtherReasons;

        Attr surname,
                name,
                patronym,
                groupName,
                passName, passName1, passName2, passName3;

        Document doc;
        DocumentBuilderFactory docBuilderFactory;
        DocumentBuilder docBuilder;
        TransformerFactory transformerFactory;
        Transformer transformer;
        DOMSource source;
        StreamResult streamResult;

        try {
            docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBuilderFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();

            students = doc.createElement("students");
            doc.appendChild(students);

            for (Student studenti : studentList) {
                surname = doc.createAttribute("surname");
                surname.setValue(studenti.getSnp().getSurname());
                name = doc.createAttribute("name");
                name.setValue(studenti.getSnp().getName());
                patronym = doc.createAttribute("patronym");
                patronym.setValue(studenti.getSnp().getPatronym());
                snp = doc.createElement("snp");
                snp.setAttributeNode(surname);
                snp.setAttributeNode(name);
                snp.setAttributeNode(patronym);

                group = doc.createElement("group");
                groupName = doc.createAttribute("group");
                groupName.setValue(studenti.getGroup());
                group.setAttributeNode(groupName);

                passBecauseOfDisease = doc.createElement("passBecauseOfDisease");
                passName = doc.createAttribute("passBecauseOfDisease");
                passName.setValue(String.valueOf(studenti.getPassBecauseOfDisease()));
                passBecauseOfDisease.setAttributeNode(passName);

                passWithoutValidReason = doc.createElement("passWithoutValidReason");
                passName1 = doc.createAttribute("passWithoutValidReason");
                passName1.setValue(String.valueOf(studenti.getPassWithout()));
                passWithoutValidReason.setAttributeNode(passName1);

                passForOtherReasons = doc.createElement("passForOtherReasons");
                passName2 = doc.createAttribute("passForOtherReasons");
                passName2.setValue(String.valueOf(studenti.getPassForOtherReason()));
                passForOtherReasons.setAttributeNode(passName2);
//
                pass = doc.createElement("total");
                passName3 = doc.createAttribute("total");
                passName3.setValue(String.valueOf(studenti.getTotal()));
                pass.setAttributeNode(passName3);


                student = doc.createElement("student");
                student.appendChild(snp);
                student.appendChild(group);
                student.appendChild(passBecauseOfDisease);
                student.appendChild(passForOtherReasons);
                student.appendChild(passWithoutValidReason);
                student.appendChild(pass);
                students.appendChild(student);
            }

            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            source = new DOMSource(doc);
            streamResult = new StreamResult(file);
            transformer.transform(source, streamResult);
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }
}
