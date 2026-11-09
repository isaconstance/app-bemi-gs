package br.com.isaconstance.bemi.ui.screens.emergencia

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.isaconstance.bemi.ui.components.BemiHeader
import br.com.isaconstance.bemi.ui.theme.BemiColors

private val VermelhoEmergencia = Color(0xFFD9362B)
private val FundoAviso = Color(0xFFFBE5E2)

@Composable
fun EmergenciaScreen(
    viewModel: EmergenciaViewModel,
    onVoltarInicio: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    fun ligar(numero: String) {
        val intent = Intent(
            Intent.ACTION_DIAL,
            Uri.parse("tel:$numero")
        )
        context.startActivity(intent)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BemiColors.Bege)
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        BemiHeader(
            titulo = "Minha conta",
            onVoltar = onVoltarInicio
        )

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = FundoAviso
            )
        ) {
            Row(
                modifier = Modifier.padding(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.WarningAmber,
                    contentDescription = null,
                    tint = VermelhoEmergencia,
                    modifier = Modifier.size(29.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Este aplicativo não substitui os serviços oficiais de emergência.",
                    fontSize = 14.sp,
                    color = Color(0xFF87332D),
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Seus contatos familiares",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = BemiColors.TextoPrincipal
        )

        Spacer(modifier = Modifier.height(12.dp))

        when {
            uiState.carregando -> {
                CircularProgressIndicator(
                    color = BemiColors.VerdeEscuro
                )
            }

            uiState.erro != null -> {
                Text(
                    text = uiState.erro ?: "Erro ao carregar contatos.",
                    color = VermelhoEmergencia,
                    fontSize = 14.sp
                )
            }

            uiState.contatos.isEmpty() -> {
                Text(
                    text = "Nenhum contato cadastrado ainda.",
                    fontSize = 14.sp,
                    color = BemiColors.TextoSecundario
                )
            }

            else -> {
                uiState.contatos.forEach { contato ->
                    ContatoEmergencia(
                        nome = contato.nome,
                        telefone = contato.telefone,
                        onLigar = { ligar(contato.telefone.filter { it.isDigit() }) }
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Precisa de atendimento médico?",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BemiColors.TextoPrincipal
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "SAMU • 192",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = VermelhoEmergencia
                    )

                    Text(
                        text = "Serviço de atendimento móvel de urgência",
                        fontSize = 12.sp,
                        color = BemiColors.TextoSecundario
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Surface(
                    modifier = Modifier
                        .size(52.dp)
                        .clickable { ligar("192") },
                    shape = CircleShape,
                    color = VermelhoEmergencia
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Phone,
                            contentDescription = "Ligar para o SAMU 192",
                            tint = Color.White,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Ao tocar no telefone, você poderá confirmar a ligação no aplicativo de chamadas.",
            fontSize = 13.sp,
            color = BemiColors.TextoSecundario,
            lineHeight = 19.sp
        )
    }
}

@Composable
private fun ContatoEmergencia(
    nome: String,
    telefone: String,
    onLigar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(42.dp),
                shape = CircleShape,
                color = BemiColors.VerdeClaro
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        tint = BemiColors.VerdeEscuro,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = nome,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = BemiColors.TextoPrincipal
                )

                Text(
                    text = telefone,
                    fontSize = 14.sp,
                    color = BemiColors.TextoSecundario
                )
            }

            Surface(
                modifier = Modifier
                    .size(42.dp)
                    .clickable { onLigar() },
                shape = CircleShape,
                color = BemiColors.VerdeEscuro
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = "Ligar para $nome",
                        tint = Color.White,
                        modifier = Modifier.size(21.dp)
                    )
                }
            }
        }
    }
}


