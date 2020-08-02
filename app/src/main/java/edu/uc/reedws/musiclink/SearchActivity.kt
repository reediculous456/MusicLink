package edu.uc.reedws.musiclink

import android.os.Bundle
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.uc.reedws.musiclink.ui.main.ApplicationViewModel
import kotlinx.android.synthetic.main.search_view.*

class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: ApplicationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_view)

        viewModel = ViewModelProvider(this).get(ApplicationViewModel::class.java)
        viewModel.songSearch.observe(this, Observer {
            songs -> searchTextView.setAdapter(ArrayAdapter(applicationContext!!, R.layout.support_simple_spinner_dropdown_item, songs))
        })

        viewModel.searchSongsByName("test")
    }


}