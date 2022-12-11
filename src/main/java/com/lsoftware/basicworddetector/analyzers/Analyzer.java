package com.lsoftware.basicworddetector.analyzers;

public interface Analyzer<T, R> {
    R analyze(T t);
}
