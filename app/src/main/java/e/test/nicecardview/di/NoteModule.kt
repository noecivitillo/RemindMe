package e.test.nicecardview.di

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import e.test.nicecardview.MainActivity

/**
 * Created by Noe on 2/3/2018.
 */
@Module(subcomponents = arrayOf(NoteSubComponent::class))
abstract class NoteModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    internal abstract fun bindsToDoActivityInjectorFactory(builder: NoteSubComponent.Builder): AndroidInjector.Factory<out Activity>
}