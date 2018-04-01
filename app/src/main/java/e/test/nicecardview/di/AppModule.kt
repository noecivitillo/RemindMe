package e.test.nicecardview.di

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import e.test.nicecardview.database.AppDatabase
import e.test.nicecardview.ui.NotePresenter

/**
 * Created by Noe on 2/3/2018.
 */

@Module class AppModule(private val context: Context){
    @Provides
    fun providesAppContext()= context

    @Provides
    fun providesAppDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase:: class.java, "notes-db").allowMainThreadQueries().build()
    @Provides
    fun providesNoteDao(database: AppDatabase)= database.noteDao()

}