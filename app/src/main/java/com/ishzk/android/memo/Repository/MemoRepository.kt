package com.ishzk.android.memo.Repository

import com.ishzk.android.memo.Model.Memo

interface MemoRepository {
    fun saveMemo(memo: Memo)
}