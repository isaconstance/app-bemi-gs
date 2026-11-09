package br.com.isaconstance.bemi.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.isaconstance.bemi.data.local.entity.ContatoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContatoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(contato: ContatoEntity)

    @Update
    suspend fun atualizar(contato: ContatoEntity)

    @Delete
    suspend fun excluir(contato: ContatoEntity)

    @Query("SELECT * FROM contatos ORDER BY favorito DESC, nome ASC")
    fun listarTodos(): Flow<List<ContatoEntity>>

    @Query("SELECT * FROM contatos WHERE id = :id")
    suspend fun buscarPorId(id: Int): ContatoEntity?
}