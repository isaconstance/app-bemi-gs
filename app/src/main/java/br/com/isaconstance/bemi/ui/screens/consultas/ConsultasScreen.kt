package br.com.isaconstance.bemi.ui.screens.consultas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import br.com.isaconstance.bemi.domain.model.Consulta
import br.com.isaconstance.bemi.domain.model.Endereco
import br.com.isaconstance.bemi.ui.components.BemiHeader
import br.com.isaconstance.bemi.ui.components.BemiLoading
import br.com.isaconstance.bemi.ui.theme.BemiColors
import br.com.isaconstance.bemi.ui.theme.BemiTheme

@Composable
fun ConsultasScreen(
    onVoltarInicio: () -> Unit,
    viewModel: ConsultasViewModel = viewModel()
) {
    val estado by viewModel.uiState.collectAsState()

    ConsultasContent(
        estado = estado,
        onTentarNovamente = { viewModel.carregarConsultas() },
        onVoltarInicio = onVoltarInicio
    )
}


@Composable
private fun ConsultasContent(
    estado: ConsultaUiState,
    onTentarNovamente: () -> Unit,
    onVoltarInicio: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BemiColors.Bege)
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        BemiHeader(
            titulo = "Minhas consultas",
            subtitulo = "Acompanhe seus próximos atendimentos.",
            onVoltar = onVoltarInicio
        )

        Spacer(modifier = Modifier.height(22.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when (estado) {
                is ConsultaUiState.Inicial,
                is ConsultaUiState.Carregando -> {
                    BemiLoading(
                        mensagem = "Carregando consultas..."
                    )
                }

                is ConsultaUiState.Sucesso -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            items = estado.consultas,
                            key = { it.id }
                        ) { consulta ->
                            ConsultaCard(consulta)
                        }
                    }
                }

                is ConsultaUiState.Vazio -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nenhuma consulta cadastrada.",
                            fontSize = 17.sp,
                            color = BemiColors.TextoSecundario
                        )
                    }
                }

                is ConsultaUiState.Erro -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Não conseguimos carregar suas consultas.",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BemiColors.TextoPrincipal
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = estado.mensagem,
                            fontSize = 14.sp,
                            color = BemiColors.TextoSecundario
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = onTentarNovamente
                        ) {
                            Text("Tentar novamente")
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun ConsultaCard(consulta: Consulta) {
    val corStatus = when (consulta.status.lowercase()) {
        "confirmada" -> BemiColors.Sucesso
        "pendente" -> BemiColors.Alerta
        "cancelada" -> BemiColors.Erro
        else -> BemiColors.TextoSecundario
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = BemiColors.Branco
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = consulta.especialidade,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = BemiColors.VerdeEscuro,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    modifier = Modifier
                        .background(
                            color = corStatus.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = consulta.status,
                        color = corStatus,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.PersonOutline,
                    contentDescription = null,
                    tint = BemiColors.Verde
                )
                Text(
                    text = consulta.medico,
                    modifier = Modifier.padding(start = 10.dp),
                    fontSize = 16.sp,
                    color = BemiColors.TextoPrincipal
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = null,
                    tint = BemiColors.Verde
                )
                Text(
                    text = formatarData(consulta.data),
                    modifier = Modifier.padding(start = 10.dp),
                    fontSize = 16.sp,
                    color = BemiColors.TextoPrincipal
                )

                Icon(
                    imageVector = Icons.Outlined.AccessTime,
                    contentDescription = null,
                    tint = BemiColors.Verde,
                    modifier = Modifier.padding(start = 18.dp)
                )
                Text(
                    text = consulta.hora,
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 16.sp,
                    color = BemiColors.TextoPrincipal
                )
            }

            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                    tint = BemiColors.Verde
                )

                Column(
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(
                        text = consulta.local,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BemiColors.TextoPrincipal
                    )

                    Text(
                        text = "${consulta.endereco.logradouro}, " +
                                "${consulta.endereco.numero} - " +
                                "${consulta.endereco.bairro}",
                        fontSize = 14.sp,
                        color = BemiColors.TextoSecundario
                    )

                    Text(
                        text = "${consulta.endereco.cidade} - CEP: " +
                                consulta.endereco.cep,
                        fontSize = 14.sp,
                        color = BemiColors.TextoSecundario
                    )
                }
            }

            if (!consulta.observacao.isNullOrBlank()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            BemiColors.VerdeClaro.copy(alpha = 0.65f),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Lembrete",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = BemiColors.VerdeEscuro
                    )

                    Text(
                        text = consulta.observacao,
                        fontSize = 15.sp,
                        color = BemiColors.TextoPrincipal
                    )
                }
            }
        }
    }
}

private fun formatarData(data: String): String {
    return try {
        val partes = data.split("-")
        "${partes[2]}/${partes[1]}/${partes[0]}"
    } catch (e: Exception) {
        data
    }
}


