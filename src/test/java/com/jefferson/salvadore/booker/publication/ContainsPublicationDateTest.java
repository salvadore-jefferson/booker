package com.jefferson.salvadore.booker.publication;

import static com.jefferson.salvadore.booker.publication.PubFreq.*;
import static com.jefferson.salvadore.booker.deprecated.PublicationBinarySearchTreeImpl.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.jefferson.salvadore.booker.deprecated.PublicationBinarySearchTreeImpl;
import com.jefferson.salvadore.booker.deprecated.PublicationBinaryTreeNode;

public class ContainsPublicationDateTest {

  @Test
  public void testContainsPublicationDateWithOneNode() {
    final Book book = new Book("foo", "bar", Range.of(1999), pubFreqOf(NA));
    final PublicationBinaryTreeNode<Book> node = new PublicationBinaryTreeNode<Book>(book);

    assertThat(node.getValue(), is(sameInstance(book)));
    assertThat(node.getValue().getYearsPublished().getRangeEnd(), is(equalTo(1999)));
    assertThat(book.getYearsPublished().getRangeEnd(), is(equalTo(1999)));
    assertThat(containsPublicationDate(node, 1999), is(true));
  }

  @Test
  public void testContainsPublicationDateWithRootAndLeftChildNode() {
    final Book book1 = new Book("foo", "bar", Range.of(2010), pubFreqOf(NA));
    final Book book2 = new Book("foo", "bar", Range.of(2001), pubFreqOf(NA));

    final PublicationBinaryTreeNode<Book> node = new PublicationBinaryTreeNode<Book>(book1);
    PublicationBinarySearchTreeImpl.add(node, book2);
    assertThat(node.getLeftChild().getValue(), is(sameInstance(book2)));
    assertThat(containsPublicationDate(node, 2010), is(true));
    assertThat(containsPublicationDate(node, 2001), is(true));
  }

  @Test
  public void testContainsPublicationDateWithRootAndRightChildNode() {
    final Book book1 = new Book("foo", "bar", Range.of(2010), pubFreqOf(NA));
    final Book book2 = new Book("foo", "bar", Range.of(2001), pubFreqOf(NA));
    final Book book3 = new Book("foo3", "bar3", Range.of(2012), pubFreqOf(NA));

    final PublicationBinaryTreeNode<Book> node = new PublicationBinaryTreeNode<Book>(book1);

    PublicationBinarySearchTreeImpl.add(node, book2);
    assertThat(node.getLeftChild().getValue(), is(sameInstance(book2)));
    assertThat(node.getRightChild(), is(nullValue()));
    assertThat(containsPublicationDate(node, 2010), is(true));
    assertThat(containsPublicationDate(node, 2001), is(true));

    PublicationBinarySearchTreeImpl.add(node, book3);
    assertThat(node.getRightChild().getValue(), is(sameInstance(book3)));
    assertThat(containsPublicationDate(node, 2010), is(true));
    assertThat(containsPublicationDate(node, 2001), is(true));
    assertThat(containsPublicationDate(node, 2012), is(true));
  }

  @Test
  public void testAddingToEmptyTreeAndContains() {
    PublicationBinarySearchTreeImpl<Book> tree = new PublicationBinarySearchTreeImpl<Book>();
    tree.add(new Book("foo", "bar", Range.of(2010), pubFreqOf(NA)));

    assertThat(tree.containsPublicationDate(2010), is(true));
  }
}
