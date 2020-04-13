package com.robert.customer_manager.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.robert.customer_manager.database.FirebaseSource
import com.robert.customer_manager.session.Session
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class UserViewModel (application:Application):AndroidViewModel(application) {

    private val firebaseSource: FirebaseSource by lazy {
        FirebaseSource()
    }

  var email:String?=""
  var password:String?=""

    lateinit var listener:Session

    private val disposables=CompositeDisposable()

    val user by lazy {
        firebaseSource.currentUser()
    }



    fun login() {
            if (email!!.isEmpty() || password!!.isEmpty()) {
                listener.onEmpty()
            }
            listener.onStarted()

            val disposable = firebaseSource.login(email!!, password!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listener.onSuccess()
                }, {
                    listener.onFail()
                })

            disposables.add(disposable)
        }

    fun logout(){
        firebaseSource.signOut()
        listener.onLogout()
    }

}