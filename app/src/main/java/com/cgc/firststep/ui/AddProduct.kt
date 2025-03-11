package com.cgc.firststep.ui

import android.os.Build
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
    private var pID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)

        if(intent.getIntExtra("type", 0) == 1){
          //  val mProduct = intent.getSerializableExtra("product") as? MyProduct

            val mProduct: MyProduct? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("product", MyProduct::class.java)
            } else {
                intent.getParcelableExtra("product")
            }

            if (mProduct != null) {
                pID = mProduct.id
                binding.etName.setText(mProduct.name)
                binding.etCategory.setText(mProduct.category)
                binding.etPrice.setText(mProduct.price.toString())
                binding.etStock.setText(mProduct.stock.toString())
            }
        }


        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val category = binding.etCategory.text.toString()
            val price = binding.etPrice.text.toString().toDoubleOrNull()
            val stock = binding.etStock.text.toString().toIntOrNull()

            if (name.isNotEmpty() && category.isNotEmpty() && price != null && stock != null) {
                val product = MyProduct(name = name, category = category, price = price, stock = stock)

                lifecycleScope.launch{
                    if(pID == 0) {
                        database.productDao().insertProduct(product)
                        Toast.makeText(this@AddProduct, "Product Saved!", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        product.id = pID
                        database.productDao().updateProduct(product)
                        Toast.makeText(this@AddProduct, "Product Updated!", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                }
            } else {
                Toast.makeText(this, "Please enter all details!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}