package com.example.grabtest.di;


import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Abhishek V on 23/11/2017.
 */
@Singleton
@Component(modules = [
        AppModule::class,
        ViewModelModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: MainApplication)

    fun inject(paytmFirebaseInstanceIDService: PaytmFirebaseInstanceIDService)
    fun inject(appVersionDialogBottom: AppVersionDialogBottom)

    fun inject(baseActivity: BaseActivity)
    fun inject(baseFragment: BaseFragment)
    fun inject(baseBottomSheetFragment: BaseBottomSheetFragment)
}
