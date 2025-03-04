package com.cgc.firststep.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")  // Make sure the table name is consistent
data class MyProduct(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // Auto-generating ID
    val name: String,
    val category: String,
    val price: Double,
    val stock: Int
)
