package com.clouway.domparser.core;

import java.io.InputStream;
import java.util.List;

/**
 * The implementation of this interface will be used to extraction data from xml documents
 *
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface Parser {
  /**
   * Will return list of all objects from document
   *
   * @param inputStream source file
   * @param clazz       the class
   * @param <T>         object
   * @return list of objects
   */
  <T> List<T> parseList(InputStream inputStream, Class<T> clazz);
}
