package com.cgc.firststep.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.cgc.firststep.HomeScreen
import com.cgc.firststep.R
import com.cgc.firststep.databinding.ActivityLoginScreenBinding
import com.cgc.firststep.utils.Constant
import com.cgc.firststep.utils.MyAppPreference

class LoginScreen : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityLoginScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
        binding.lsSubmit.setOnClickListener(this)

    }
    

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.lsSubmit ->{
                MyAppPreference.putBoolPreference(this@LoginScreen, true, Constant.IS_LOGIN)

                startActivity(Intent(this, Dashboard::class.java))
               // showExitDialog()
//                if(isValidate()){
//                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
//                }
            }
        }
    }

    private fun isValidate(): Boolean {

        if(binding.lsEmail.text.isEmpty()){
            binding.lsEmail.error = "Enter your email"
            return false
        }else if(!isValidEmail(binding.lsEmail.text.toString())){
            binding.lsEmail.error = "Please enter valid email"
            return false
        }else if(binding.lsPassword.text.isEmpty()){
            binding.lsPassword.error = "Enter your password"
            return false
        }else if(binding.lsPassword.text.toString().length < 6){
            binding.lsPassword.error = "Password should be 6 or more char long"
            return false
        }

        return true
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return email.matches(emailRegex)
    }


    private fun showExitDialog() {

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.exit_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()

        val cancelBtn: CardView = dialog.findViewById(R.id.edCancel)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        val exitBtn: CardView = dialog.findViewById(R.id.edExit)
        exitBtn.setOnClickListener {
            dialog.dismiss()
            finishAffinity()
        }
    }

}