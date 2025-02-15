package com.cgc.firststep

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.cgc.firststep.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {

        binding.btnSubmit.setOnClickListener(this)
        binding.title.setOnClickListener(this)

        //TODO To get result from another activity
        val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val message = data?.getStringExtra("DATA")
                Toast.makeText(this, "Result: $message", Toast.LENGTH_LONG).show()
            }
        }


        // Handle Button Click
//        binding.btnSubmit.setOnClickListener {
//
//    //            val intent = Intent(this@MainActivity, HomeScreen::class.java)
//    //            intent.putExtra("name", "Sarabjeet")
//    //            intent.putExtra("phone_number", 9876543210)
//    //            startActivity(intent)
//
//    //            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
//    //            startActivity(intent)
//
//    //            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:987737658673"))
//    //            startActivity(intent)
//
//    //            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//    //            startActivity(intent)
//
//            val intent = Intent(this, HomeScreen::class.java)
//            getResult.launch(intent)
//
//
//    //            // Input validation
//    //            if (edtText.text.toString().isEmpty()) {
//    //                edtText.error = "Field cannot be empty"
//    //                return@setOnClickListener
//    //            }
//    //
//    //            // CheckBox validation
//    //            if (!checkBox.isChecked) {
//    //                Toast.makeText(this, "Please accept terms & conditions", Toast.LENGTH_SHORT).show()
//    //                return@setOnClickListener
//    //            }
//    //
//    //            // RadioButton validation
//    //            val selectedId = radioGroup.checkedRadioButtonId
//    //            if (selectedId == -1) {
//    //                Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
//    //                return@setOnClickListener
//    //            }
//    //
//    //            val selectedRadio: RadioButton = findViewById(selectedId)
//    //            Toast.makeText(this, "Submitted: ${edtText.text}, Gender: ${selectedRadio.text}", Toast.LENGTH_SHORT).show()
//
//        }

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnSubmit -> {
                submitData()
            }

            R.id.title -> {
                Toast.makeText(this, "Clicked on title", Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun submitData() {
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
    }

}