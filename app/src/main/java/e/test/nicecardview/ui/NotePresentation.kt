package e.test.nicecardview.ui

import e.test.nicecardview.model.Note

/**
 * Created by Noe on 2/3/2018.
 */
interface NotePresentation {

    fun showNotes(notes : List<Note>)
    fun noteAddedAt(position: Int)
    fun scrollTo(position: Int)
}