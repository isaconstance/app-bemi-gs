
package br.com.isaconstance.bemi.ui.screens.cadastro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.isaconstance.bemi.data.repository.ResultadoCadastro
import br.com.isaconstance.bemi.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class EstadoUsuario {
    data object Inicial : EstadoUsuario()
    data object Carregando : EstadoUsuario()
    data class Sucesso(val nome: String) : EstadoUsuario()
    data class Erro(val mensagem: String) : EstadoUsuario()
}

class UsuarioViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _estado = MutableStateFlow<EstadoUsuario>(EstadoUsuario.Inicial)
    val estado: StateFlow<EstadoUsuario> = _estado.asStateFlow()

    fun cadastrar(nome: String, email: String, senha: String) {
        viewModelScope.launch {
            _estado.value = EstadoUsuario.Carregando

            try {
                when (repository.cadastrar(nome, email, senha)) {
                    ResultadoCadastro.SUCESSO -> {
                        _estado.value = EstadoUsuario.Sucesso(nome)
                    }

                    ResultadoCadastro.EMAIL_EXISTENTE -> {
                        _estado.value = EstadoUsuario.Erro(
                            "Este e-mail já está cadastrado."
                        )
                    }
                }
            } catch (e: Exception) {
                _estado.value = EstadoUsuario.Erro(
                    "Não foi possível criar sua conta. Tente novamente."
                )
            }
        }
    }

    fun entrar(email: String, senha: String) {
        viewModelScope.launch {
            _estado.value = EstadoUsuario.Carregando

            try {
                val usuario = repository.autenticar(email, senha)

                _estado.value = if (usuario != null) {
                    EstadoUsuario.Sucesso(usuario.nome)
                } else {
                    EstadoUsuario.Erro("E-mail ou senha incorretos.")
                }
            } catch (e: Exception) {
                _estado.value = EstadoUsuario.Erro(
                    "Não foi possível entrar. Tente novamente."
                )
            }
        }
    }

    fun limparEstado() {
        _estado.value = EstadoUsuario.Inicial
    }
}
