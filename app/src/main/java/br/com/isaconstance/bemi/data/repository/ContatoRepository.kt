package br.com.isaconstance.bemi.data.repository

import br.com.isaconstance.bemi.data.local.dao.ContatoDao
import br.com.isaconstance.bemi.data.local.entity.ContatoEntity
import br.com.isaconstance.bemi.domain.model.Contato
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContatoRepository(
    private val contatoDao: ContatoDao
) {

    fun listarContatos(): Flow<List<Contato>> {
        return contatoDao.listarTodos().map { contatos ->
            contatos.map { it.toDomain() }
        }
    }

    suspend fun buscarContato(id: Int): Contato? {
        return contatoDao.buscarPorId(id)?.toDomain()
    }

    suspend fun cadastrarContato(contato: Contato) {
        contatoDao.inserir(contato.toEntity())
    }

    suspend fun atualizarContato(contato: Contato) {
        contatoDao.atualizar(contato.toEntity())
    }

    suspend fun excluirContato(contato: Contato) {
        contatoDao.excluir(contato.toEntity())
    }

    private fun ContatoEntity.toDomain() = Contato(
        id = id,
        nome = nome,
        telefone = telefone,
        parentesco = parentesco,
        favorito = favorito
    )

    private fun Contato.toEntity() = ContatoEntity(
        id = id,
        nome = nome,
        telefone = telefone,
        parentesco = parentesco,
        favorito = favorito
    )
}