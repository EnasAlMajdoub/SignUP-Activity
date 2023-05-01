package com.example.signup_activity

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.signup_activity.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri : Uri
    private lateinit var dialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.logout.setOnClickListener {

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()

        }


        auth = FirebaseAuth.getInstance()

        val uid = auth.currentUser?.uid

        databaseReference = FirebaseDatabase.getInstance().getReference("User")

        binding.saveBtn.setOnClickListener {

            showProgressBar()
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val bio = binding.etBio.text.toString()

            val user = User(firstName,lastName,bio)

            if (uid != null){
                databaseReference.child(uid).setValue(user).addOnCompleteListener {
                    if (it.isSuccessful){

                        uploadProfilePic()

                    }else{
                        hideProgressBar()
                        Toast.makeText(this@MainActivity,"failed to update data",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }


        FirebaseMessaging.getInstance().subscribeToTopic("Enas")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.e("d", "done")
                } else {
                    Log.e("d", "failed")
                }
            }
    }

    private fun uploadProfilePic() {
        imageUri = Uri.parse("android.resource://$packageName/${R.drawable.profile}")

        storageReference = FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {
            hideProgressBar()
            Toast.makeText(this@MainActivity,"success to update profile image",Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            hideProgressBar()

            Toast.makeText(this@MainActivity,"failed to update image",Toast.LENGTH_LONG).show()
        }




    }

    private fun showProgressBar(){
        dialog = Dialog(this@MainActivity)
        dialog.requestWindowFeature(window.navigationBarColor)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar(){
        dialog.dismiss()
    }


}

