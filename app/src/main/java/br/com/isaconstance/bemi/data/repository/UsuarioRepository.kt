package br.com.isaconstance.bemi.data.repository

import br.com.isaconstance.bemi.data.local.dao.UsuarioDao
import br.com.isaconstance.bemi.data.local.entity.UsuarioEntity
import java.security.MessageDigest
import java.security.SecureRandom

class UsuarioRepository(
    private val usuarioDao: UsuarioDao
) {

    suspend fun cadastrar(
        nome: String,
        email: String,
        senha: String
    ): ResultadoCadastro {

        val emailNormalizado = email.trim().lowercase()

        if (usuarioDao.buscarPorEmail(emailNormalizado) != null) {
            return ResultadoCadastro.EMAIL_EXISTENTE
        }

        val salt = ByteArray(16)
        SecureRandom().nextBytes(salt)

        val saltHex = salt.joinToString("") {
            "%02x".format(it)
        }

        val senhaHash = gerarHash(senha, saltHex)

        usuarioDao.inserir(
            UsuarioEntity(
                nome = nome.trim(),
                email = emailNormalizado,
                senhaHash = senhaHash,
                salt = saltHex
            )
        )

        return ResultadoCadastro.SUCESSO
    }

    suspend fun autenticar(
        email: String,
        senha: String
    ): UsuarioEntity? {

        val usuario = usuarioDao.buscarPorEmail(
            email.trim().lowercase()
        ) ?: return null

        val hashInformado = gerarHash(senha, usuario.salt)

        return if (hashInformado == usuario.senhaHash) {
            usuario
        } else {
            null
        }
    }

    private fun gerarHash(senha: String, salt: String): String {
        val bytes = MessageDigest.getInstance("SHA-256")
            .digest((salt + senha).toByteArray())

        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }
}

enum class ResultadoCadastro {
    SUCESSO,
    EMAIL_EXISTENTE
}
