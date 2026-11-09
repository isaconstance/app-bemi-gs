package br.com.isaconstance.bemi.ui.conta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.AccessibilityNew
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import br.com.isaconstance.bemi.ui.theme.BemiColors
import br.com.isaconstance.bemi.ui.theme.BemiTheme

@Composable
fun ContaScreen(
    onVoltarInicio: () -> Unit,
    onEntrar: () -> Unit,
    onCadastrar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BemiColors.Bege)
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 22.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVoltarInicio) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Voltar",
                    tint = BemiColors.VerdeEscuro
                )
            }

            Text(
                text = "Minha conta",
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                color = BemiColors.VerdeEscuro
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = BemiColors.VerdeClaro
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(58.dp),
                        shape = CircleShape,
                        color = Color.White
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null,
                                tint = BemiColors.VerdeEscuro,
                                modifier = Modifier.size(34.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Olá! Seja bem-vindo.",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = BemiColors.VerdeEscuro
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Entre ou crie sua conta para personalizar o BEMI.",
                            fontSize = 14.sp,
                            color = BemiColors.TextoSecundario,
                            lineHeight = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onEntrar,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BemiColors.VerdeEscuro
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Entrar na minha conta",
                        fontSize = 16.sp
                    )
                }

                OutlinedButton(
                    onClick = onCadastrar,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    border = ButtonDefaults.outlinedButtonBorder,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = BemiColors.VerdeEscuro
                    )
                ) {
                    Text(
                        text = "Criar uma conta",
                        fontSize = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Personalização",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = BemiColors.VerdeEscuro
        )

        Spacer(modifier = Modifier.height(12.dp))

        OpcaoConta(
            icone = {
                Icon(Icons.Outlined.People, contentDescription = null)
            },
            titulo = "Contatos familiares",
            descricao = "Seus contatos de confiança"
        )

        OpcaoConta(
            icone = {
                Icon(Icons.Outlined.AccessibilityNew, contentDescription = null)
            },
            titulo = "Acessibilidade",
            descricao = "Preferências de visualização"
        )

        OpcaoConta(
            icone = {
                Icon(Icons.Outlined.Settings, contentDescription = null)
            },
            titulo = "Configurações",
            descricao = "Preferências do aplicativo"
        )

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Sobre o aplicativo",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = BemiColors.VerdeEscuro
        )

        Spacer(modifier = Modifier.height(12.dp))

        OpcaoConta(
            icone = {
                Icon(Icons.Outlined.Info, contentDescription = null)
            },
            titulo = "Sobre o BEMI",
            descricao = "Conheça nosso aplicativo"
        )
    }
}

@Composable
private fun OpcaoConta(
    icone: @Composable () -> Unit,
    titulo: String,
    descricao: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(46.dp),
            shape = CircleShape,
            color = BemiColors.VerdeClaro
        ) {
            Box(contentAlignment = Alignment.Center) {
                CompositionLocalProvider(
                    LocalContentColor provides BemiColors.VerdeEscuro
                ) {
                    icone()
                }
            }
        }

        Spacer(modifier = Modifier.width(13.dp))

        Column {
            Text(
                text = titulo,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = BemiColors.TextoPrincipal
            )

            Text(
                text = descricao,
                fontSize = 13.sp,
                color = BemiColors.TextoSecundario
            )
        }
    }
}


