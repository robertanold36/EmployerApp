package com.robert.customer_manager.model

import androidx.annotation.Keep

@Keep
data class ChatModel(
                      var senderID:String="",
                      var receiverID:String="",
                      var msg:String="",
                      var time:String=""){



}

