package br.com.isaconstance.bemi.ui.screens.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.isaconstance.bemi.R
import br.com.isaconstance.bemi.ui.theme.BemiColors

private data class IntroPage(
    val titulo: String,
    val descricao: String,
    val imagem: Int
)

@Composable
fun IntroScreen(
    onFinish: () -> Unit,
    initialPage: Int = 0
) {
    var paginaAtual by rememberSaveable {
        mutableIntStateOf(initialPage)
    }

    val paginas = listOf(
        IntroPage(
            titulo = "Bem-vindo(a) ao BEMI!",
            descricao = "Organize seus documentos e consultas de forma simples.",
            imagem = R.drawable.docs
        ),
        IntroPage(
            titulo = "Receba lembretes e avisos importantes!",
            descricao = "Nunca mais esqueça sua consulta ou seus documentos.",
            imagem = R.drawable.datas
        ),
        IntroPage(
            titulo = "Conte com o BEMI para sua segurança!",
            descricao = "Evite golpes e tenha acesso rápido a contatos de emergência.",
            imagem = R.drawable.home
        )
    )

    val pagina = paginas[paginaAtual]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BemiColors.Bege)
            .padding(
                horizontal = 28.dp,
                vertical = 32.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Indicador de progresso
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = if (index == paginaAtual)
                                BemiColors.VerdeEscuro
                            else
                                BemiColors.Dourado.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Título
        Text(
            text = pagina.titulo,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = BemiColors.VerdeEscuro,
            textAlign = TextAlign.Center,
            lineHeight = 34.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Descrição
        Text(
            text = pagina.descricao,
            fontSize = 18.sp,
            color = BemiColors.TextoPrincipal,
            textAlign = TextAlign.Center,
            lineHeight = 27.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        // Ilustração provisória
        Box(
            modifier = Modifier
                .size(230.dp)
                .background(
                    color = BemiColors.VerdeClaro,
                    shape = RoundedCornerShape(80.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = pagina.imagem),
                contentDescription = pagina.titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botão principal
        Button(
            onClick = {
                if (paginaAtual < 2) {
                    paginaAtual++
                } else {
                    onFinish()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BemiColors.VerdeEscuro
            )
        ) {
            Text(
                text = if (paginaAtual == 2)
                    "Concluir"
                else
                    "Próximo  ›",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (paginaAtual < 2) {
            TextButton(
                onClick = onFinish,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Pular",
                    fontSize = 17.sp,
                    color = BemiColors.VerdeEscuro
                )
            }
        }
    }
}


@Preview(showBackground = true, name = "Introdução 1 - Bem-vinda")
@Composable
private fun IntroPreview1() {
    IntroScreen(
        onFinish = {},
        initialPage = 0
    )
}

@Preview(showBackground = true, name = "Introdução 2 - Lembretes")
@Composable
private fun IntroPreview2() {
    IntroScreen(
        onFinish = {},
        initialPage = 1
    )
}

@Preview(showBackground = true, name = "Introdução 3 - Segurança")
@Composable
private fun IntroPreview3() {
    IntroScreen(
        onFinish = {},
        initialPage = 2
    )
}

