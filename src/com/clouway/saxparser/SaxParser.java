package com.clouway.saxparser;

import com.clouway.saxparser.core.Parser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class SaxParser implements Parser {
  @Override
  public <T> List<T> parseList(InputStream inputStream, Class<T> clazz) {
    List<T> objects = new ArrayList<>();

    SAXParserFactory factory = SAXParserFactory.newInstance();

    try {
      if (inputStream == null) {
        return objects;
      }
      SAXParser parser = factory.newSAXParser();
      SaxHandler handler = new SaxHandler(clazz);

      parser.parse(inputStream, handler);

      objects = handler.getListOfObjects();
    } catch (ParserConfigurationException | org.xml.sax.SAXException | IOException e) {
      e.printStackTrace();
    }

    return objects;
  }
}
