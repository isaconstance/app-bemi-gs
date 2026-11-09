package br.com.isaconstance.bemi.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.isaconstance.bemi.ui.theme.BemiColors

@Composable
fun BemiHeader(
    titulo: String,
    subtitulo: String? = null,
    onVoltar: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVoltar) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Voltar",
                    tint = BemiColors.VerdeEscuro
                )
            }

            Text(
                text = titulo,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = BemiColors.VerdeEscuro
            )
        }

        if (subtitulo != null) {
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = subtitulo,
                fontSize = 16.sp,
                color = BemiColors.TextoSecundario
            )
        }
    }
}
