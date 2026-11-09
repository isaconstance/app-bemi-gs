package br.com.isaconstance.bemi.ui.screens.documentos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.isaconstance.bemi.domain.model.Documento
import br.com.isaconstance.bemi.ui.theme.BemiColors
import br.com.isaconstance.bemi.ui.theme.BemiTheme

@Composable
fun DocumentosScreen(
    onVoltarInicio: () -> Unit,
    viewModel: DocumentosViewModel = viewModel()
) {
    val estado by viewModel.uiState.collectAsState()

    DocumentosContent(
        estado = estado,
        onVoltarInicio = onVoltarInicio,
        onTentarNovamente = { viewModel.carregarDocumentos() }
    )
}

@Composable
private fun DocumentosContent(
    estado: DocumentoUiState,
    onVoltarInicio: () -> Unit,
    onTentarNovamente: () -> Unit
) {
    var mostrarPendencias by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BemiColors.Bege)
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVoltarInicio) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Voltar para início",
                    tint = BemiColors.VerdeEscuro
                )
            }

            Text(
                text = "Meus documentos",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = BemiColors.VerdeEscuro
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Tenha seus documentos sempre organizados.",
            fontSize = 16.sp,
            color = BemiColors.TextoSecundario
        )

        Spacer(modifier = Modifier.height(20.dp))

        when (estado) {
            DocumentoUiState.Inicial,
            DocumentoUiState.Carregando -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = BemiColors.Verde)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Carregando documentos...",
                            color = BemiColors.TextoSecundario,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            is DocumentoUiState.Erro -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Outlined.ErrorOutline,
                            contentDescription = null,
                            tint = BemiColors.Erro,
                            modifier = Modifier.size(48.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Não foi possível carregar seus documentos.",
                            fontSize = 18.sp,
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
                            Icon(
                                imageVector = Icons.Outlined.Refresh,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Tentar novamente")
                        }
                    }
                }
            }

            DocumentoUiState.Vazio -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nenhum documento cadastrado ainda.",
                        fontSize = 17.sp,
                        color = BemiColors.TextoSecundario
                    )
                }
            }

            is DocumentoUiState.Sucesso -> {
                val documentos = estado.documentos
                val pendencias = documentos.filter {
                    !it.status.equals("OK", ignoreCase = true)
                }

                val documentosExibidos = if (mostrarPendencias) {
                    pendencias
                } else {
                    documentos
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = BemiColors.VerdeClaro
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Description,
                            contentDescription = null,
                            tint = BemiColors.VerdeEscuro,
                            modifier = Modifier.size(32.dp)
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = "${documentos.size} documentos cadastrados",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = BemiColors.VerdeEscuro
                            )

                            Text(
                                text = if (pendencias.isEmpty()) {
                                    "Todos estão em situação regular."
                                } else {
                                    "${pendencias.size} documento(s) precisam de atenção."
                                },
                                fontSize = 14.sp,
                                color = BemiColors.TextoSecundario
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                OutlinedButton(
                    onClick = { mostrarPendencias = !mostrarPendencias },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = BemiColors.VerdeEscuro
                    )
                ) {
                    Text(
                        text = if (mostrarPendencias) {
                            "Ver todos os documentos"
                        } else {
                            "Ver pendências (${pendencias.size})"
                        },
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (mostrarPendencias) "Documentos pendentes" else "Seus documentos",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = BemiColors.VerdeEscuro
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (documentosExibidos.isEmpty()) {
                    Text(
                        text = "Nenhuma pendência encontrada. Tudo certo!",
                        fontSize = 16.sp,
                        color = BemiColors.TextoSecundario,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = documentosExibidos,
                            key = { it.id }
                        ) { documento ->
                            DocumentoCard(documento = documento)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DocumentoCard(documento: Documento) {
    val status = documento.status.trim().lowercase()

    val corStatus = when (status) {
        "ok" -> BemiColors.Sucesso
        "vencido" -> BemiColors.Erro
        "ilegível", "ilegivel" -> BemiColors.Alerta
        else -> BemiColors.TextoSecundario
    }

    val iconeStatus = when (status) {
        "ok" -> Icons.Outlined.CheckCircle
        "vencido" -> Icons.Outlined.ErrorOutline
        "ilegível", "ilegivel" -> Icons.Outlined.WarningAmber
        else -> Icons.Outlined.Description
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = BemiColors.Branco
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Description,
                    contentDescription = null,
                    tint = BemiColors.Verde,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = documento.tipo,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = BemiColors.TextoPrincipal,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        imageVector = iconeStatus,
                        contentDescription = null,
                        tint = corStatus,
                        modifier = Modifier.size(18.dp)
                    )

                    Text(
                        text = documento.status,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = corStatus
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = documento.validade?.let {
                    "Validade: ${formatarData(it)}"
                } ?: "Validade: Não informada",
                fontSize = 15.sp,
                color = BemiColors.TextoSecundario
            )

            documento.observacao?.let {
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = BemiColors.TextoSecundario
                )
            }
        }
    }
}

private fun formatarData(data: String): String {
    val partes = data.split("-")

    return if (partes.size == 3) {
        "${partes[2]}/${partes[1]}/${partes[0]}"
    } else {
        data
    }
}


