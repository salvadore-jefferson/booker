/*
 * Copyright © 2015 Salvadore Jefferson
 */

package com.jefferson.salvadore.booker.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import javax.annotation.Nonnull;

/**
 * <p>
 * A utility class for reading from Input Streams and writing to Output Streams.
 * </p>
 * 
 * @author Salvadore Jefferson
 * @version 3.0.0 12-02-2015
 */
public class IO {

  private static final byte[] UTF8_BOM = new byte[] {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
  private static final byte[] UTF16LE_BOM = new byte[] {(byte) 0xFF, (byte) 0xFE};
  private static final byte[] UTF16BE_BOM = new byte[] {(byte) 0xFE, (byte) 0xFF};

  /**
   * Reads a file from the users hard drive and prints the contents to the console. Delegates the
   * logic execution to the {@link #readText(InputStream)} method.
   * 
   * @param path The <code>Path</code> to the file to be read.
   * @return String The text contained in the file.
   * @throws IOException Thrown if there is a problem loading the file.
   */
  public static String readText(@Nonnull final Path path) throws IOException {
    try (final InputStream stream = Files.newInputStream(path)) {
      return readText(stream);
    }
  }

  /**
   * Reads the <code>Bytes</code> from an <code>Input Stream</code>.
   * 
   * @param stream The Input Stream to read.
   * @return String The contents of the Input Stream.
   * @throws IOException Thrown if there is a problem accessing the InputStream.
   */
  public static String readText(@Nonnull final InputStream stream) throws IOException {
    final StringBuilder builder = new StringBuilder();
    final BufferedInputStream bufferedStream = new BufferedInputStream(stream);
    int byteValue;
    try (final Reader reader = new InputStreamReader(bufferedStream, getCharset(bufferedStream))) {

      while ((byteValue = reader.read()) != -1) {
        builder.appendCodePoint(byteValue);
      }
    }
    return builder.toString();
  }

  /**
   * Tests the <code>InputStream</code> to determine if a <code>BOM</code>(Byte Order Mark) is
   * present. If a <code>BOM</code> is found, the appropriate Character Set is returned. If no
   * <code>BOM</code> is found, a default <code>Charset</code> of UTF-8 is returned.
   * 
   * @param stream An <code>BufferedInputStream</code> to be read
   * @return Charset The <code>Charset</code> used to encode this InputStream. If no Charset is
   *         specified, the default will be <code>StandardCharsets.UTF_8</code>
   * @throws IOException Thrown if there is a problem accessing the InputStream.
   */
  public static Charset getCharset(@Nonnull final BufferedInputStream stream) throws IOException {
    final byte[] bomBuffer = new byte[3];
    Charset theSet = StandardCharsets.UTF_8;
    stream.mark(3);

    for (int i = 0; i < UTF16LE_BOM.length; i++) {
      bomBuffer[i] = (byte) stream.read();
      if (bomBuffer[i] == -1) {
        return theSet;
      } else if (Arrays.equals(bomBuffer, UTF16LE_BOM)) {
        theSet = StandardCharsets.UTF_16LE;
        return theSet;
      } else if (Arrays.equals(bomBuffer, UTF16BE_BOM)) {
        theSet = StandardCharsets.UTF_16BE;
        return theSet;
      }
    }

    bomBuffer[2] = (byte) stream.read();
    for (int i = 0; i < UTF8_BOM.length; i++) {
      if (bomBuffer[i] != -1) {
        if (bomBuffer[i] == UTF8_BOM[i]) {
          theSet = StandardCharsets.UTF_8;
          return theSet;
        }
      }
    }
    stream.reset();
    return theSet;
  }
}
