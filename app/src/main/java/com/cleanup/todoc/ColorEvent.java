package com.cleanup.todoc;

import com.cleanup.todoc.model.Task;

public class ColorEvent {

    public Task tag;

    public ColorEvent(Task tag) {
        this.tag = tag;
    }
}
