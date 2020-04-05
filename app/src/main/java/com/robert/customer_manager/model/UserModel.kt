package com.robert.customer_manager.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserModel(var uid:String="",
                     var email:String="",
                     var fullName:String="",
                     var job:String="",
                     var department:String="",
                     var imageUrl:String="",
                     var endYear:String="") : Parcelable




