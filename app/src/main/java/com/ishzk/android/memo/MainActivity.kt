package com.ishzk.android.memo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ishzk.android.memo.Model.Memo
import com.ishzk.android.memo.Repository.FetchException
import com.ishzk.android.memo.Repository.MemoRepository
import com.ishzk.android.memo.di.FireStoreMemo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @FireStoreMemo
    @Inject
    lateinit var memoRepository: MemoRepository

    private lateinit var adapter: MemoRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab: FloatingActionButton = findViewById(R.id.fabNewMemoButton)
        fab.setOnClickListener{
            val intent = Intent(this, NewMemoActivity::class.java)
            startActivity(intent)
        }

        val query = memoRepository.fetchMemos()
        val options = FirestoreRecyclerOptions.Builder<Memo>()
            .setQuery(query, Memo::class.java)
            .build()

        adapter = MemoRecyclerAdapter(options)

        val memoMenu: RecyclerView = findViewById(R.id.memoListView)
        setRecyclerView(memoMenu)
    }

    // set some params to recyclerview to restart recyclerview after activity restart.
    private fun setRecyclerView(memoMenu: RecyclerView){
        memoMenu.let {
            it.layoutManager = LinearLayoutManager(this@MainActivity)
            it.adapter = adapter

            // show decorator line at RecyclerView items.
            val decorator = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            it.addItemDecoration(decorator)
        }
    }

    override fun onStart() {
        super.onStart()

        val memoMenu: RecyclerView = findViewById(R.id.memoListView)
        setRecyclerView(memoMenu)

        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private inner class MemoRecyclerAdapter(options: FirestoreRecyclerOptions<Memo>): FirestoreRecyclerAdapter<Memo, MemoHolder>(options){
        override fun onBindViewHolder(holder: MemoHolder, position: Int, model: Memo) {
            holder.setMemoMenu(model)

            // set click listener to each items.
            holder.itemView.setOnClickListener(ItemClickListener(model))

            // set long click listener to delete item.
            holder.itemView.setOnLongClickListener(ItemLongClickListener(model))
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoHolder {
            val layout = layoutInflater.inflate(R.layout.memo_row, parent, false)
            return MemoHolder(layout)
        }
    }

    private inner class MemoHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun setMemoMenu(memo: Memo){
            val titleRow: TextView = itemView.findViewById(R.id.memoTitleText)
            titleRow.text = memo.title
            val contentRow: TextView = itemView.findViewById(R.id.contentSummaryText)
            contentRow.text = memo.content
            val createdTimeRow: TextView = itemView.findViewById(R.id.createdAtText)
            createdTimeRow.text =  memo.formattedCreatedDate
            val updatedTimeRow: TextView = itemView.findViewById(R.id.updatedAtText)
            updatedTimeRow.text =  memo.formattedUpdatedDate
        }
    }

    private inner class ItemClickListener(private val data: Memo): View.OnClickListener {
        override fun onClick(view: View) {
            try{
                val memo = memoRepository.fetchMemo(data.id)

                // gives extras and shows NewMemoActivity
                val intent = Intent(this@MainActivity, NewMemoActivity::class.java)
                intent.putExtra("id", memo.id)
                intent.putExtra("title", memo.title)
                intent.putExtra("content", memo.content)
                startActivity(intent)

            }catch (e: FetchException){
                Toast.makeText(this@MainActivity, "Error: $e", Toast.LENGTH_LONG).show()
            }
        }
    }

    private inner class ItemLongClickListener(private val data: Memo): View.OnLongClickListener {
        override fun onLongClick(view: View): Boolean {
            AlertDialog.Builder(this@MainActivity)
                .setTitle(R.string.delete_dialog_title)
                .setMessage(R.string.delete_dialog_message)
                .setPositiveButton("OK"){ dialog, _ ->
                    memoRepository.deleteMemo(data.id)
                    dialog.cancel()
                    Toast.makeText(this@MainActivity, "Memo is deleted.", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("NO"){ dialog, _ ->
                    dialog.cancel()
                }
                .show()

            return true
        }
    }
}