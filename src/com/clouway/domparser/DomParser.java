package com.clouway.domparser;

import com.clouway.domparser.core.Parser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class DomParser<T> implements Parser {
  private Document document;

  @Override
  public <T> List<T> parseList(InputStream inputStream, Class<T> clazz) {
    List<T> objects = new ArrayList<T>();
    try {
      if (inputStream != null) {

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        document = builder.parse(inputStream);

        domParse(clazz, objects);
      }
    } catch (ParserConfigurationException | SAXException | IOException | NoSuchFieldException | InstantiationException | IllegalAccessException | ClassNotFoundException | ParseException e) {
      e.printStackTrace();
    }
    return objects;
  }

  private <T> void domParse(Class<T> clazz, List<T> objects) throws NoSuchFieldException, InstantiationException, IllegalAccessException, ClassNotFoundException, ParseException {
    NodeList nodeList = document.getElementsByTagName(clazz.getSimpleName().toLowerCase());
    T object = getObject(clazz, nodeList.item(0));

    for (int i = 0; i < nodeList.getLength(); i++) {
      objects.add(object);
    }
  }

  private <T> T getObject(Class<T> clazz, Node node) throws NoSuchFieldException, InstantiationException, IllegalAccessException, ClassNotFoundException, ParseException {
    T newObject = clazz.newInstance();

    if (node.getNodeType() == Node.ELEMENT_NODE) {
      Element element = (Element) node;

      Field[] fields = clazz.getDeclaredFields();

      for (Field each : fields) {
        each.setAccessible(true);

        setTagValue(each, newObject, element);
      }
    }
    return newObject;
  }

  private <T> void setTagValue(Field field, T newObject, Element element) throws IllegalAccessException, ClassNotFoundException, NoSuchFieldException, InstantiationException, ParseException {
    String fieldName = field.getName();

    if (field.getType().equals(String.class)) {
      field.set(newObject, getTextValue(fieldName, element));

    } else if (Collection.class.isAssignableFrom(field.getType())) {
      ParameterizedType type = (ParameterizedType) field.getGenericType();
      Class<?> listClass = (Class<?>) type.getActualTypeArguments()[0];
      NodeList nodeList = document.getElementsByTagName(fieldName);

      field.set(newObject, getListOfObjects(nodeList, listClass));
    } else {
      field.set(newObject, getObject(field.getType().newInstance().getClass(), element));
    }
  }

  private String getTextValue(String fieldName, Element element) {
    return element.getElementsByTagName(fieldName).item(0).getTextContent();
  }

  private <E> List<E> getListOfObjects(NodeList nodeList, Class<E> clazz) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException, NoSuchFieldException {
    List<E> list = new ArrayList<>();

    Node node = nodeList.item(0).getFirstChild();

    while (node != null) {

      if (node.getNodeType() == Node.ELEMENT_NODE) {

        E instance = getObject(clazz, node);
        list.add(instance);
      }

      node = node.getNextSibling();
    }
    return list;
  }
}
