package edu.uc.reedws.musiclink

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.main_activity.*

class IndividualPlaylistActivity : AppCompatActivity() {
    private lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.playlistsongview)

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        // Opens the Main or Playlist Library Screen
        libraryButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        // Opens Search Screen
        searchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        // Opens Search Screen and will need to associate with the playlist to avoid having to select a playlist
        addPlaylistOrSongButton.setOnClickListener {
            val intent = Intent(
                this,
                SearchActivity::class.java
            ) // Change this later to include playlist data without making you select a playlist
            startActivity(intent)
        }
    }
}