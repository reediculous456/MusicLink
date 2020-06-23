package edu.uc.reedws.musiclink

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import edu.uc.reedws.musiclink.ui.main.MainFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        title = "Playlist Library"
        val arrayAdapter: ArrayAdapter<*>
        val playLists = arrayOf(
            "Rock",
            "Pop",
            "Jazz",
            "Indie",
            "Metal",
            "Classical"
        )
        val listView = findViewById<ListView>(R.id.listOfPlayLists)
        arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, playLists
        )
        listView.adapter = arrayAdapter

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.mainScreen, MainFragment.newInstance())
                    .commitNow()
        }
        searchButton.setOnClickListener {
            val intent = Intent(this,SearchActivity::class.java)
            startActivity(intent)
        }
//        libraryButton.setOnClickListener {
//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//        }
    }
}
