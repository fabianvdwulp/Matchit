package com.example.fabian.matchit;

import java.util.EventObject;

/**
 * Created by fvdwulp on 31-10-2014.
 */
public class ReadyEvent extends EventObject {
    public ReadyEvent(Object source) {
        super(source);
    }
}

// Roughly analogous to .NET delegate
interface ReadyListener {
    void ready(ReadyEvent e);
}