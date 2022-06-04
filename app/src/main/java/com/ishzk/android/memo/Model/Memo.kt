package com.ishzk.android.memo.Model

import com.google.firebase.firestore.DocumentId
import java.text.SimpleDateFormat
import java.util.*

data class Memo(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
){
    val formattedCreatedDate: String
      get() = "Created : " + SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(this.createdAt)

    val formattedUpdatedDate: String
        get() = "Updated : " + SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(this.updatedAt)
}
