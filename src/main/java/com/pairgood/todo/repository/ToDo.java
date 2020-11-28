package com.pairgood.todo.repository;

import com.pairgood.todo.priority.Prioritizable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ToDo implements Prioritizable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private boolean done;
    private long priority;

    @SuppressWarnings("unused")
    public ToDo() {

    }

    public ToDo(String description, int priority) {
        this.description = description;
        this.priority = priority;
    }

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public void markAsDone() {
        done = true;
    }

    @Override
    public long getPriority() {
        return priority;
    }

    @Override
    public void setPriority(long priority) {
        this.priority = priority;
    }
}
