package com.lsoftware.basicworddetector.queues;

import com.lsoftware.basicworddetector.model.Candidate;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class CandidatesQueue {
    private Queue<Candidate> queue = new LinkedList<>();
    private boolean isEmpty = true;
    private boolean isTerminate = false;
    private int capacity;

    public CandidatesQueue(int capacity){
        this.capacity = capacity;
    }

    public synchronized void add(Candidate candidate){
        while (queue.size() == this.capacity){
            try {
                System.out.println(Thread.currentThread().getName() + " Waiting....");
                wait();
            } catch (InterruptedException e){}
        }
        System.out.println("Adding new one.");
        queue.add(candidate);
        isEmpty = false;
        notify();
    }

    public synchronized Optional<Candidate> remove(){
        Candidate candidate;

        while (isEmpty && !isTerminate) {
            try {
                wait();
            } catch (InterruptedException e){}
        }

        if (queue.size() == 1) isEmpty = true;

        if (queue.size() == 0 && isTerminate) return Optional.empty();

        System.out.println("QUEUE SIZE: " + queue.size());

        candidate = queue.remove();
        if (queue.size() == capacity - 1) {
            notifyAll();
        }
        return Optional.of(candidate);
    }

    public synchronized void terminate(){
        isTerminate = true;
        notifyAll();
    }

    public boolean isTerminate(){
        return isTerminate;
    }

}
