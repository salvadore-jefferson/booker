/*
 * Copyright © 2015 Salvadore Jefferson
 */

package com.jefferson.salvadore.booker.repo.snapshot;

import static com.jefferson.salvadore.booker.publication.PubFreq.MONTHLY;
import static com.jefferson.salvadore.booker.publication.PubFreq.NA;
import static com.jefferson.salvadore.booker.publication.PubFreq.UNDISCLOSED;
import static com.jefferson.salvadore.booker.publication.PubFreq.WEEKLY;
import static com.jefferson.salvadore.booker.publication.PubFreq.pubFreqOf;
import static com.jefferson.salvadore.booker.publication.Range.of;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import com.jefferson.salvadore.booker.publication.Book;
import com.jefferson.salvadore.booker.publication.Journal;
import com.jefferson.salvadore.booker.publication.Magazine;
import com.jefferson.salvadore.booker.publication.Publication;
import com.jefferson.salvadore.booker.repo.PublicationRepository;

/**
 * A snapshot of the best-selling publications according to Wikipedia on 08-28-2015
 * 
 * @version 2.0.0 12-02-2015
 * @author Salvadore Jefferson
 */
public class SnapshotTopPublicationRepository implements PublicationRepository {

  private Integer inventoryCount = 0;
  private final ArrayList<Publication> publicationList = new ArrayList<>();
  private final Map<String, Integer> publicationMap = new HashMap<>();

  public SnapshotTopPublicationRepository() {

    publicationList.add(
        new Book("A Tale of Two Cities", "Charles Dickens", of(0), 200_000_000, pubFreqOf(NA)));
    publicationList.add(new Book("The Lord of The Rings", "J.R.R Tolkien", of(1954, 1955),
        150_000_000, pubFreqOf(NA)));
    publicationList
        .add(new Book("The Hobbit", "J.R.R Tolkien", of(1937), 140_600_000, pubFreqOf(NA)));
    publicationList.add(new Book("Harry Potter and the Philosopher's Stone", "J. K. Rowling",
        of(1997), 107_000_000, pubFreqOf(NA)));
    publicationList.add(new Book("Le Petit Prince", "Antoine de Saint-Exupéry", "French", of(1943),
        140_000_000, pubFreqOf(NA)));
    publicationList.add(new Book("紅樓夢/红楼梦 (Dream of the Red Chamber)", "Cao Xueqin", of(1754, 1791),
        100_000_000, pubFreqOf(NA)));
    publicationList
        .add(new Book("And Then There Were None", "Agatha Christie", of(1939), pubFreqOf(NA)));
    publicationList
        .add(new Book("She: A History of Adventure", "H. Rider Haggard", of(1887), pubFreqOf(NA)));
    publicationList
        .add(new Magazine("AARP The Magazine", "AARP", of(1958), 22_274_096, pubFreqOf(MONTHLY)));
    publicationList
        .add(new Magazine("AARP Bulletin", "AARP", of(1960), 22_244_820, pubFreqOf(MONTHLY)));
    publicationList.add(new Magazine("Costco Connection", "Costco Wholesale", of(0), 8_654_464,
        pubFreqOf(MONTHLY)));
    publicationList.add(new Magazine("Better Homes And Gardens", "Meredith", of(1922), 7_615_581,
        pubFreqOf(MONTHLY)));
    publicationList
        .add(new Magazine("Game Informer", "GameStop", of(1991), 7_629_995, pubFreqOf(MONTHLY)));
    publicationList.add(new Journal("PLOS ONE", "Public Library of Science", of(2006),
        pubFreqOf(UNDISCLOSED), "Multidisciplinary", 3.234));
    publicationList.add(new Journal("Philosophical Transactions of the Royal Society",
        "Royal Society", of(1665), pubFreqOf(UNDISCLOSED), "Physical Sciences", 7.055));
    publicationList.add(new Journal("Nature Communications", "Nature Publishing Group", of(2010),
        pubFreqOf(UNDISCLOSED), "Earth Sciences", 11.47));
    publicationList.add(new Journal("Nature", "Nature Publishing Group", of(1869),
        pubFreqOf(WEEKLY), "Natural Science", 41.456));
    publicationList.add(new Journal(
        "Proceedings of the National Academy of Sciences of the United States of America",
        "United States National Academy of Sciences", of(1914), pubFreqOf(WEEKLY),
        "Multidisciplinary", 9.674));

    buildMap(publicationList);
  }

  @Override
  public void addPub(Publication publication) throws IOException {
    final String title = publication.getTitle();
    publicationList.add(publication);
    publicationMap.put(title, inventoryCount);

  }

  @Override
  public void removePubByTitle(String title) throws IOException {
    for (int i = 0; i < publicationList.size(); i++) {
      final Publication publication = publicationList.get(i);
      if (publication.getTitle().equals(title)) {
        publicationList.remove(publication);
      }
    }
  }


  @Override
  public Publication findByTitle(String title) throws IOException {
    for (int i = 0; i < publicationList.size(); i++) {
      final Publication publication = publicationList.get(i);
      if (publication.getTitle().equals(title)) {
        return publication;
      }
    }
    throw new NoSuchElementException("this title does not exist: " + title);
  }

  @Override
  public <P extends Publication> List<P> getByType(Class<P> publicationType) throws IOException {
    final List<P> theList = new ArrayList<P>();
    for (int i = 0; i < publicationList.size(); i++) {
      final Publication publication = publicationList.get(i);
      if (publicationType.isInstance(publication)) {
        theList.add(publicationType.cast(publication));
      }
    }
    return theList;
  }

  @Override
  public void printAllPubs() {
    for (Publication pub : publicationList) {
      System.out.println(pub);
    }

  }

  @Override
  public void setInventoryCount(String title, int count) {
    publicationMap.replace(title, count);

  }

  @Override
  public Integer getInventoryCount(String title) {
    return publicationMap.getOrDefault(title, inventoryCount);
  }

  /**
   * Builds a {@link HashMap} based on the contents of the local {@link List} of
   * {@link Publications}. The <code>Publications</code> title is used for the <code>key</code> and
   * the inventory count is set to zero by default.
   * 
   * @param pubList The List of <code>Publication</code>s contained in this class.
   */
  private void buildMap(@Nonnull final List<Publication> pubList) {
    for (Publication pub : pubList) {
      publicationMap.put(pub.getTitle(), inventoryCount);
    }
  }

  /**
   * Returns the size of the repository
   * 
   * @return int The repository's size
   */
  public int getRepoSize() {
    return publicationList.size();
  }
}
