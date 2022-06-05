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
import java.lang.Exception
import java.util.*
import kotlin.coroutines.suspendCoroutine

private const val TAG = "FireStoreMemoRepository"

class FireStoreMemoRepository: MemoRepository {
    private val db by lazy { Firebase.firestore }

    override fun saveMemo(memo: Memo) {
        if(memo.id.isEmpty()){
            createMemo(memo)
        }else{
            updateMemo(memo)
        }
    }

    private fun createMemo(memo: Memo){
        db.collection("memos")
            .add(memo)
            .addOnSuccessListener {
                Log.d(TAG, "Generated ID:${it.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding memo.", e)
            }
    }

    private fun updateMemo(memo: Memo) {
        if(memo.id.isEmpty()) return

        db.collection("memos")
            .document(memo.id)
            .update(mapOf("title" to memo.title, "content" to memo.content, "updatedAt" to Date()))
    }

    override fun fetchMemos(): Query {
        return db.collection("memos")
            .orderBy("updatedAt", Query.Direction.ASCENDING)
            .limit(50)
    }

    override fun fetchMemo(id: String): Memo {
        val task = db.collection("memos")
            .document(id)
            .get()
            .addOnSuccessListener{
                it.toObject(Memo::class.java)
            }
            .addOnFailureListener {
                Log.w(TAG, "Error getting memo.", it)
            }

        while(!task.isComplete){

        }
        return task.result.toObject(Memo::class.java) ?: throw FetchException("fetch error from firestore: ${task.result.id}")
    }

    override fun deleteMemo(id: String) {
        db.collection("memos")
            .document(id)
            .delete()
    }
}

class FetchException(message: String): Exception(message)