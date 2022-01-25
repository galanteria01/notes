package com.shanu.notes_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note (
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content:String?,
    val isDone: Boolean
)

