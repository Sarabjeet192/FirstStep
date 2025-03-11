package com.cgc.firststep.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: MyProduct)  // Inserts a product

    @Query("SELECT * FROM products ORDER BY id DESC")
    fun getAllProducts(): LiveData<List<MyProduct>>  // Fetch all products

    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteProductById(productId: Int) // Delete a product by ID

    @Delete
    suspend fun deleteProduct(product: MyProduct)

    @Update
    suspend fun updateProduct(product: MyProduct)

}
