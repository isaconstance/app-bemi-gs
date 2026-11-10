package br.com.isaconstance.bemi.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import br.com.isaconstance.bemi.R
import br.com.isaconstance.bemi.ui.theme.BemiColors
import br.com.isaconstance.bemi.ui.theme.BemiTheme

@Composable
fun HomeScreen(
    onAbrirDocumentos: () -> Unit,
    onAbrirConsultas: () -> Unit,
    onAbrirAvisos: () -> Unit,
    onAbrirEmergencia: () -> Unit,
    onAbrirConta: () -> Unit,
    nomeUsuario: String? = null,
) {
    Scaffold(
        containerColor = BemiColors.Bege,
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFFFF9EF),
                tonalElevation = 3.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(Icons.Outlined.Home, contentDescription = "Início")
                    },
                    label = { Text("Início", fontSize = 12.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BemiColors.VerdeEscuro,
                        selectedTextColor = BemiColors.VerdeEscuro,
                        indicatorColor = BemiColors.VerdeClaro
                    )
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onAbrirDocumentos,
                    icon = {
                        Icon(Icons.Outlined.Description, contentDescription = "Documentos")
                    },
                    label = { Text("Documentos", fontSize = 12.sp) }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onAbrirConsultas,
                    icon = {
                        Icon(Icons.Outlined.CalendarMonth, contentDescription = "Consultas")
                    },
                    label = { Text("Consultas", fontSize = 12.sp) }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onAbrirConta,
                    icon = {
                        Icon(Icons.Outlined.Person,
                            contentDescription = "Minha conta")
                    },
                    label = { Text("Conta", fontSize = 12.sp) }
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BemiColors.VerdeEscuro)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BemiColors.VerdeEscuro)
                    .height(76.dp)
                    .padding(horizontal = 22.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo_bemi),
                        contentDescription = "Logo BEMI",
                        modifier = Modifier.size(42.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.width(9.dp))

                    Text(
                        text = "BEMI",
                        color = BemiColors.Dourado,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(onClick = onAbrirAvisos) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notificações",
                        tint = Color.White,
                        modifier = Modifier.size(27.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = 24.dp,
                            topEnd = 24.dp
                        )
                    )
                    .background(BemiColors.Bege)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 18.dp)
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = if (nomeUsuario.isNullOrBlank()){
                                "Bem-vindo(a) ao BEMI!"
                            } else{
                                "Olá, $nomeUsuario!"
                            },
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = BemiColors.VerdeEscuro
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "Que bom ter você por aqui!",
                            fontSize = 15.sp,
                            color = BemiColors.TextoPrincipal
                        )
                    }

                }

                Spacer(modifier = Modifier.height(17.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE7EBD8)
                    ),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(112.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.bemi_robot),
                                contentDescription = "Robozinho BEMI",
                                modifier = Modifier
                                    .width(105.dp)
                                    .fillMaxHeight(),
                                contentScale = ContentScale.Fit
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "Como posso\nte ajudar hoje?",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = BemiColors.VerdeEscuro,
                                lineHeight = 24.sp
                            )
                        }

                        Button(
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp),
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BemiColors.VerdeEscuro
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Mic,
                                contentDescription = null,
                                modifier = Modifier.size(21.dp)
                            )

                            Spacer(modifier = Modifier.width(9.dp))

                            Text(
                                text = "Falar com o BEMI",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(17.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AtalhoCard(
                        titulo = "Documentos",
                        onClick = onAbrirDocumentos,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Outlined.Description,
                            contentDescription = null
                        )
                    }

                    AtalhoCard(
                        titulo = "Consultas",
                        onClick = onAbrirConsultas,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Outlined.CalendarMonth,
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AtalhoCard(
                        titulo = "Avisos",
                        onClick = onAbrirAvisos,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Outlined.Notifications,
                            contentDescription = null
                        )
                    }

                    AtalhoCard(
                        titulo = "Família",
                        onClick = onAbrirEmergencia,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Outlined.Groups,
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onAbrirEmergencia,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(17.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC93630)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Call,
                        contentDescription = null,
                        modifier = Modifier.size(23.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Emergência",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 18.sp
                        )

                        Text(
                            text = "Ligue 192",
                            fontSize = 13.sp,
                            lineHeight = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
            }
        }
    }
}

@Composable
private fun AtalhoCard(
    titulo: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icone: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .height(76.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF9EF)
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CompositionLocalProvider(
                LocalContentColor provides BemiColors.Verde
            ) {
                Box(modifier = Modifier.size(27.dp)) {
                    Box(modifier = Modifier.align(Alignment.Center)) {
                        icone()
                    }
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = titulo,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = BemiColors.TextoPrincipal
            )
        }
    }
}

