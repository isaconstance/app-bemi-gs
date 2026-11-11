package br.com.isaconstance.bemi.ui.screens.documentos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.isaconstance.bemi.R
import br.com.isaconstance.bemi.domain.model.Documento
import br.com.isaconstance.bemi.ui.components.BemiHeader
import br.com.isaconstance.bemi.ui.components.BemiLoading
import br.com.isaconstance.bemi.ui.theme.BemiColors

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
        BemiHeader(
            titulo = "Meus documentos",
            subtitulo = "Tenha seus documentos sempre organizados.",
            onVoltar = onVoltarInicio
        )

        Spacer(modifier = Modifier.height(22.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when (estado) {

                DocumentoUiState.Inicial,
                DocumentoUiState.Carregando -> {
                    BemiLoading(
                        mensagem = "Carregando documentos..."
                    )
                }

                is DocumentoUiState.Erro -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
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

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
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
                            onClick = {
                                mostrarPendencias = !mostrarPendencias
                            },
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
                            text = if (mostrarPendencias) {
                                "Documentos pendentes"
                            } else {
                                "Seus documentos"
                            },
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
    }
}

@Composable
private fun DocumentoCard(documento: Documento) {

    var mostrarDocumento by remember(documento.id) {
        mutableStateOf(false)
    }

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

    val imagemDocumento = when {
        documento.tipo.contains("rg", ignoreCase = true) ||
                documento.tipo.contains("identidade", ignoreCase = true) ->
            R.drawable.documento_rg

        documento.tipo.contains("sus", ignoreCase = true) ->
            R.drawable.cartao_sus

        documento.tipo.contains("resid", ignoreCase = true) ||
                documento.tipo.contains("comprovante", ignoreCase = true) ->
            R.drawable.residencia

        else -> null
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = BemiColors.Branco
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

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

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = documento.tipo,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = BemiColors.TextoPrincipal
                    )

                    Text(
                        text = documento.validade?.let {
                            "Validade: ${formatarData(it)}"
                        } ?: "Validade não informada",
                        fontSize = 13.sp,
                        color = BemiColors.TextoSecundario
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = iconeStatus,
                        contentDescription = null,
                        tint = corStatus,
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = documento.status,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = corStatus
                    )
                }
            }

            documento.observacao?.let {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = it,
                    fontSize = 13.sp,
                    color = BemiColors.TextoSecundario
                )
            }

            if (imagemDocumento != null) {
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = {
                        mostrarDocumento = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = BemiColors.VerdeEscuro
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Description,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Ver documento")
                }
            }
        }
    }

    if (mostrarDocumento && imagemDocumento != null) {
        AlertDialog(
            onDismissRequest = {
                mostrarDocumento = false
            },
            title = {
                Text(
                    text = documento.tipo,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Image(
                        painter = painterResource(
                            id = imagemDocumento
                        ),
                        contentDescription = "Imagem ilustrativa de ${documento.tipo}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 350.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Imagem ilustrativa para demonstração.",
                        fontSize = 12.sp,
                        color = BemiColors.TextoSecundario
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarDocumento = false
                    }
                ) {
                    Text("Fechar")
                }
            },
            containerColor = BemiColors.Branco
        )
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
