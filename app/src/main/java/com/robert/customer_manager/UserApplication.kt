package com.robert.customer_manager

import android.app.Application
import com.robert.customer_manager.database.FirebaseSource
import com.robert.customer_manager.repository.UserRepository
import com.robert.customer_manager.viewModel.UserViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class UserApplication:Application(),KodeinAware {

    override val kodein: Kodein= Kodein.lazy {
        import(androidXModule(this@UserApplication))

        bind() from singleton { FirebaseSource() }
        bind() from singleton { UserRepository(instance()) }
        bind() from provider { UserViewModelFactory(instance()) }
    }

}