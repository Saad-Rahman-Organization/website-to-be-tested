package com.bittercode.service.impl;

import com.bittercode.model.Book;
import com.bittercode.model.StoreException;
import org.junit.Before;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class BookServiceImplPerfTest {

    Book[] books;
    @Setup(Level.Invocation)
    public void setup()throws StoreException
    {
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
    }

    @Benchmark
    @Fork(2)
    public void testgetBookById() throws StoreException
    {
        BookServiceImpl bookService = new BookServiceImpl();
        Random rand=new Random();
        int choice= rand.nextInt(12);
        Book b=bookService.getBookById(books[choice].getBarcode());

    }


    @Benchmark
    @Fork(2)
    public void testdeleteBook() throws StoreException
    {
        BookServiceImpl bookService = new BookServiceImpl();
    }

    @Benchmark
    @Fork(2)
    public void testaddBook() throws StoreException
    {
        BookServiceImpl bookService = new BookServiceImpl();
    }

    @Benchmark
    @Fork(2)
    public void testupdateBookQuantity() throws StoreException
    {
        BookServiceImpl bookService = new BookServiceImpl();
    }

    @Benchmark
    @Fork(2)
    public void testgetBooksSeparatedByComma() throws StoreException
    {
        BookServiceImpl bookService = new BookServiceImpl();
    }

    @Benchmark
    @Fork(2)
    public void testupdateBook() throws StoreException
    {
        BookServiceImpl bookService = new BookServiceImpl();
    }
}
