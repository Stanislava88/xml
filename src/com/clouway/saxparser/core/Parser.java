package com.clouway.saxparser.core;

import java.io.InputStream;
import java.util.List;

/**
 * The implementation of this interface will be used to retrieve data from xml document
 */
public interface Parser {
  /**
   * Will return list of all objects
   *
   * @param inputStream source file
   * @param clazz       the class
   * @param <T>         object
   * @return list of objects
   */
  <T> List<T> parseList(InputStream inputStream, Class<T> clazz);
}
