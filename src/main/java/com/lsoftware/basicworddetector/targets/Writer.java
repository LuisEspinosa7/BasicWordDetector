package com.lsoftware.basicworddetector.targets;

import java.io.IOException;

public interface Writer<T> {
    void write(T t) throws IOException;
    void close() throws IOException;
}
