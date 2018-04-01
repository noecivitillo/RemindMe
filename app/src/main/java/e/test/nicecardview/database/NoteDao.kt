package e.test.nicecardview.database

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import e.test.nicecardview.model.Note

/**
 * Created by Noe on 2/3/2018.
 */
@Dao
interface NoteDao {
        @Query("select * from notes ORDER BY inverseDate")
        fun getAllNotes(): List<Note>

        @Query("select * from notes where id = :id")
        fun findNoteById(id: Long): Note

        @Insert(onConflict = REPLACE)
        fun insertNote(note: Note)

        @Update(onConflict = REPLACE)
        fun updateNote(note: Note)

        @Delete
        fun delete(note: Note)

}