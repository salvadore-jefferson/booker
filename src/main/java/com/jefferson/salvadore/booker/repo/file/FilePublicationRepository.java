/*
 * Copyright © 2015 Salvadore Jefferson
 */

package com.jefferson.salvadore.booker.repo.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import com.jefferson.salvadore.booker.util.IO;
import com.jefferson.salvadore.booker.publication.Publication;
import com.jefferson.salvadore.booker.repo.PublicationRepository;
import com.jefferson.salvadore.booker.repo.snapshot.SnapshotTopPublicationRepository;

/**
 * A repository for saving {@link Publication}s to the file system.
 * 
 * @author Salvadore Jefferson
 * @version 3.0.0 12-02-2-15
 *
 */
public class FilePublicationRepository implements PublicationRepository {

  private Integer inventoryCount = 20;
  private static final String PUB_FILE_EXT = ".pub.dat";
  private static final String STOCK_FILE_EXT = ".stock.txt";
  private final List<Publication> pubList = new ArrayList<>();
  private static final byte[] SIG_FILE_BYTES =
      new byte[] {0x02, 0x03, 0x04, (byte) 0xEA, 0x00, 0x00, 0x00, (byte) 0xEA};
  private final Path repoPath;

  /**
   * A <code>private</code> constructor used by {@link #initialize()} for creating new instances of
   * <code>FilePublicationRepository</code>.
   * 
   * @throws IOException
   */
  public FilePublicationRepository(@Nonnull final Path repoPath) throws IOException {
    this.repoPath = repoPath;
  }

  @Override
  public void addPub(@Nonnull final Publication publication) throws IOException {
    final String title = publication.getTitle();
    final String fileName = encodeFileName(title + PUB_FILE_EXT);
    final String inventoryFile = encodeFileName(title + STOCK_FILE_EXT);
    try (final ObjectOutputStream outputStream = new ObjectOutputStream(
        new BufferedOutputStream(Files.newOutputStream(repoPath.resolve(fileName))))) {
      outputStream.writeObject(publication);
    }
    try (final Writer writeInventoryFile =
        new PrintWriter(Files.newOutputStream(repoPath.resolve(inventoryFile)))) {
      writeInventoryFile.write(inventoryCount.toString());;
    }
    addPubToList(publication);
  }

