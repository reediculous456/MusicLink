package edu.uc.reedws.musiclink

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.main_activity.*

class IndividualPlaylistActivity : AppCompatActivity() {
    private lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.playlist_song_view)
        val playListsLabelText = findViewById<TextView>(R.id.singlePlayListsLabel)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        /** Changes title of the screen to clicked playlist name*/
        playListsLabelText.text = intent.extras?.getString("playlist")

        /** Share functionality */
        shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "type/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "This is example text.")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Example Subject")
            startActivity(Intent.createChooser(intent, "Share playlist"))
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
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.profile_menu -> {
                    true
                }
                R.id.settings_menu -> {
                    true
                }
                else -> false
            }
        }
        /** Opens Search Screen and will need to associate with the playlist to avoid having to select a playlist */
        addPlaylistOrSongButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }
}
