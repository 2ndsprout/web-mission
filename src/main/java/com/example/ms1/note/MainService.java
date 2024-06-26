package com.example.ms1.note;

import com.example.ms1.note.note.Note;
import com.example.ms1.note.note.NoteService;
import com.example.ms1.note.notebook.Notebook;
import com.example.ms1.note.notebook.NotebookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final NotebookService notebookService;
    private final NoteService noteService;

    public MainDataDto getDefaultMainData(String keyword) {
        List<Notebook> notebookList = notebookService.getTopNotebookList();

        if (notebookList.isEmpty()) {
            Notebook notebook = this.saveDefaultNotebook();
            notebookList.add(notebook);
        }
        List<Notebook> searchedNotebookList = this.notebookService.getSearchedNotebookList(keyword);
        List<Note> searchedNoteList = this.noteService.getSearchedNoteList(keyword);

        Notebook targetNotebook = notebookList.get(0);
        List<Note> noteList = targetNotebook.getNoteList();
        Note targetNote = noteList.get(0);

        MainDataDto mainDataDto = new MainDataDto(notebookList, targetNotebook, noteList, targetNote, searchedNotebookList, searchedNoteList);
        return mainDataDto;
    }

    public MainDataDto getMainData(Long notebookId, Long noteId, String keyword,String sort) {

        MainDataDto mainDataDto = this.getDefaultMainData(keyword);
        Notebook targetNotebook = this.getNotebook(notebookId);
        Note targetNote = noteService.getNote(noteId);

        mainDataDto.setTargetNotebook(targetNotebook);
        mainDataDto.setTargetNote(targetNote);

        List<Note> sortedNoteList;

        if (sort.equals("title")) {
            sortedNoteList = this.noteService.getSortedByTitleNoteList(targetNotebook);
        }
        else {
            sortedNoteList = this.noteService.getSortedByCreateDateNoteList(targetNotebook);
        }
        mainDataDto.setNoteList(sortedNoteList);

        return mainDataDto;
    }

    public Notebook getNotebook(Long notebookId) {
        return notebookService.getNotebook(notebookId);
    }

    public List<Notebook> getNotebookList() {
        return notebookService.getNotebookList();
    }

    public Notebook saveDefaultNotebook() {
        Notebook notebook = new Notebook();
        notebook.setName("새노트북");

        Note note = noteService.saveDefault();
        notebook.addNote(note);

        return notebookService.save(notebook);
    }

    public void saveGroupNotebook(Long notebookId) {
        Notebook parent = this.getNotebook(notebookId);
        Notebook child = this.saveDefaultNotebook();
        parent.addChild(child);

        notebookService.save(parent);
    }

    public Notebook addToNotebook(Long notebookId) {
        Notebook notebook = this.getNotebook(notebookId);
        Note note = noteService.saveDefault();
        notebook.addNote(note);

        return notebookService.save(notebook);
    }
}
