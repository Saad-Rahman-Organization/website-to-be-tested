package com.bittercode.service.impl;

import com.bittercode.model.StoreException;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class UserServiceImplPerfTest {
    @Benchmark
    @Fork(2)
    public void testregisterUser() throws StoreException
    {
        BookServiceImpl bookService = new BookServiceImpl();
    }
}
