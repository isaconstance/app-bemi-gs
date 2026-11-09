package br.com.isaconstance.bemi.ui.screens.avisos

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.isaconstance.bemi.data.repository.ConsultaRepository
import br.com.isaconstance.bemi.data.repository.DocumentoRepository
import br.com.isaconstance.bemi.domain.GeradorDeAvisos
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AvisosViewModel : ViewModel() {

    private val consultaRepository = ConsultaRepository()
    private val documentoRepository = DocumentoRepository()

    private val _uiState =
        MutableStateFlow<AvisosUiState>(AvisosUiState.Carregando)

    val uiState: StateFlow<AvisosUiState> = _uiState.asStateFlow()

    init {
        carregarAvisos()
    }

    fun carregarAvisos() {
        viewModelScope.launch {
            _uiState.value = AvisosUiState.Carregando

            try {
               val (consultas, documentos) = coroutineScope {
                   val consultasDeferred = async {
                       consultaRepository.buscarConsultas()
                   }

                   val documentosDeferred = async {
                       documentoRepository.buscarDocumentos()
                   }

                   Pair(
                       consultasDeferred.await(),
                       documentosDeferred.await()
                   )
               }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val avisosGerados = GeradorDeAvisos.gerarAvisos(
                        consultas = consultas,
                        documentos = documentos
                    )

                    val avisoSeguranca = AvisoItem(
                        id = -1,
                        titulo = "Cuidado com golpes!",
                        descricao = "Nunca compartilhe senhas ou códigos recebidos por mensagem.",
                        detalhes = "Desconfie de ligações que pedem transferências, senhas ou dados pessoais. Em caso de dúvida, procure alguém de confiança.",
                        tipo = TipoAviso.SEGURANCA
                    )

                    val todosAvisos = avisosGerados + avisoSeguranca

                    _uiState.value = if (todosAvisos.isEmpty()) {
                        AvisosUiState.Vazio
                    } else {
                        AvisosUiState.Sucesso(todosAvisos)
                    }
                } else {
                    _uiState.value = AvisosUiState.Erro(
                        "Seu dispositivo não oferece suporte à geração de avisos."
                    )
                }

            } catch (e: Exception) {
                _uiState.value = AvisosUiState.Erro(
                    e.message ?: "Não foi possível carregar os avisos."
                )
            }
        }
    }

    fun marcarComoLido(id: Int) {
        val estado = _uiState.value

        if (estado is AvisosUiState.Sucesso) {
            val atualizados = estado.avisos.map { aviso ->
                if (aviso.id == id) aviso.copy(lido = true)
                else aviso
            }

            _uiState.value = AvisosUiState.Sucesso(atualizados)
        }
    }
}