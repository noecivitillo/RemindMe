package e.test.nicecardview

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.internal.SetFactory.builder

import e.test.nicecardview.di.AppComponent
import e.test.nicecardview.di.AppModule
import e.test.nicecardview.di.DaggerAppComponent
import javax.inject.Inject

/**
 * Created by Noe on 2/3/2018.
 */

class NoteApplication: Application(), HasActivityInjector{



    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(applicationContext)).build()
        appComponent.inject(this)
    }
    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}