package e.test.nicecardview.di

import dagger.Component
import e.test.nicecardview.NoteApplication

/**
 * Created by Noe on 2/3/2018.
 */
@Component(modules = arrayOf(AppModule::class,
        NoteModule::class))
interface AppComponent {
    fun inject(application: NoteApplication)
}