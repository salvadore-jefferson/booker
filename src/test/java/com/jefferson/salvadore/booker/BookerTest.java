/*
 * Copyright © 2015 Salvadore Jefferson
 */
package com.jefferson.salvadore.booker;

import static com.jefferson.salvadore.booker.deprecated.SourceCodeReference.buildHashMap;
import static com.jefferson.salvadore.booker.deprecated.SourceCodeReference.buildListByType;
import static com.jefferson.salvadore.booker.deprecated.SourceCodeReference.findByName;
import static com.jefferson.salvadore.booker.deprecated.SourceCodeReference.getPublication;
import static com.jefferson.salvadore.booker.publication.PubFreq.MONTHLY;
import static com.jefferson.salvadore.booker.publication.PubFreq.NA;
import static com.jefferson.salvadore.booker.publication.PubFreq.UNDISCLOSED;
import static com.jefferson.salvadore.booker.publication.PubFreq.WEEKLY;
import static com.jefferson.salvadore.booker.publication.PubFreq.pubFreqOf;
import static com.jefferson.salvadore.booker.publication.Range.of;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.jefferson.salvadore.booker.publication.Book;
import com.jefferson.salvadore.booker.publication.Journal;
import com.jefferson.salvadore.booker.publication.Magazine;
import com.jefferson.salvadore.booker.publication.PubFreq;
import com.jefferson.salvadore.booker.publication.Publication;


public class BookerTest {

  @Test
  public void testBuildHashTableMethod() {
    final ArrayList<Publication> publications = new ArrayList<>();
    publications
        .add(new Book("And Then There Were None", "Agatha Christie", of(1939), pubFreqOf(NA)));
    publications.add(new Magazine("Better Homes And Gardens", "Meredith", of(1922), 7_615_581,
        pubFreqOf(MONTHLY)));
    publications.add(new Journal("Nature", "Nature Publishing Group", of(1869), pubFreqOf(WEEKLY),
        "Natural Science", 41.456));

    HashMap<String, Publication> table = buildHashMap(publications);
    assertThat(table.containsKey("And Then There Were None"), is(true));
    assertThat(table.containsKey("Better Homes And Gardens"), is(true));
    assertThat(table.containsKey("Nature"), is(true));

  }

  @Test
  public void testGetPublicationMethod() {
    final ArrayList<Publication> publications = new ArrayList<>();
    publications
        .add(new Book("And Then There Were None", "Agatha Christie", of(1939), pubFreqOf(NA)));
    publications.add(new Magazine("Better Homes And Gardens", "Meredith", of(1922), 7_615_581,
        pubFreqOf(MONTHLY)));
    publications.add(new Journal("Nature", "Nature Publishing Group", of(1869), pubFreqOf(WEEKLY),
        "Natural Science", 41.456));

    HashMap<String, Publication> table = buildHashMap(publications);
    assertThat(getPublication(table, "Nature"), is(sameInstance(publications.get(2))));
  }

  @Test
  public void testFindByNameMethod() {
    final ArrayList<Publication> publications = new ArrayList<>();
    publications
        .add(new Book("And Then There Were None", "Agatha Christie", of(1939), pubFreqOf(NA)));
    publications.add(new Magazine("Better Homes And Gardens", "Meredith", of(1922), 7_615_581,
        pubFreqOf(MONTHLY)));
    publications.add(new Journal("Nature", "Nature Publishing Group", of(1869), pubFreqOf(WEEKLY),
        "Natural Science", 41.456));

    assertThat(findByName("Nature", publications), is(sameInstance(publications.get(2))));
  }

  @Test
  public void testBuildListByType() {
    final ArrayList<Publication> publications = new ArrayList<>();
    publications
        .add(new Book("And Then There Were None", "Agatha Christie", of(1939), pubFreqOf(NA)));
    publications.add(new Magazine("Better Homes And Gardens", "Meredith", of(1922), 7_615_581,
        pubFreqOf(MONTHLY)));
    publications.add(new Journal("Nature", "Nature Publishing Group", of(1869), pubFreqOf(WEEKLY),
        "Natural Science", 41.456));
    publications
        .add(new Book("And Then There Were More", "Agatha Christie", of(1939), pubFreqOf(NA)));
    publications.add(new Magazine("Better Homes And And Better Gardens", "Meredith", of(1922),
        7_615_581, pubFreqOf(MONTHLY)));
    publications.add(new Journal("Nature Week", "Nature Publishing Group", of(1869),
        pubFreqOf(WEEKLY), "Natural Science", 41.456));


    List<Book> bookList = buildListByType(publications, Book.class);
    List<Journal> journalList = buildListByType(publications, Journal.class);
    List<Magazine> magazineList = buildListByType(publications, Magazine.class);

    assertThat(findByName("And Then There Were More", bookList),
        is(sameInstance(publications.get(3))));
    assertThat(bookList.get(1).getTitle(), is("And Then There Were More"));

    assertThat(findByName("Nature Week", journalList), is(sameInstance(publications.get(5))));
    assertThat(journalList.get(1).getTitle(), is("Nature Week"));

    assertThat(findByName("Better Homes And And Better Gardens", magazineList),
        is(sameInstance(publications.get(4))));
    assertThat(magazineList.get(1).getTitle(), is("Better Homes And And Better Gardens"));
  }

  @Test
  public void testEnumValuesWithOrdinal() {
    PubFreq first = PubFreq.WEEKLY;
    assertThat(PubFreq.values()[first.ordinal()], is(WEEKLY));
    assertThat(PubFreq.values()[first.ordinal() + 1], is(MONTHLY));
    assertThat(PubFreq.values()[first.ordinal() + 2], is(UNDISCLOSED));
    assertThat(PubFreq.values()[first.ordinal() + 3], is(NA));

    assertThat(PubFreq.values()[first.ordinal()].getFreqValue(), is(52));
    assertThat(PubFreq.values()[first.ordinal() + 1].getFreqValue(), is(12));
    assertThat(PubFreq.values()[first.ordinal() + 2].getFreqValue(), is(0));
    assertThat(PubFreq.values()[first.ordinal() + 3].getFreqValue(), is(-1));
  }

  @Test
  public void testGuavaImmutableList() {
    ImmutableList.Builder<String> list = ImmutableList.builder();
    list.add("Foo");
    list.add("Bar");
    list.add("foo bar");
    ImmutableList<String> theList = list.build();

    assertThat(theList.contains("Foo"), is(true));
    assertThat(theList.contains("Foo Bar"), is(false));
  }
}
