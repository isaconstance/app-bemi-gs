package br.com.isaconstance.bemi.ui.screens.cadastro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
fun CadastroScreen(
    onVoltar: () -> Unit,
    onCadastrar: (String, String, String) -> Unit,
    onIrLogin: () -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }

    var mostrarSenha by remember { mutableStateOf(false) }
    var mostrarConfirmacao by remember { mutableStateOf(false) }
    var mensagemErro by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BemiColors.Bege)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BemiHeader(
            titulo = "BEMI",
            subtitulo = "Vamos criar sua conta?",
            onVoltar = onVoltar
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Preencha seus dados para começar.",
            fontSize = 17.sp,
            color = BemiColors.TextoSecundario,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        OutlinedTextField(
            value = nome,
            onValueChange = {
                nome = it
                mensagemErro = ""
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Nome completo") },
            placeholder = { Text("Digite seu nome") },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = campoCores()
        )

        Spacer(modifier = Modifier.height(18.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                mensagemErro = ""
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("E-mail") },
            placeholder = { Text("Digite seu e-mail") },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = campoCores()
        )

        Spacer(modifier = Modifier.height(18.dp))

        OutlinedTextField(
            value = senha,
            onValueChange = {
                senha = it
                mensagemErro = ""
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Crie uma senha") },
            placeholder = { Text("Mínimo de 6 caracteres") },
            singleLine = true,
            visualTransformation = if (mostrarSenha)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {
                    mostrarSenha = !mostrarSenha
                }) {
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
            colors = campoCores()
        )

        Spacer(modifier = Modifier.height(18.dp))

        OutlinedTextField(
            value = confirmarSenha,
            onValueChange = {
                confirmarSenha = it
                mensagemErro = ""
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Confirme sua senha") },
            placeholder = { Text("Digite a senha novamente") },
            singleLine = true,
            visualTransformation = if (mostrarConfirmacao)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {
                    mostrarConfirmacao = !mostrarConfirmacao
                }) {
                    Icon(
                        imageVector = if (mostrarConfirmacao)
                            Icons.Default.VisibilityOff
                        else
                            Icons.Default.Visibility,
                        contentDescription = "Mostrar ou ocultar confirmação"
                    )
                }
            },
            shape = RoundedCornerShape(14.dp),
            colors = campoCores()
        )

        if (mensagemErro.isNotBlank()) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = mensagemErro,
                color = BemiColors.Erro,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {
                mensagemErro = when {
                    nome.isBlank() ->
                        "Por favor, informe seu nome."

                    email.isBlank() || !email.contains("@") ->
                        "Informe um e-mail válido."

                    senha.length < 6 ->
                        "A senha precisa ter pelo menos 6 caracteres."

                    senha != confirmarSenha ->
                        "As senhas não são iguais."

                    else -> ""
                }

                if (mensagemErro.isBlank()) {
                    onCadastrar(nome.trim(), email.trim(), senha)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BemiColors.VerdeEscuro
            )
        ) {
            Text(
                text = "Criar minha conta",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Já tem uma conta?",
                fontSize = 16.sp,
                color = BemiColors.TextoSecundario
            )

            TextButton(onClick = onIrLogin) {
                Text(
                    text = "Entrar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = BemiColors.VerdeEscuro
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun campoCores() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = BemiColors.Verde,
    focusedLabelColor = BemiColors.Verde,
    cursorColor = BemiColors.Verde
)
