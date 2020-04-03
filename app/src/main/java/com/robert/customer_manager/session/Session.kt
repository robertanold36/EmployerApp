package com.robert.customer_manager.session

interface Session {
   fun onStarted()
   fun onSuccess()
   fun onEmpty()
   fun onFail()
   fun onLogout()
}