package e.test.nicecardview.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import e.test.nicecardview.model.Note

/**
 * Created by Noe on 2/3/2018.
 */
@Database(entities = arrayOf(Note :: class), version = 1, exportSchema = false)
        abstract class AppDatabase: RoomDatabase(){
        abstract fun noteDao() : NoteDao

}