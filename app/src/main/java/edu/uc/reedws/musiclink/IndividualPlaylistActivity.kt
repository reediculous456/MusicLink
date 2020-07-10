package edu.uc.reedws.musiclink

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*

class IndividualPlaylistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.playlist_song_view)

        /** Share functionality */
        shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "type/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "This is example text.")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Example Subject")
            startActivity(Intent.createChooser(intent, "Share playlist"))
        }
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.search_menu -> {
                    val intent = Intent(this,SearchActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.library_menu -> {
                    val intent = Intent(this,MainActivity::class.java)
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
            val intent = Intent(this,SearchActivity::class.java) // Change this later to include playlist data without making you select a playlist
            startActivity(intent)
        }
    }
}
