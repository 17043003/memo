package com.ishzk.android.memo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.ishzk.android.memo.Model.Memo
import com.ishzk.android.memo.Repository.MemoRepository
import com.ishzk.android.memo.di.FireStoreMemo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

private const val TAG = "NewMemoActivity"

@AndroidEntryPoint
class NewMemoActivity : AppCompatActivity() {
    @FireStoreMemo
    @Inject
    lateinit var memoRepository: MemoRepository

    var memoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_memo)

        memoId = intent.extras?.get("id") as String?
        if(!memoId.isNullOrEmpty()){
            val title = intent.extras?.get("title") as String? ?: ""
            val content = intent.extras?.get("content") as String? ?: ""
            setMemoTexts(title = title, content = content)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_new_memo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save_button -> {
                val (title, content) = getMemoTexts()

                memoRepository.saveMemo(
                    Memo(
                        id = memoId ?: "",
                        title = title,
                        content = content,
                    )
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getMemoTexts(): EditedText{
        val titleEditText: EditText = findViewById(R.id.editTextTitle)
        val contentEditText: EditText = findViewById(R.id.editTextMemo)

        return EditedText(titleEditText.text.toString(), contentEditText.text.toString())
    }

    private fun setMemoTexts(title: String, content: String){
        val titleEditText: EditText = findViewById(R.id.editTextTitle)
        titleEditText.setText(title)

        val contentEditText: EditText = findViewById(R.id.editTextMemo)
        contentEditText.setText(content)
    }

    data class EditedText(
        val title: String,
        val content: String,
    )
}