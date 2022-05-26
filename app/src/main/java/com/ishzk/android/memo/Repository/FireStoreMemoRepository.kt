package com.ishzk.android.memo.Repository

import java.util.Calendar
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ishzk.android.memo.Model.Memo

private const val TAG = "FireStoreMemoRepository"

class FireStoreMemoRepository: MemoRepository {
    override fun saveMemo(memo: Memo) {
        val db = Firebase.firestore
        db.collection("memos")
            .add(memo)
            .addOnSuccessListener {
                Log.d(TAG, "Generated ID:${it.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding memo.", e)
            }
    }
}