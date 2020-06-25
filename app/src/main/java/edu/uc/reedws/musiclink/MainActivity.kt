package edu.uc.reedws.musiclink

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.uc.reedws.musiclink.ui.main.MainFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //Mock Data
        val playLists: MutableList<String> = arrayListOf(
            "Rock",
            "Pop",
            "Jazz",
            "Indie",
            "Metal",
            "Classical"
        )
        val listView = findViewById<ListView>(R.id.listOfPlayLists)
        val arrayAdapter = ArrayAdapter(
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
        addPlaylistOrSongButton.setOnClickListener {

        }
        val addPlaylistDialogBtn = findViewById<FloatingActionButton>(R.id.addPlaylistOrSongButton)
        addPlaylistDialogBtn.setOnClickListener {
            showAlertDialog(playLists as ArrayList<String>)
        }
        libraryButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showAlertDialog(arrayList: ArrayList<String>) {

        val addPlaylistDialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        addPlaylistDialogBuilder.setTitle("Enter name of playlist to create")
        val dialogLayout = inflater.inflate(R.layout.add_playlist_dialog, null)
        val editText  = dialogLayout.findViewById<EditText>(R.id.editText)
        addPlaylistDialogBuilder.setView(dialogLayout)
        addPlaylistDialogBuilder.setPositiveButton("Done") { dialogInterface, i -> Toast.makeText(applicationContext, "You added " + editText.text.toString(), Toast.LENGTH_SHORT).show()
        val newPlaylist = editText.text
            arrayList.add(newPlaylist.toString())
        }
        addPlaylistDialogBuilder.setNeutralButton("Cancel") { dialog, id -> dialog.cancel()}

        addPlaylistDialogBuilder.show()
    }
}
