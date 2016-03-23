package com.clouway.firsttask;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class MySAXParser extends DefaultHandler {

    private Class aClass;
    private StringBuilder content;
    private List objects = new ArrayList();
    private Map<String, Object> map = new HashMap<>();

    public <T> List<T> parse(Class<T> clazz, FileInputStream inputStream) throws IOException, ParserConfigurationException, SAXException, IllegalAccessException, InstantiationException {
        aClass = clazz;
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        saxParser.parse(inputStream, this);
        return objects;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        content = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (!qName.equalsIgnoreCase(aClass.getSimpleName())) {
            map.put(qName, content.toString());
        } else {
            objects.add(createObject(aClass));
        }
    }

    private Object createObject(Class aClass) {
        Object aClassInstance = null;

        try {
            aClassInstance = aClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Field[] fields = aClass.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);

                if (map.containsKey(field.getName())) {

                    if (field.getType() == String.class) {
                        field.set(aClassInstance, map.get(field.getName()));
                        continue;
                    }

                    if (field.getType() == Integer.class) {
                        field.set(aClassInstance, new Integer((String) map.get(field.getName())));
                        continue;
                    }

                    field.set(aClassInstance, createObject(field.getType()));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return aClassInstance;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        content.append(ch, start, length);
    }
}