package com.example.ms1.note.notebook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotebookService {
    private final NotebookRepository notebookRepository;

    public Notebook getNotebook(Long notebookId) {
        return notebookRepository.findById(notebookId).orElseThrow();
    }

    public List<Notebook> getNotebookList() {
        return notebookRepository.findAll();
    }

    public Notebook save(Notebook notebook) {
        return notebookRepository.save(notebook);
    }
    public void delete (Notebook notebook) {
        this.notebookRepository.delete(notebook);
    }
    public List<Notebook> getTopNotebookList () {
        return this.notebookRepository.findByParentIsNull();
    }

    public void move (Long notebookId, Long destinationId) {
        Notebook notebook = this.getNotebook(notebookId);
        Notebook parent = this.getNotebook(destinationId);
        notebook.setParent(parent);
        this.save(notebook);
    }
    public void update (Long notebookId, String name) {
        Notebook notebook = this.getNotebook(notebookId);
        notebook.setName(name);

        this.save(notebook);
    }
}
