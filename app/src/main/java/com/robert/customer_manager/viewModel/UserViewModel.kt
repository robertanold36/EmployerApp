package com.robert. customer_manager.viewModel

import androidx.lifecycle.ViewModel
import com.robert.customer_manager.repository.UserRepository
import com.robert.customer_manager.session.Session
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class UserViewModel(private val userRepository: UserRepository):ViewModel() {
  var email:String?=""
  var password:String?=""

    lateinit var listener:Session

    private val disposables=CompositeDisposable()

    val user by lazy {
        userRepository.currentUser()
    }


    fun login(){
        if(email!!.isEmpty()  || password!!.isEmpty()){
            listener.onEmpty()
        }
        listener.onStarted()

        val disposable=userRepository.login(email!!,password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                listener.onSuccess()
            },{
                listener.onFail()
            })

        disposables.add(disposable)
    }
}