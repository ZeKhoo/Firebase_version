package com.example.firebase_version

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import com.example.firebase_version.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth:FirebaseAuth
    private var  databaseReference: DatabaseReference? = null
    private var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        register()

    }

    private fun register(){

        binding.buttonRegister.setOnClickListener {

            if(TextUtils.isEmpty(binding.editTextTextFirstName.text.toString())){
                binding.editTextTextFirstName.error = getString(R.string.error)
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(binding.editTextTextLastName.text.toString())){
                binding.editTextTextLastName.error = getString(R.string.error)
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(binding.editTextTextEmail.text.toString())){
                binding.editTextTextEmail.error = getString(R.string.error)
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(binding.editTextTextPassword.text.toString())){
                binding.editTextTextPassword.error = getString(R.string.error)
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(binding.editTextTextEmail.text.toString(),binding.editTextTextPassword.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        val currentUser = auth.currentUser
                        val currentUserDb = databaseReference?.child((currentUser?.uid)!!)
                        currentUserDb?.child("firstname")?.setValue(binding.editTextTextFirstName.text.toString())
                        currentUserDb?.child("lastname")?.setValue(binding.editTextTextLastName.text.toString())

                        Toast.makeText(this@RegistrationActivity, "Registration Success.",Toast.LENGTH_LONG).show()
                        finish()
                    }
                    else {
                        Toast.makeText(this@RegistrationActivity, "Registration failed, please try again later",Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}

