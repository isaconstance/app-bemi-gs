package br.com.isaconstance.bemi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contatos")
data class ContatoEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nome: String,

    val telefone: String,

    val parentesco: String,

    val favorito: Boolean = false
)