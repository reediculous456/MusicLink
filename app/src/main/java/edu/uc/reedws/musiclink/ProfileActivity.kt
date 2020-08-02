package edu.uc.reedws.musiclink

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.content.ContentValues.TAG
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import edu.uc.reedws.musiclink.ui.main.ApplicationViewModel
import kotlinx.android.synthetic.main.main_activity.bottomNav
import kotlinx.android.synthetic.main.profile_view.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception

class ProfileActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION_REQUEST_CODE = 2000
    private val CAMERA_REQUEST_CODE = 2001
    private val SAVE_IMAGE_REQUEST_CODE = 2002
    private val AUTH_REQUEST_CODE = 2003

    private lateinit var currentPhotoPath: String
    private lateinit var viewModel: ApplicationViewModel
    private var user: FirebaseUser? = null
    private var profilePhotoLocalURI: Uri? = null
    private var profilePhotoRemoteURI: Uri? = null

    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ApplicationViewModel::class.java)

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
        /** Opens the login screen*/
        val loginBtn = findViewById<Button>(R.id.loginButton)
        loginBtn.setOnClickListener {
            login()
        }
        /** Opens the logout screen*/
        val logoutBtn = findViewById<Button>(R.id.logoutButton)
        logoutBtn.setOnClickListener {
            logout()
        }

        onLogin()
    }

    private fun logout() {
        if(user == null) {
            Toast.makeText(applicationContext, "You are not logged in", Toast.LENGTH_LONG).show()
        } else {
            AuthUI.getInstance().signOut(applicationContext).addOnCompleteListener {
                Toast.makeText(applicationContext, "Successfully logged out", Toast.LENGTH_LONG).show()
                user = null
            }
        }
    }

    private fun login() {
        if(user == null) {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
            )
            startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(), AUTH_REQUEST_CODE
            )
        } else {
            Toast.makeText(applicationContext, "Already logged in", Toast.LENGTH_LONG).show()
        }
    }

    private fun onLogin() {
        user = FirebaseAuth.getInstance().currentUser
        if(user != null) {
            profilePhotoRemoteURI = user!!.photoUrl
            if(profilePhotoRemoteURI != null) {
                updateAvatar(profilePhotoRemoteURI.toString())
            }
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
        if(user == null) {
            Toast.makeText(applicationContext, "You need to be logged in", Toast.LENGTH_LONG).show()
            return
        }
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(applicationContext!!.packageManager)
            val photoFile: File = createImageFile()
            photoFile.also {
                profilePhotoLocalURI = FileProvider.getUriForFile(applicationContext, "edu.uc.reedws.musiclink.fileprovider", it)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile)
                startActivityForResult(takePictureIntent, SAVE_IMAGE_REQUEST_CODE)
            }
        }
    }

    /** Sets the image view to the picture the camera took*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                val photoTaken = data!!.extras!!.get("data") as Bitmap
                avatarPhotoView.setImageBitmap(photoTaken)
            }
            SAVE_IMAGE_REQUEST_CODE -> {
                Toast.makeText(applicationContext, "New photo - $profilePhotoLocalURI", Toast.LENGTH_LONG).show()
                onProfilePhotoUpdate()
            }
            AUTH_REQUEST_CODE -> {
                onLogin()
            }
            else -> {
                Toast.makeText(applicationContext, "Unable to set photo", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onProfilePhotoUpdate() {
         scope.launch {
             val downloadUri = viewModel.uploadProfilePhoto(profilePhotoLocalURI!!, user!!)
             updateAvatar(downloadUri)
         }
    }

    private fun updateAvatar(downloadUri: String) {
        Picasso.get().load(downloadUri).into(avatarPhotoView, object: Callback {
            override fun onSuccess() {
                profilePhotoRemoteURI = Uri.parse(downloadUri)
            }
            override fun onError(e: Exception?) {
                Log.e(TAG, e?.message);
                Toast.makeText(applicationContext, "Could not load profile photo", Toast.LENGTH_LONG).show()
            }
        })
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

    private fun createImageFile(): File {
        val storageDir: File? = applicationContext!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("profilePhoto", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }
}
