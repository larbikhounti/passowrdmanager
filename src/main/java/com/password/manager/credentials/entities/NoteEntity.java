package com.password.manager.credentials.entities;

import com.password.manager.credentials.base.Entity;

public class NoteEntity extends Entity {
    protected String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
