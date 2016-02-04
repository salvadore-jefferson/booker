/*
 * Copyright © 2015 Salvadore Jefferson
 */
package com.jefferson.salvadore.booker.util;

import static com.jefferson.salvadore.booker.util.IO.readText;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

public class IOTest {

  @Test
  public void testByteArryNoBOM() throws IOException {
    final byte[] noBOM = new byte[] {0x61, 0x62, 0x63};
    assertThat(readText(new ByteArrayInputStream(noBOM)), is("abc"));
  }

  @Test
  public void testWithBOM() throws IOException {
    final byte[] withBOM = new byte[] {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF, 0x61, 0x62, 0x63};
    assertThat(readText(new ByteArrayInputStream(withBOM)), is("abc"));
  }

  @Test
  public void testTouché() throws IOException {
    final byte[] touché = new byte[] {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF, 0x74, 0x6F, 0x75, 0x63,
        0x68, (byte) 0xC3, (byte) 0xA9};
    assertThat(readText(new ByteArrayInputStream(touché)), is("touché"));
  }

  @Test
  public void testMA() throws IOException {
    final byte[] MA;
    MA = new byte[] {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF, (byte) 0xE0, (byte) 0xA4, (byte) 0xAE};
    assertThat(readText(new ByteArrayInputStream(MA)), is("म"));
  }
}
