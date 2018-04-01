package e.test.nicecardview.di

import dagger.Subcomponent
import dagger.android.AndroidInjector
import e.test.nicecardview.MainActivity

/**
 * Created by Noe on 2/3/2018.
 */
@Subcomponent
interface NoteSubComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Builder abstract class Builder : AndroidInjector.Builder<MainActivity>()
}