  @Override
  public void removePubByTitle(@Nonnull final String title) throws IOException {
    for (int i = 0; i < pubList.size(); i++) {
      final Publication publication = pubList.get(i);
      if (publication.getTitle().equals(title)) {
        pubList.remove(publication);
      }
    }
    final Path fileRemove = repoPath.resolve(encodeFileName(title) + PUB_FILE_EXT);
    try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(repoPath, "*.pub.dat")) {
      for (Path file : dirStream) {
        if (fileRemove.getFileName().equals(file.getFileName())) {
          Files.delete(fileRemove);
        }
      }
    }
    final Path inventoryRemove = repoPath.resolve(encodeFileName(title) + STOCK_FILE_EXT);
    try (DirectoryStream<Path> dirStream2 = Files.newDirectoryStream(repoPath, "*.stock.txt")) {
      for (Path file2 : dirStream2) {
        if (inventoryRemove.getFileName().equals(file2.getFileName())) {
          Files.delete(inventoryRemove);
        }
      }
    }
  }

  @Override
  public <P extends Publication> List<P> getByType(@Nonnull final Class<P> publicationType)
      throws IOException {
    final List<P> theList = new ArrayList<P>();
    for (Publication publication : pubList) {
      if (publicationType.isInstance(publication)) {
        theList.add(publicationType.cast(publication));
      }
    }
    return theList;

  }

  @Override
  public Publication findByTitle(@Nonnull final String title) throws IOException {
    for (Publication publication : pubList) {
      if (publication.getTitle().equals(title)) {
        return publication;
      }
    }
    throw new NoSuchElementException("this title does not exist: " + title);
  }

  /**
   * Tests the signature.dat file's contents to confirm validity of directory. And populates the
   * repository with <code>Publication</code>s
   * 
   * @throws IOException Thrown if there is a problem accessing the directory, or if the
   *         initialization fails.
   */
  public void initialize() throws IOException {
    final byte[] bytesRead = new byte[SIG_FILE_BYTES.length];
    try (final InputStream stream =
        new BufferedInputStream(Files.newInputStream(repoPath.resolve("signature.dat")))) {
      int byteValue;
      int index = 0;
      while ((byteValue = stream.read()) != -1) {
        bytesRead[index] = (byte) byteValue;
        index++;
      }
      if (!Arrays.equals(bytesRead, SIG_FILE_BYTES) && stream.read() != -1) {
        throw new IOException("no valid directory was found");
      }
    }
    addAllPublications(new SnapshotTopPublicationRepository());
  }

  /**
   * Reads a file from disc and prints out the contents to the console.
   * 
   * @param fileName The name of the file to read.
   * @throws IOException Thrown if there is a problem accessing the directory.
   */
  @Deprecated
  public void readFile(@Nonnull final String fileName) throws IOException {
    final String name = encodeFileName(fileName) + PUB_FILE_EXT;
    try (final InputStream stream =
        new BufferedInputStream(Files.newInputStream(repoPath.resolve(name)))) {
      int byteValue;
      while ((byteValue = stream.read()) != -1) {
        System.out.print(byteValue + ", ");
      }
    }
  }

  /**
   * Reads a Java <code>Object</code> file from disk.
   * 
   * @param fileName The name of the file to read.
   * @return Publication The <code>Object</code> returned from this method
   * @throws IOException If there is a problem accessing the file directory
   * @throws ClassCastException
   * @throws ClassNotFoundException
   */
  public Publication deserializeFile(@Nonnull final String fileName)
      throws IOException, ClassCastException, ClassNotFoundException {
    final String name = encodeFileName(fileName) + PUB_FILE_EXT;
    Publication pub = null;
    try (final ObjectInputStream inputStream = new ObjectInputStream(
        new BufferedInputStream(Files.newInputStream(repoPath.resolve(name))))) {
      pub = (Publication) inputStream.readObject();
    }
    return pub;
  }

  /**
   * Checks to see if the {@link #pubList} contains the specified {@link Publication}. If the
   * <code>Publication</code> is in the list, the <code>Object</code> is overridden with the new
   * instance. If not the <code>Publication</code> is added to <code>pubList</code>
   * 
   * @param publication The <code>Publication</code> to add to the repository.
   */
  private void addPubToList(@Nonnull final Publication publication) {
    final Publication newPub = publication;

    if (pubList.contains(publication)) {
      pubList.remove(publication);
      pubList.add(newPub);
    } else {
      pubList.add(publication);
    }
  }

  /**
   * Returns the size of the repository in memory
   * 
   * @return int The number of <code>Publications</code> in the list
   */
  public int getListSize() {
    return pubList.size();
  }

  @Override
  public void printAllPubs() {
    throw new UnsupportedOperationException();
  }

  /**
   * Provides default encoding of a <code>String</code> to supported file-name characters
   * 
   * @param fileName The title of the file or <code>Publication</code>
   * @return An encoded version of the provided String
   * @throws UnsupportedEncodingException
   */
  private String encodeFileName(@Nonnull final String fileName)
      throws UnsupportedEncodingException {
    return java.net.URLEncoder.encode(fileName, "UTF-8");
  }

  @Override
  public void setInventoryCount(String title, int count) throws IOException {
    inventoryCount = count;
    final String inventoryFile = encodeFileName(title + STOCK_FILE_EXT);
    final Path checkFile = repoPath.resolve(encodeFileName(title) + STOCK_FILE_EXT);
    try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(repoPath, "*.stock.txt")) {
      for (Path file : dirStream) {
        if (checkFile.getFileName().equals(file.getFileName())) {
          try (final Writer writeInventoryFile =
              new PrintWriter(Files.newOutputStream(repoPath.resolve(inventoryFile)))) {
            writeInventoryFile.write(inventoryCount.toString());
            return;
          }
        }
      }
    }
    throw new IOException(new NoSuchFileException("The title you are referencing ["
        + checkFile.getFileName() + "] is not present in the repository"));
  }

  @Override
  public Integer getInventoryCount(String title) throws IOException {
    final Path readFile = repoPath.resolve(encodeFileName(title) + STOCK_FILE_EXT);
    try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(repoPath, "*.stock.txt")) {
      for (Path file : dirStream) {
        if (readFile.getFileName().equals(file.getFileName())) {
          inventoryCount = Integer.valueOf(IO.readText(readFile));
        }
      }
    }
    return inventoryCount;

  }
}
