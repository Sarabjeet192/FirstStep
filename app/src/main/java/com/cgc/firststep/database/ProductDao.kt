package com.cgc.firststep.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: MyProduct)  // Inserts a product

    @Query("SELECT * FROM products ORDER BY id DESC")
    fun getAllProducts(): LiveData<List<MyProduct>>  // Fetch all products

    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteProductById(productId: Int) // Delete a product by ID

}
