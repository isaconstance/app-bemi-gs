package br.com.isaconstance.bemi.ui.screens.acesso

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.isaconstance.bemi.ui.theme.BemiColors

@Composable
fun AcessoScreen(
    onEntrar: () -> Unit,
    onCadastrar: () -> Unit,
    onContinuarVisitante: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BemiColors.Bege)
            .padding(horizontal = 28.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "BEMI",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = BemiColors.VerdeEscuro
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Bem-estar, cuidado e companhia.",
            fontSize = 18.sp,
            color = BemiColors.TextoPrincipal,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Como deseja continuar?",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = BemiColors.VerdeEscuro,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onEntrar,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BemiColors.Verde
            )
        ) {
            Text(
                text = "Entrar na minha conta",
                fontSize = 17.sp
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedButton(
            onClick = onCadastrar,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Criar uma conta",
                fontSize = 17.sp,
                color = BemiColors.VerdeEscuro
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Ou, se preferir:",
            fontSize = 15.sp,
            color = BemiColors.TextoSecundario
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onContinuarVisitante,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Conhecer o aplicativo",
                fontSize = 17.sp,
                color = BemiColors.Verde
            )
        }
    }
}
