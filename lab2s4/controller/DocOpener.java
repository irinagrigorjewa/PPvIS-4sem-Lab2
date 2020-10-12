package ppvis.lab2s4.controller;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ppvis.lab2s4.model.FullName;
import ppvis.lab2s4.model.Student;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocOpener {
    private static FullName snp;
    private static String group;
    private static int passBecauseOfDisease;
    private static int passWithoutValidReason;
    private static int passForOtherReasons;
    private static int total;

    private static List<Student> studentList;

    public static List<Student> openDoc(File file) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory parserFactory;
        SAXParser parser;
        XMLHandler handler;

        studentList = new ArrayList<>();

        handler = new XMLHandler();
        parserFactory = SAXParserFactory.newInstance();
        parser = parserFactory.newSAXParser();
        parser.parse(file, handler);
        return studentList;
    }

    private static class XMLHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("snp")) {
                snp = new FullName(
                        attributes.getValue("surname"),
                        attributes.getValue("name"),
                        attributes.getValue("patronym")
                );
            }
            if (qName.equals("group")) {
                group = attributes.getValue("name");
            }
            if (qName.equals("passBecauseOfDisease")) {
                passBecauseOfDisease = Integer.valueOf(attributes.getValue("passBecauseOfDisease"));

            }
            if (qName.equals("passWithoutValidReason")) {

                passWithoutValidReason = Integer.valueOf(attributes.getValue("passWithoutValidReason"));
            }
            if (qName.equals("passForOtherReasons")) {
//
                passForOtherReasons = Integer.valueOf(attributes.getValue("passForOtherReasons"));
            }
            if (qName.equals("pass")) {

                total = Integer.valueOf(attributes.getValue("total"));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equals("student")) {
                studentList.add(new Student(
                        snp,
                        group,
                        passBecauseOfDisease,
                        passWithoutValidReason,
                        passForOtherReasons
                        , total


                ));
            }
        }
    }
}
