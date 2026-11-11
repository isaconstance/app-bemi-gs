package br.com.isaconstance.bemi.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.isaconstance.bemi.ui.components.BemiHeader
import br.com.isaconstance.bemi.ui.theme.BemiColors

@Composable
fun LoginScreen(
    onVoltar: () -> Unit,
    onEntrar: (String, String) -> Unit,
    onCadastrar: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mostrarSenha by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BemiColors.Bege)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BemiHeader(
            titulo = "BEMI",
            subtitulo = "Que bom ter você de volta!",
            onVoltar = onVoltar
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Entre na sua conta para acessar suas informações.",
            fontSize = 16.sp,
            color = BemiColors.TextoSecundario,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(36.dp))

        Spacer(modifier = Modifier.height(36.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("E-mail") },
            placeholder = { Text("Digite seu e-mail") },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BemiColors.Verde,
                focusedLabelColor = BemiColors.Verde,
                cursorColor = BemiColors.Verde
            )
        )

        Spacer(modifier = Modifier.height(18.dp))

        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Senha") },
            placeholder = { Text("Digite sua senha") },
            singleLine = true,
            visualTransformation = if (mostrarSenha)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { mostrarSenha = !mostrarSenha }) {
                    Icon(
                        imageVector = if (mostrarSenha)
                            Icons.Default.VisibilityOff
                        else
                            Icons.Default.Visibility,
                        contentDescription = "Mostrar ou ocultar senha"
                    )
                }
            },
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BemiColors.Verde,
                focusedLabelColor = BemiColors.Verde,
                cursorColor = BemiColors.Verde
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { onEntrar(email.trim(), senha) },
            enabled = email.isNotBlank() && senha.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BemiColors.VerdeEscuro
            )
        ) {
            Text(
                text = "Entrar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ainda não tem uma conta?",
                color = BemiColors.TextoSecundario,
                fontSize = 15.sp
            )
            TextButton(onClick = onCadastrar) {
                Text(
                    text = "Cadastre-se",
                    color = BemiColors.VerdeEscuro,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
