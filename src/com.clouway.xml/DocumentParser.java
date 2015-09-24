package com.clouway.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class DocumentParser<T> {

  private final Class<T> clazz;

  public DocumentParser(Class<T> clazz) {
    this.clazz = clazz;
  }

  public List<T> parse(Document doc) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
    List<T> objects = new ArrayList<>();
    NodeList childNodes = doc.getDocumentElement().getChildNodes();
    objectsTree(childNodes, objects, 1);
    return objects;
  }

  private void objectsTree(NodeList childNodes, List<T> objects, int i) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchFieldException {
    Object object;
    if (childNodes.item(i).getNodeName().equalsIgnoreCase(clazz.getSimpleName())) {
      object = clazz.newInstance();
      fillFields(object, childNodes.item(i));
      objects.add((T) object);
    }
    if (i < childNodes.getLength() - 1) {
      objectsTree(childNodes, objects, i + 1);
    }
  }

  private void fillFields(Object object, Node item) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
    NodeList nodeList = item.getChildNodes();
    for (int i = 0; i < nodeList.getLength() - 1; i++) {
      Field field = null;
      try {
        field = object.getClass().getDeclaredField(nodeList.item(i).getNodeName());
      } catch (NoSuchFieldException e) {
        field = null;
      }
      if (field != null) {
        field.setAccessible(true);
        fillField(field, object, nodeList.item(i));
      }
    }
  }

  private void fillField(Field field, Object object, Node item) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchFieldException {
    if (field.getType().equals(String.class)) {
      field.set(object, item.getFirstChild().getNodeValue());
    } else if (field.getType().equals(Integer.class)) {
      field.set(object, Integer.parseInt(item.getFirstChild().getNodeValue()));
    } else if (field.getType().equals(java.util.Date.class)) {
      field.set(object, Date.valueOf(item.getFirstChild().getNodeValue()));
    } else if (item.hasChildNodes()) {
      Class fieldClass = field.getType();
      Object fieldObject = fieldClass.newInstance();
      fillFields(fieldObject, item);
      field.set(object, fieldObject);
    }
  }

}
