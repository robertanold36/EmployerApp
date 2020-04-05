package com.robert.customer_manager.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatModel(var chatID:String="",
                     var senderID:String="",
                     var receiverID:String="",
                     var msg:String="",
                     var time:String) :   Parcelable

