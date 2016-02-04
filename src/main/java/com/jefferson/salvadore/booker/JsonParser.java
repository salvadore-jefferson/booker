/*
 * Copyright © 2016 Salvadore Jefferson
 */
package com.jefferson.salvadore.booker;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jefferson.salvadore.booker.publication.Book;
import com.jefferson.salvadore.booker.publication.PubFreq;

import com.jefferson.salvadore.booker.publication.Range;
import static com.jefferson.salvadore.booker.publication.PubFreq.NA;


/**
 * A helper class for parsing JSON data from an Internet resource and creating a corresponding
 * {@Link Book}.
 * 
 * @author Salvadore Jefferson
 * @version 1.0.0 1-11-16
 *
 */
public class JsonParser {

  /**
   * Parses JSON objects from an InputStream and creates an {@link Book} object from the results.
   * 
   * @param source The InputStream to parse
   * @return book A new instance of <code>Book</code>
   * @throws JsonParseException
   * @throws JsonMappingException
   * @throws IOException
   */
  public static Book parseJson(@Nonnull final InputStream source)
      throws JsonParseException, JsonMappingException, IOException {
    final ObjectMapper mapper = new ObjectMapper();
    final JsonNode rootNode = mapper.readTree(source);
    final JsonNode authors = rootNode.findValue("authors");

    final String author = authors.get(0).textValue();
    final String title = rootNode.findValue("title").textValue();
    final String year = (String) rootNode.findValue("publishedDate").textValue().subSequence(0, 4);
    final int yearPublished = Integer.parseInt(year);

    final Book book = new Book(title, author, Range.of(yearPublished), PubFreq.pubFreqOf(NA));
    return book;
  }
}
