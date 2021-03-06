package edu.uc.reedws.musiclink

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.uc.reedws.musiclink.ui.main.ApplicationViewModel
import edu.uc.reedws.musiclink.ui.main.MainFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ApplicationViewModel
    private lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewModel = ViewModelProvider(this).get(ApplicationViewModel::class.java)

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val listView = findViewById<ListView>(R.id.listOfPlaylists)

        viewModel.playlists.observe(this, Observer {playlists ->
            listView.adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1, playlists
            )
            listView.setOnItemClickListener { _: AdapterView<*>, _: View, i: Int, _: Long ->
                val intent = Intent(this, IndividualPlaylistActivity::class.java)
                intent.putExtra("playlist", listView.adapter.getItem(i).toString())
                startActivity(intent)
            }
        })

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainScreen, MainFragment.newInstance())
                .commitNow()
        }

        /** Navigation Bar for bottom of screen*/
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.search_menu -> {
                    val intent = Intent(this, SearchActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.library_menu -> {
                    true
                }
                R.id.profile_menu -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.settings_menu -> {
                    true
                }
                else -> false
            }
        }

        /** Share functionality */
        shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "type/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "This is example text.")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Example Subject")
            startActivity(Intent.createChooser(intent, "Share playlist"))
        }
        /** Opens Dialog Screen to add a playlist */
        val addPlaylistDialogBtn = findViewById<FloatingActionButton>(R.id.addPlaylistOrSongButton)
        addPlaylistDialogBtn.setOnClickListener {
            showAddPlaylistDialog(viewModel)
        }
    }

    /** Show Dialog Screen for adding a new playlist */
    private fun showAddPlaylistDialog(viewModel: ApplicationViewModel) {

        val addPlaylistDialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        addPlaylistDialogBuilder.setTitle("Enter name of playlist to create")
        val dialogLayout = inflater.inflate(R.layout.add_playlist_dialog, null)
        val newPlaylistName = dialogLayout.findViewById<EditText>(R.id.newPlaylistName)

        addPlaylistDialogBuilder.setView(dialogLayout)
        addPlaylistDialogBuilder.setPositiveButton("Done") { _, _ ->
            Toast.makeText(
                applicationContext,
                "You added " + newPlaylistName.text.toString(),
                Toast.LENGTH_SHORT
            ).show()
            viewModel.createPlaylist(newPlaylistName.text.toString())
        }
        addPlaylistDialogBuilder.setNeutralButton("Cancel") { dialog, _ -> dialog.cancel() }
        addPlaylistDialogBuilder.show()
    }
}
