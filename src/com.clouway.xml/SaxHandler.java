package com.clouway.xml;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class SaxHandler<T> extends DefaultHandler {
    List<T> elements = new ArrayList<>();
    private Class clazz = elements.getClass();
    private Object parent = null;
    private Object child = null;
    private String primitiveField = null;


    public SaxHandler(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        try {
            if (qName.equalsIgnoreCase(clazz.getSimpleName())) {
                parent = clazz.newInstance();
            } else {
                Field[] fields = getCurrentDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (qName.equalsIgnoreCase(field.getName())) {
                        if (field.getType().isPrimitive() || field.getType().equals(String.class)) {
                            primitiveField = field.getName();
                        } else{
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
            if (primitiveField != null) {
                if (child==null) {
                    Field field = parent.getClass().getDeclaredField(primitiveField);
                    field.setAccessible(true);
                    putInField(parent, new String(ch, start, length), field);
                    primitiveField = null;
                }else{
                    Field field = child.getClass().getDeclaredField(primitiveField);
                    field.setAccessible(true);
                    putInField(child, new String(ch, start, length), field);
                    primitiveField = null;
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void putInField(Object object, String value, Field field) throws IllegalAccessException {
        if (field.getType().equals(String.class)){
            field.set(object, value);
        }else if (field.getType().getName().contains("int")){
            field.setInt(object, new Integer(value));
        }
    }

    public Field[] getCurrentDeclaredFields() {
        if(child!=null){
            return child.getClass().getDeclaredFields();
        }else{
            return clazz.getDeclaredFields();
        }
    }
}
