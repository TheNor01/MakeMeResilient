package com.parser;

import java.util.concurrent.TimeUnit;

public class myClassParser {

    Integer foo;

    String boo;

    public myClassParser(String booLocal, Integer fooLocal) {
        this.foo = fooLocal;
        this.boo = booLocal;
    }

    public Integer getFoo() {
        return foo;
    }
}
