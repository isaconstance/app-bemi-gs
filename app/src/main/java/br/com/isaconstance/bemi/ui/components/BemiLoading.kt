package br.com.isaconstance.bemi.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.isaconstance.bemi.ui.theme.BemiColors

@Composable
fun BemiLoading(
    mensagem: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = BemiColors.Verde
        )

        if (mensagem != null) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = mensagem,
                color = BemiColors.TextoSecundario,
                fontSize = 16.sp
            )
        }
    }
}