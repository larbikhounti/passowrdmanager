package com.password.manager.credentials.entities;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.services.RenderService;

public class Note extends Entity {

    private String title;
    private String note;

    public Note() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public void render(RenderService renderer) {
        renderer.renderNoteCredential(this);
    }

    @Override
    public void renderMany(RenderService renderer) {
        renderer.renderCredentialMany(this.getId(), this.getTitle(), this.getNote(), "[Note]", this);
    }
}
