/*
 * Copyright © 2015 Salvadore Jefferson
 */
package com.jefferson.salvadore.booker.repo.file;

import static com.jefferson.salvadore.booker.publication.PubFreq.*;
import static com.jefferson.salvadore.booker.publication.Range.of;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;

import com.jefferson.salvadore.booker.publication.Book;
import com.jefferson.salvadore.booker.repo.file.FilePublicationRepository;
import com.jefferson.salvadore.booker.repo.snapshot.SnapshotTopPublicationRepository;


public class FilePublicationRepositoryTest {

  private static byte[] sigBytes;

  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  @BeforeClass
  public static void setUpSigFile() {
    sigBytes = new byte[] {0x02, 0x03, 0x04, (byte) 0xEA, 0x00, 0x00, 0x00, (byte) 0xEA};
  }

  @Test
  public void writeToEmptyRepository() throws IOException {
    int byteValue;
    final Path tempDirRootPath = tempFolder.getRoot().toPath();
    final Path sigFilePath = tempDirRootPath.resolve("signature.dat");
    final FilePublicationRepository fileRepo = new FilePublicationRepository(tempDirRootPath);
    final OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(sigFilePath));
    for (int i = 0; i < sigBytes.length; i++) {
      byteValue = sigBytes[i];
      outputStream.write(byteValue);
    }
    fileRepo.initialize();
    fileRepo.addPub(new Book("Title", "Author", of(0), 200_000_000, pubFreqOf(NA)));

  }

  @Test(expected = NoSuchElementException.class)
  public void workingWithPopulatedRepository()
      throws IOException, ClassCastException, ClassNotFoundException {
    int byteValue;
    final Path tempDirRootPath = tempFolder.getRoot().toPath();
    final Path sigFilePath = tempDirRootPath.resolve("signature.dat");
    final FilePublicationRepository fileRepo = new FilePublicationRepository(tempDirRootPath);
    final OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(sigFilePath));
    for (int i = 0; i < sigBytes.length; i++) {
      byteValue = sigBytes[i];
      outputStream.write(byteValue);
    }
    fileRepo.initialize();
    fileRepo.addAllPublications(new SnapshotTopPublicationRepository());

    assertThat(fileRepo.deserializeFile("紅樓夢/红楼梦 (Dream of the Red Chamber)").getTitle(),
        is("紅樓夢/红楼梦 (Dream of the Red Chamber)"));
    fileRepo.addPub(new Book("Test Title", "Test Author", of(0), 200_000_000, pubFreqOf(NA)));
    assertThat(fileRepo.findByTitle("Test Title").getTitle(), is("Test Title"));
    fileRepo.removePubByTitle("Test Title");
    fileRepo.findByTitle("Test Title");
  }

  @AfterClass
  public static void cleanUpBytes() {
    sigBytes = null;

  }
}
