package com.cgc.firststep.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "products")  // Make sure the table name is consistent
data class MyProduct(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,  // Auto-generating ID
    val name: String,
    val category: String,
    val price: Double,
    val stock: Int
) : Parcelable
