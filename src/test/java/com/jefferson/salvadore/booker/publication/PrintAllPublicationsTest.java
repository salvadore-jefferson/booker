package com.jefferson.salvadore.booker.publication;

import static com.jefferson.salvadore.booker.publication.PubFreq.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.jefferson.salvadore.booker.deprecated.PublicationBinarySearchTreeImpl;
import com.jefferson.salvadore.booker.deprecated.PublicationBinaryTreeNode;

import junit.framework.TestCase;

public class PrintAllPublicationsTest extends TestCase {

  @Test
  public void testPrintAllPublicationsWithOnePublication() {
    final Book book = new Book("foo", "bar", Range.of(1999), pubFreqOf(NA));
    final PublicationBinaryTreeNode<Book> node = new PublicationBinaryTreeNode<Book>(book);

    assertThat(node.getValue(), is(sameInstance(book)));
    assertThat(node.getLeftChild(), is(nullValue()));
    assertThat(node.getRightChild(), is(nullValue()));
  }

  @Test
  public void testPrintAllPublicationsWithOneLeftChild() {
    final Book book = new Book("foo", "bar", Range.of(1999), pubFreqOf(NA));
    final Book book2 = new Book("foo2", "bar2", Range.of(1997), pubFreqOf(NA));

    final PublicationBinaryTreeNode<Book> node = new PublicationBinaryTreeNode<Book>(book);

    PublicationBinarySearchTreeImpl.add(node, book2);
    assertThat(node.getValue(), is(sameInstance(book)));
    assertThat(node.getLeftChild().getValue(), is(sameInstance(book2)));
    assertThat(node.getRightChild(), is(nullValue()));
  }

  @Test
  public void testPrintAllPublicationsWithThreeLeftChildren() {
    final Book book = new Book("foo", "bar", Range.of(1999), pubFreqOf(NA));
    final Book book2 = new Book("foo2", "bar2", Range.of(1997), pubFreqOf(NA));
    final Book book3 = new Book("foo3", "bar3", Range.of(1990), pubFreqOf(NA));
    final Book book4 = new Book("foo4", "bar4", Range.of(1980), pubFreqOf(NA));

    final PublicationBinaryTreeNode<Book> node = new PublicationBinaryTreeNode<Book>(book);

    PublicationBinarySearchTreeImpl.add(node, book2);
    assertThat(node.getValue(), is(sameInstance(book)));
    assertThat(node.getLeftChild().getValue(), is(sameInstance(book2)));
    assertThat(node.getRightChild(), is(nullValue()));

    PublicationBinarySearchTreeImpl.add(node, book3);
    assertThat(node.getValue(), is(sameInstance(book)));
    assertThat(node.getLeftChild().getValue(), is(sameInstance(book2)));
    assertThat(node.getLeftChild().getLeftChild().getValue(), is(sameInstance(book3)));
    assertThat(node.getRightChild(), is(nullValue()));

    PublicationBinarySearchTreeImpl.add(node, book4);
  }

  @Test
  public void testPrintAllPublicationsWithOneRightChild() {
    final Book book = new Book("foo", "bar", Range.of(1999), pubFreqOf(NA));
    final Book book2 = new Book("foo2", "bar2", Range.of(2000), pubFreqOf(NA));

    final PublicationBinaryTreeNode<Book> node = new PublicationBinaryTreeNode<Book>(book);

    PublicationBinarySearchTreeImpl.add(node, book2);
    assertThat(node.getValue(), is(sameInstance(book)));
    assertThat(node.getLeftChild(), is(nullValue()));
    assertThat(node.getRightChild().getValue(), is(sameInstance(book2)));
  }

  @Test
  public void testPrintAllPublicationsWithThreeRightChildren() {
    final Book book = new Book("foo", "bar", Range.of(1990), pubFreqOf(NA));
    final Book book2 = new Book("foo2", "bar2", Range.of(1995), pubFreqOf(NA));
    final Book book3 = new Book("foo3", "bar3", Range.of(1996), pubFreqOf(NA));
    final Book book4 = new Book("foo4", "bar4", Range.of(1999), pubFreqOf(NA));

    final PublicationBinaryTreeNode<Book> node = new PublicationBinaryTreeNode<Book>(book);

    PublicationBinarySearchTreeImpl.add(node, book2);
    assertThat(node.getValue(), is(sameInstance(book)));
    assertThat(node.getLeftChild(), is(nullValue()));
    assertThat(node.getRightChild().getValue(), is(sameInstance(book2)));

    PublicationBinarySearchTreeImpl.add(node, book3);
    assertThat(node.getValue(), is(sameInstance(book)));
    assertThat(node.getLeftChild(), is(nullValue()));
    assertThat(node.getRightChild().getRightChild().getValue(), is(sameInstance(book3)));

    PublicationBinarySearchTreeImpl.add(node, book4);
  }

  @Test
  public void testPublicPrintAllMethodWithNoArgsAndEmptyTree() {
    PublicationBinarySearchTreeImpl<Book> tree = new PublicationBinarySearchTreeImpl<Book>();
    tree.add(new Book("foo", "bar", Range.of(1490), pubFreqOf(NA)));
    tree.printAll();
  }
}


