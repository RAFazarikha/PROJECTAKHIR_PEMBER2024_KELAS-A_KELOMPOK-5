package com.example.projectakhir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.projectakhir.login.Login
import com.google.android.material.textfield.TextInputEditText

class RegisActivity : AppCompatActivity() {

    private lateinit var usernameInput: TextInputEditText
    private lateinit var namaInput: TextInputEditText
    private lateinit var profesiInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var passwordValInput: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regis)

        usernameInput = findViewById(R.id.username_edit)
        namaInput = findViewById(R.id.user_edit)
        profesiInput = findViewById(R.id.job_edit)
        passwordInput = findViewById(R.id.password_edit)
        passwordValInput = findViewById(R.id.password2_edit)

        val loginBTN =findViewById<Button>(R.id.btnRegis)

        loginBTN.setOnClickListener{

            if(validasiInput()){
                val intent = Intent(this, BerandaActivity::class.java)

                val data = mapOf<String, String>(
                    "username" to usernameInput.text.toString(),
                    "nama" to namaInput.text.toString(),
                    "profesi" to profesiInput.text.toString()
                )

                val userLogin = Login(
                    data["username"],
                    data["nama"],
                    data["profesi"]
                )

                intent.putExtra("user", userLogin)

                startActivity(intent)
            }else{
                Toast.makeText(this, "Tolong Masukkan Inputan Dengan Benar", Toast.LENGTH_SHORT).show()
            }
        }


        onClick()

    }

    private fun onClick(){
        val lognow =findViewById<TextView>(R.id.textLog)
        lognow.setOnClickListener{
            val intent =Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validasiInput() : Boolean{
        if(usernameInput.text.toString().isEmpty()){
            usernameInput.error = "Tolong Masukkan Username Kamu!"
            return false
        }

        if(passwordInput.text.toString().isEmpty()){
            passwordInput.error = "Tolong Masukkan Password Kamu!"
            return false
        }

        if(passwordValInput.text.toString() != passwordInput.text.toString()){
            passwordValInput.error = "Masukkan Password Dengan Benar!"
            return false
        }

        return true
    }
}