package br.com.isaconstance.bemi.data.local

import android.content.Context

class UserPreferences(context: Context) {

    private val prefs = context.getSharedPreferences(
        "bemi_prefs",
        Context.MODE_PRIVATE
    )

    fun introVista(): Boolean =
        prefs.getBoolean(CHAVE_INTRO_VISTA, false)

    fun marcarIntroVista() {
        prefs.edit().putBoolean(CHAVE_INTRO_VISTA, true).apply()
    }

    fun salvarUsuario(nome: String) {
        prefs.edit()
            .putString(CHAVE_NOME_USUARIO, nome)
            .putBoolean(CHAVE_LOGADO, true)
            .apply()
    }

    fun nomeUsuario(): String? =
        prefs.getString(CHAVE_NOME_USUARIO, null)

    fun estaLogado(): Boolean =
        prefs.getBoolean(CHAVE_LOGADO, false)

    fun sair() {
        prefs.edit()
            .remove(CHAVE_NOME_USUARIO)
            .putBoolean(CHAVE_LOGADO, false)
            .apply()
    }

    companion object {
        private const val CHAVE_INTRO_VISTA = "intro_vista"
        private const val CHAVE_NOME_USUARIO = "nome_usuario"
        private const val CHAVE_LOGADO = "logado"
    }
}
