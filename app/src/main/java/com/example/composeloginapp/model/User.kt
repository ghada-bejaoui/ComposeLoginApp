package com.example.composeloginapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
class User (
    @PrimaryKey
   var username: String,
    val password: String
)
