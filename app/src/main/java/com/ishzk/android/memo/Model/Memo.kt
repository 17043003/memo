package com.ishzk.android.memo.Model

import java.util.*

data class Memo(
    val title: String,
    val content: String,
    val createdAt: Calendar,
    val updatedAt: Calendar,
)
