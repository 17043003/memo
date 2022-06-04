package com.ishzk.android.memo.Repository

import com.google.firebase.firestore.Query
import com.ishzk.android.memo.Model.Memo

interface MemoRepository {
    fun saveMemo(memo: Memo)
    fun fetchMemos(): Query
}