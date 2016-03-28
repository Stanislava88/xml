package com.clouway.xml;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class SaxHandler<T> extends DefaultHandler {
    private List<T> elements = new ArrayList<>();
    private final Class clazz;
    private Object parent = null;
    private Object child = null;
    private String field = null;

    public SaxHandler(Class clazz) {
        this.clazz = clazz;
    }

    public List<T> getElements() {
        return elements;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        try {
            if (qName.equalsIgnoreCase(clazz.getSimpleName())) {

                parent = clazz.newInstance();

            } else {
                Field[] fields = child == null ? clazz.getDeclaredFields() : child.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (qName.equalsIgnoreCase(field.getName())) {
                        if (field.getType().isPrimitive() || field.getType().equals(String.class) || field.getType().equals(Integer.class) || field.getType().equals(Date.class)) {
                            this.field = field.getName();

                        } else {
                            child = Class.forName(field.getType().getName()).newInstance();
                        }
                    }
                }
            }

        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        try {
            if (qName.equalsIgnoreCase(clazz.getSimpleName())) {
                elements.add((T) parent);
                parent = null;
            } else {
                if (child != null) {
                    String childObjectName = child.getClass().getSimpleName();

                    if (qName.equalsIgnoreCase(childObjectName)) {
                        Field field = getDeclaredFieldIgnoreCase(parent, childObjectName);
                        field.setAccessible(true);
                        field.set(parent, child);
                        child = null;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Field getDeclaredFieldIgnoreCase(Object object, String fieldName) {
        Field field = null;
        for (Field field1 : parent.getClass().getDeclaredFields()) {
            if (field1.getName().equalsIgnoreCase(fieldName)) {
                field = field1;
            }
        }
        return field;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        try {
            if (field != null) {
                if (child == null) {
                    Field field = parent.getClass().getDeclaredField(this.field);
                    field.setAccessible(true);
                    putInField(parent, new String(ch, start, length), field);
                    this.field = null;
                } else {
                    Field field = child.getClass().getDeclaredField(this.field);
                    field.setAccessible(true);
                    putInField(child, new String(ch, start, length), field);
                    this.field = null;
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void putInField(Object object, String value, Field field) throws IllegalAccessException {
        if (field.getType().equals(String.class)) {
            field.set(object, value);
        } else if (field.getType().getName().contains("Integer")) {
            field.set(object, new Integer(value));
        } else if (field.getType().getName().contains("Date")) {
            field.set(object, java.sql.Date.valueOf(value));
        }
    }
}
