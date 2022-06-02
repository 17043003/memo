package com.ishzk.android.memo.Model

import com.google.firebase.firestore.DocumentId
import java.util.*

data class Memo(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
)
