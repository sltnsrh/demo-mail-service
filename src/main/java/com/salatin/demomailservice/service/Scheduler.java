package com.salatin.demomailservice.service;

public interface Scheduler {

    void schedule(Integer cronId);

    void stop();
}
