package com.cgc.firststep.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cgc.firststep.database.AppDatabase
import com.cgc.firststep.database.MyProduct
import com.cgc.firststep.databinding.FragmentProductBinding
import com.cgc.firststep.databinding.ItemProductBinding
import com.cgc.firststep.ui.AddProduct
import kotlinx.coroutines.launch

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private lateinit var database: AppDatabase
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = AppDatabase.getDatabase(requireContext())  // Initialize database


        adapter = ProductAdapter()
        binding.fpRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.fpRecyclerView.adapter = adapter

        loadProducts()

        binding.fpAddProduct.setOnClickListener {
            startActivity(Intent(requireContext(), AddProduct::class.java).putExtra("type", 0))
        }
    }

    private fun loadProducts() {

        database.productDao().getAllProducts().observe(viewLifecycleOwner) { productList ->
            adapter.submitList(productList)
        }
    }

    inner class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

        private var productList: List<MyProduct> = emptyList()

        fun submitList(list: List<MyProduct>) {
            productList = list
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ProductViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            holder.bind(productList[position])

        }

        override fun getItemCount() = productList.size

        inner class ProductViewHolder(private val binding: ItemProductBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(product: MyProduct) {
                binding.tvName.text = product.name
                binding.tvCategory.text = product.category
                binding.tvPrice.text = "$${product.price}"
                binding.tvStock.text = "Stock: ${product.stock}"

                binding.tvDelete.setOnClickListener {
                    lifecycleScope.launch {

                        database.productDao().deleteProduct(product)
                     //   database.productDao().deleteProductById(product.id)
                       // loadProducts()  // Refresh the list after deletion
                    }
                }

                binding.tvEdit.setOnClickListener {
                    startActivity(Intent(requireContext(), AddProduct::class.java)
                        .putExtra("type", 1)
                        .putExtra("product", product))
                }
            }
        }
    }
}
