package edu.uc.reedws.musiclink

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_activity.bottomNav
import kotlinx.android.synthetic.main.profile_view.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_view)
        val profileNameText = findViewById<TextView>(R.id.txtPersonName)
        val emailAddressText = findViewById<TextView>(R.id.txtEmailAddress)

        /** Changes the text views to previously set text*/
        profileNameText.text = intent.extras?.getString("profileName")
        emailAddressText.text = intent.extras?.getString("emailAddress")

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
        /** Opens Dialog Screen to change profile name*/
        val changeNameBtn = findViewById<Button>(R.id.btnChangeName)
        changeNameBtn.setOnClickListener {
            showChangeNameDialog()
        }
        /** Opens Dialog Screen to change email address */
        val changeEmailBtn = findViewById<Button>(R.id.btnChangeEmail)
        changeEmailBtn.setOnClickListener {
            showChangeEmailDialog()
        }
    }

    /** Show Dialog Screen for changing the profile name */
    private fun showChangeNameDialog() {

        val changeNameDialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        changeNameDialogBuilder.setTitle("Enter the new profile name")
        val dialogLayout = inflater.inflate(R.layout.change_name_dialog, null)
        val newName = dialogLayout.findViewById<TextView>(R.id.newProfileName)

        changeNameDialogBuilder.setView(dialogLayout)
        changeNameDialogBuilder.setPositiveButton("Done") { _, _ ->
            txtPersonName.text = newName.text
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("profileName", txtPersonName.text)
        }
        changeNameDialogBuilder.setNeutralButton("Cancel") { dialog, _ -> dialog.cancel() }
        changeNameDialogBuilder.show()
    }

    /** Show Dialog Screen for changing the email address */
    private fun showChangeEmailDialog() {

        val changeEmailDialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        changeEmailDialogBuilder.setTitle("Enter the new email address")
        val dialogLayout = inflater.inflate(R.layout.change_email_dialog, null)
        val newEmail = dialogLayout.findViewById<EditText>(R.id.newEmailAddress)

        changeEmailDialogBuilder.setView(dialogLayout)
        changeEmailDialogBuilder.setPositiveButton("Done") { _, _ ->
            txtEmailAddress.text = newEmail.text
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("emailAddress", txtEmailAddress.text)
        }
        changeEmailDialogBuilder.setNeutralButton("Cancel") { dialog, _ -> dialog.cancel() }
        changeEmailDialogBuilder.show()
    }
}