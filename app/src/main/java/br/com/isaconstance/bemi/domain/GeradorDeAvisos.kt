package br.com.isaconstance.bemi.domain

import android.os.Build
import androidx.annotation.RequiresApi
import br.com.isaconstance.bemi.domain.model.Consulta
import br.com.isaconstance.bemi.domain.model.Documento
import br.com.isaconstance.bemi.ui.screens.avisos.AvisoItem
import br.com.isaconstance.bemi.ui.screens.avisos.TipoAviso
import java.time.LocalDate
import java.time.temporal.ChronoUnit

object GeradorDeAvisos {

    @RequiresApi(Build.VERSION_CODES.O)
    fun gerarAvisos(
        consultas: List<Consulta>,
        documentos: List<Documento>
    ): List<AvisoItem> {

        val avisos = mutableListOf<AvisoItem>()

        consultas.forEach { consulta ->
            val dataConsulta = LocalDate.parse(consulta.data)
            val diasRestantes = ChronoUnit.DAYS.between(
                LocalDate.now(),
                dataConsulta
            )

            if (diasRestantes in 0..7) {
                avisos.add(
                    AvisoItem(
                        id = consulta.id,
                        titulo = "Consulta próxima",
                        descricao = "Você tem consulta de ${consulta.especialidade} em ${consulta.data}.",
                        detalhes = "Médico: ${consulta.medico}\nHorário: ${consulta.hora}\nLocal: ${consulta.local}\n${consulta.observacao.orEmpty()}",
                        tipo = TipoAviso.CONSULTA
                    )
                )
            }
        }

        documentos.forEach { documento ->
            if (!documento.validade.isNullOrBlank()) {
                val validade = LocalDate.parse(documento.validade)
                val diasRestantes = ChronoUnit.DAYS.between(
                    LocalDate.now(),
                    validade
                )

                if (diasRestantes in 0..30) {
                    avisos.add(
                        AvisoItem(
                            id = 1000 + documento.id,
                            titulo = "Documento próximo do vencimento",
                            descricao = "O documento ${documento.tipo} está próximo do vencimento.",
                            detalhes = "Validade: ${documento.validade}",
                            tipo = TipoAviso.DOCUMENTO
                        )
                    )
                }
            }

            if (documento.status.equals("Pendente", ignoreCase = true)) {
                avisos.add(
                    AvisoItem(
                        id = 2000 + documento.id,
                        titulo = "Documento pendente",
                        descricao = "O documento ${documento.tipo} precisa de atenção.",
                        detalhes = documento.observacao.orEmpty(),
                        tipo = TipoAviso.DOCUMENTO
                    )
                )
            }
        }

        return avisos
    }
}