package com.cgc.firststep.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cgc.firststep.database.AppDatabase
import com.cgc.firststep.database.MyProduct
import com.cgc.firststep.databinding.ActivityAddProductBinding
import kotlinx.coroutines.launch

class AddProduct : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val category = binding.etCategory.text.toString()
            val price = binding.etPrice.text.toString().toDoubleOrNull()
            val stock = binding.etStock.text.toString().toIntOrNull()

            if (name.isNotEmpty() && category.isNotEmpty() && price != null && stock != null) {
                val product = MyProduct(name = name, category = category, price = price, stock = stock)

                lifecycleScope.launch {

                    database.productDao().insertProduct(product)
                    Toast.makeText(this@AddProduct, "Product Saved!", Toast.LENGTH_SHORT).show()
                    finish()

                }
            } else {
                Toast.makeText(this, "Please enter all details!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}