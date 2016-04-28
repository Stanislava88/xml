package com.clouway.saxparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class SaxHandler<T> extends DefaultHandler {
  private String value;
  private T root;
  private Object child;
  private Class<T> clazz;
  private List<T> children = new ArrayList<T>();
  private List<T> subChildren = new ArrayList<>();
  private List<T> rootElement = new ArrayList<>();

  public SaxHandler(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    try {
      if (qName.equalsIgnoreCase(clazz.getSimpleName())) {
        root = clazz.newInstance();
        rootElement.add(root);
      }

      boolean isRoot = root.getClass().getSimpleName().equalsIgnoreCase(clazz.getSimpleName());
      boolean isChild = qName.equalsIgnoreCase(root.getClass().getSimpleName());

      if (root != null && !isRoot && isChild) {
        children.add(root);
      }

      if (root != null) {
        createChildren(qName);
      }

      if (child != null) {
        createSubChildren(qName, child, subChildren);
      }

    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    value = new String(ch, start, length).trim();
    if (value.length() == 0) {
      return;
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (child == null) {
      setValue(qName, root);

    } else {
      setValue(qName, child);
    }
  }

  public List<T> getListOfObjects() {
    return rootElement;
  }

  private void createChildren(String qName) throws InstantiationException, IllegalAccessException {
    Field[] fields = root.getClass().getDeclaredFields();

    for (Field field : fields) {
      field.setAccessible(true);

      String fieldName = field.getName();

      if (qName.equalsIgnoreCase(fieldName)) {

        if (!field.getType().equals(String.class) && !isCollection(field)) {
          child = field.getType().newInstance();
          field.set(root, child);
        }

        if (isCollection(field)) {
          createCollectionObject(qName, field, root);
        }
      }
    }
  }

  private void createSubChildren(String qName, Object object, List<T> list) throws InstantiationException, IllegalAccessException {
    Field[] fields = object.getClass().getDeclaredFields();
    for (Field field : fields) {

      field.setAccessible(true);

      if (isCollection(field)) {
        ParameterizedType type = (ParameterizedType) field.getGenericType();
        Class<?> listClass = (Class<?>) type.getActualTypeArguments()[0];

        if (qName.equalsIgnoreCase(field.getName())) {
          child = listClass.newInstance();
          list.add((T) child);

          field.set(object, list);
        }
      }
    }
  }

  private void createCollectionObject(String qName, Field field, Object object) throws IllegalAccessException, InstantiationException {
    ParameterizedType type = (ParameterizedType) field.getGenericType();
    Class<?> listClass = (Class<?>) type.getActualTypeArguments()[0];

    if (qName.equalsIgnoreCase(field.getName())) {
      root = (T) listClass.newInstance();

      field.set(object, children);
    }
  }

  private void setValue(String qName, Object object) throws SAXException {
    Field[] fields = object.getClass().getDeclaredFields();
    for (Field field : fields)
      try {
        field.setAccessible(true);

        if (qName.equalsIgnoreCase(field.getName())) {

          if (field.getType().equals(String.class)) {
            field.set(object, value);
          }
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
  }

  private boolean isCollection(Field field) {
    return Collection.class.isAssignableFrom(field.getType());
  }
}