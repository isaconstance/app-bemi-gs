package br.com.isaconstance.bemi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nome: String,

    val email: String,

    val senhaHash: String,

    val salt: String
)
