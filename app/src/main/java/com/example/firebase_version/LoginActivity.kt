package com.example.firebase_version

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.firebase_version.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        login()
    }

    private fun login(){
        binding.buttonLogin.setOnClickListener {

            if(TextUtils.isEmpty(binding.editTextTextEmail.text.toString())){
                binding.editTextTextEmail.error = getString(R.string.error)
                return@setOnClickListener
            }

            else if(TextUtils.isEmpty(binding.editTextTextPassword.text.toString())) {
                binding.editTextTextPassword.error = getString(R.string.error)
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(binding.editTextTextEmail.text.toString(),binding.editTextTextPassword.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        startActivity(Intent(this@LoginActivity,ProfileActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this@LoginActivity, "Login failed, please try again later",
                            Toast.LENGTH_LONG).show()
                    }
                }
        }

        binding.textViewRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegistrationActivity::class.java))
        }
    }
}