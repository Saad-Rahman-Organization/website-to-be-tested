package com.bittercode.service.impl;

import com.bittercode.model.Book;
import com.bittercode.model.StoreException;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;
import java.util.Random;

public class BookServiceImplTest extends TestCase {
@Test
public void testMutationTool() throws StoreException
{
    Book[] books;
    books=new Book[20];
    BookServiceImpl bookService = new BookServiceImpl();
    List<Book> book=bookService.getAllBooks();
    int i;
    i=0;
    for (Book b:book)
    {
        books[i]=b;
        i++;
    }

    Random rand=new Random();
    int choice= rand.nextInt(12);
    Book b=bookService.getBookById(books[choice].getBarcode());
}
}