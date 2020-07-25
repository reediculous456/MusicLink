package edu.uc.reedws.musiclink

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

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
                    true
                }
                R.id.settings_menu -> {
                    true
                }
                else -> false
            }
        }
    }
}