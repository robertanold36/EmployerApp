package com.robert.customer_manager

import android.app.Application
import com.robert.customer_manager.database.FirebaseSource
import com.robert.customer_manager.ui.registration.RegisterViewModelFactory
import com.robert.customer_manager.ui.login.UserViewModelFactory
import com.robert.customer_manager.viewModel.GetAllEmployerViewFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class BaseApplication1 : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@BaseApplication1))

        bind() from singleton { FirebaseSource() }
        bind() from provider {
            UserViewModelFactory(instance())
        }
        bind() from provider {
            RegisterViewModelFactory(
                instance()
            )
        }

        bind() from provider {
            GetAllEmployerViewFactory(instance())
        }
    }

}
