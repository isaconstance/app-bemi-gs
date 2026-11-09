package br.com.isaconstance.bemi.data.local

import android.content.Context

class UserPreferences(context: Context){

    private val prefs = context.getSharedPreferences("bemi_prefs", Context.MODE_PRIVATE)

    fun introVista(): Boolean = prefs.getBoolean(CHAVE_INTRO_VISTA, false)

    fun marcarIntroVista(){
        prefs.edit().putBoolean(CHAVE_INTRO_VISTA, true).apply()
    }

    companion object{
        private const val CHAVE_INTRO_VISTA = "intro_vista"
    }
}