package br.com.isaconstance.bemi.ui.screens.avisos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import br.com.isaconstance.bemi.ui.theme.BemiColors
import br.com.isaconstance.bemi.ui.theme.BemiTheme


@Composable
fun AvisosScreen(
    onVoltarInicio: () -> Unit,
    viewModel: AvisosViewModel = viewModel()
) {
    val estado by viewModel.uiState.collectAsState()

    AvisosContent(
        estado = estado,
        onMarcarComoLido = viewModel::marcarComoLido,
        onVoltarInicio = onVoltarInicio,
        onTentarNovamente = viewModel::carregarAvisos
    )
}

@Composable
private fun AvisosContent(
    estado: AvisosUiState,
    onMarcarComoLido: (Int) -> Unit,
    onVoltarInicio: () -> Unit,
    onTentarNovamente: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BemiColors.Bege)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onVoltarInicio
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Voltar",
                    tint = BemiColors.VerdeEscuro
                )
            }

            Text(
                text = "Meus avisos",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = BemiColors.VerdeEscuro
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Fique por dentro de informações importantes.",
            fontSize = 16.sp,
            color = BemiColors.TextoSecundario
        )

        Spacer(modifier = Modifier.height(22.dp))

        when (estado) {

            AvisosUiState.Carregando -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = BemiColors.Verde
                )
            }

            AvisosUiState.Vazio -> {
                Text(
                    text = "Você não tem avisos no momento.",
                    fontSize = 17.sp,
                    color = BemiColors.TextoSecundario
                )
            }

            is AvisosUiState.Sucesso -> {
                val naoLidos = estado.avisos.count { !it.lido }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = BemiColors.VerdeClaro
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.Notifications,
                            contentDescription = null,
                            tint = BemiColors.VerdeEscuro,
                            modifier = Modifier.size(30.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "$naoLidos avisos não lidos",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = BemiColors.VerdeEscuro
                            )

                            Text(
                                text = "Confira os lembretes abaixo.",
                                fontSize = 14.sp,
                                color = BemiColors.TextoPrincipal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                estado.avisos.forEach { aviso ->
                    AvisoCard(
                        aviso = aviso,
                        onMarcarComoLido = {
                            onMarcarComoLido(aviso.id)
                        }
                    )

                    Spacer(modifier = Modifier.height(13.dp))
                }
            }

            is AvisosUiState.Erro -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Não foi possível carregar seus avisos.",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = BemiColors.TextoPrincipal
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = estado.mensagem,
                        fontSize = 14.sp,
                        color = BemiColors.TextoSecundario
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onTentarNovamente,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BemiColors.Verde
                        )
                    ) {
                        Text("Tentar novamente")
                    }
                }
            }
        }
    }
}

@Composable
private fun AvisoCard(
    aviso: AvisoItem,
    onMarcarComoLido: () -> Unit
) {
    var expandido by remember { mutableStateOf(false) }

    val corFundo = when (aviso.tipo) {
        TipoAviso.CONSULTA -> Color(0xFFE8EEDC)
        TipoAviso.DOCUMENTO -> Color(0xFFFFF0D8)
        TipoAviso.SEGURANCA -> Color(0xFFFBE5E2)
    }

    val corIcone = when (aviso.tipo) {
        TipoAviso.CONSULTA -> BemiColors.Verde
        TipoAviso.DOCUMENTO -> Color(0xFFAA741A)
        TipoAviso.SEGURANCA -> BemiColors.Erro
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(
            containerColor = corFundo
        )
    ) {
        Column(
            modifier = Modifier.padding(17.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = when (aviso.tipo) {
                        TipoAviso.CONSULTA -> Icons.Outlined.CalendarMonth
                        TipoAviso.DOCUMENTO -> Icons.Outlined.Description
                        TipoAviso.SEGURANCA -> Icons.Outlined.Security
                    },
                    contentDescription = null,
                    tint = corIcone,
                    modifier = Modifier.size(30.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = aviso.titulo,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = BemiColors.TextoPrincipal
                    )

                    if (!aviso.lido) {
                        Text(
                            text = "NOVO",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = corIcone
                        )
                    }
                }

                if (aviso.lido) {
                    Icon(
                        Icons.Outlined.CheckCircle,
                        contentDescription = "Aviso lido",
                        tint = BemiColors.Sucesso
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = aviso.descricao,
                fontSize = 15.sp,
                color = BemiColors.TextoPrincipal,
                lineHeight = 22.sp
            )

            if (expandido) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = aviso.detalhes,
                    fontSize = 15.sp,
                    color = BemiColors.TextoPrincipal,
                    lineHeight = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expandido = !expandido
                        if (!aviso.lido) onMarcarComoLido()
                    }
                    .padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = if (expandido) "Mostrar menos" else "Saiba mais",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = BemiColors.VerdeEscuro
                )

                Spacer(modifier = Modifier.width(5.dp))

                Icon(
                    imageVector = if (expandido) {
                        Icons.Outlined.ExpandLess
                    } else {
                        Icons.Outlined.ExpandMore
                    },
                    contentDescription = null,
                    tint = BemiColors.VerdeEscuro
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AvisosScreenPreview() {
    BemiTheme {
        AvisosContent(
            estado = AvisosUiState.Sucesso(
                listOf(
                    AvisoItem(
                        1,
                        "Consulta amanhã",
                        "Você tem uma consulta de Cardiologia às 14:30.",
                        "Local: Clínica Vida. Rua das Flores, 120. Não esqueça seus exames anteriores.",
                        TipoAviso.CONSULTA
                    ),
                    AvisoItem(
                        2,
                        "Confira seus documentos",
                        "Acesse seus documentos para conferir a situação.",
                        "Verifique a validade dos documentos antes dos atendimentos.",
                        TipoAviso.DOCUMENTO
                    ),
                    AvisoItem(
                        3,
                        "Cuidado com golpes!",
                        "Nunca compartilhe senhas ou códigos.",
                        "Desconfie de ligações que pedem transferências ou dados pessoais.",
                        TipoAviso.SEGURANCA
                    )
                )
            ),
            onMarcarComoLido = {},
            onVoltarInicio = {},
            onTentarNovamente = {}
        )
    }
}
