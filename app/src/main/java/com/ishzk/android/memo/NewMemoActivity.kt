package com.ishzk.android.memo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

class NewMemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_memo)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_new_memo, menu)
        return super.onCreateOptionsMenu(menu)
    }
}