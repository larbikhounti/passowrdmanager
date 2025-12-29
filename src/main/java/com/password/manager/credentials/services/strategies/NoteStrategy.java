package com.password.manager.credentials.services.strategies;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.contracts.ICredential;
import com.password.manager.credentials.entities.Note;
import com.password.manager.credentials.repositories.NoteRepository;

import java.util.ArrayList;

public class NoteStrategy implements ICredential {
    NoteRepository noteRepository;

    public NoteStrategy(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
    @Override
    public boolean addCredential(Entity credential) {
      return  this.noteRepository.addCredential(credential);
    }

    @Override
    public boolean editCredential(int id, Entity credential) {
        return this.noteRepository.editCredential(id, credential);
    }

    @Override
    public boolean removeCredential(int id) {
        return this.noteRepository.removeCredential(id);
    }

    @Override
    public Entity getCredential(int id) {
        return this.noteRepository.getCredential(id);
    }

    @Override
    public ArrayList<Entity> getAllCredentials() {
        return this.noteRepository.getAllCredentials();
    }
}
