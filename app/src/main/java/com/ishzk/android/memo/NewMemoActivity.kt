package com.ishzk.android.memo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.ishzk.android.memo.Model.Memo
import com.ishzk.android.memo.Repository.FireStoreMemoRepository
import com.ishzk.android.memo.Repository.MemoRepository
import java.util.*

class NewMemoActivity : AppCompatActivity() {
    private val memoRepository: MemoRepository = FireStoreMemoRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_memo)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_new_memo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save_button -> {
                val calendar = Calendar.getInstance()
                val (title, content) = getMemoTexts()
                memoRepository.saveMemo(
                    Memo(
                        title = title,
                        content = content,
                        createdAt = calendar,
                        updatedAt = calendar,
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

    data class EditedText(
        val title: String,
        val content: String,
    )
}