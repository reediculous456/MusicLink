package edu.uc.reedws.musiclink

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.main_activity.bottomNav
import kotlinx.android.synthetic.main.profile_view.*

class ProfileActivity : AppCompatActivity() {

    private const val CAMERA_PERMISSION_REQUEST_CODE = 2000
    private const val CAMERA_REQUEST_CODE = 2001

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
        /** Opens the camera to take a picture for the profile photo*/
        val photoBtn = findViewById<FloatingActionButton>(R.id.btnCamera)
        photoBtn.setOnClickListener {
            prepTakePhoto()
        }
    }

    /** Check for permissions to use camera*/
    private fun prepTakePhoto() {
        if (ContextCompat.checkSelfPermission(
                applicationContext!!,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            takePhoto()
        } else {
            val permissionRequest = arrayOf(Manifest.permission.CAMERA)
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Unable to take photo without permission",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(applicationContext!!.packageManager)?.also {
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
            }
        }
    }

    /** Sets the image view to the picture the camera took*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE) {
            val photoTaken = data!!.extras!!.get("data") as Bitmap
            avatarPhotoView.setImageBitmap(photoTaken)
        } else {
            Toast.makeText(applicationContext, "Unable to set photo", Toast.LENGTH_LONG).show()
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
