package e.test.nicecardview.ui

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Update
import android.util.Log
import e.test.nicecardview.R
import e.test.nicecardview.database.NoteDao
import e.test.nicecardview.model.Note
import java.util.*
import javax.inject.Inject

/**
 * Created by Noe on 2/3/2018.
 */

    class NotePresenter @Inject constructor(val noteDao: NoteDao){


        var notes = ArrayList<Note>()
        var presentation: NotePresentation? = null


        fun onCreate(notePresentation: NotePresentation) {
            presentation = notePresentation
            loadNotes()
        }

        fun onDestroy() {
            presentation = null
        }

        fun loadNotes() {
            notes.clear()
            notes.addAll(noteDao.getAllNotes())
            presentation?.showNotes(notes)
        }

        fun getNoteById(id: Long) : Note{

           return noteDao.findNoteById(id)
        }

        fun addNewNote(color: Int) {
            val newNote = Note(color = color)
            notes.add(0, newNote)
            noteDao.insertNote(newNote)
            presentation?.noteAddedAt(0)
            presentation?.scrollTo(0)

        /**
            (notes.size - 1).let {
                presentation?.noteAddedAt(it)
                presentation?.scrollTo(it)
            }
            */
        Log.i("NotePresenter" , "Note added ID = " + newNote.id + " CONTENT " +newNote.content)
        }
        fun delete(note: Note){
            noteDao.delete(note = note)
            notes.remove(note);
            presentation?.showNotes(notes)
        }
        fun update(note: Note){
            noteDao.updateNote(note = note)
            presentation?.showNotes(notes)
        }

    }

