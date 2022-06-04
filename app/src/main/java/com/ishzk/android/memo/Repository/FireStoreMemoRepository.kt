package com.ishzk.android.memo.Repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ishzk.android.memo.Model.Memo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.coroutines.suspendCoroutine

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

    override fun fetchMemos(): Query {
        val db = Firebase.firestore
        return db.collection("memos")
            .orderBy("updatedAt", Query.Direction.ASCENDING)
            .limit(50)
    }
}