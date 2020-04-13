package com.robert.customer_manager.ui.registration


import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.robert.customer_manager.database.FirebaseSource
import com.robert.customer_manager.session.Session
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application):AndroidViewModel(application) {

    private val firebaseSource: FirebaseSource by lazy {
        FirebaseSource()
    }

    var session:Session?=null
    private val disposables=CompositeDisposable()


    fun register(email:String, fullName:String,job:String,department:String,imageUri: Uri,endYear:String) {
          val disposable=firebaseSource.signUp(email,fullName,job,department,imageUri,endYear)
              .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
              .subscribe({
                  session?.onSuccess()
              },{
                  session?.onFail()
              })
        disposables.add(disposable)
    }


}