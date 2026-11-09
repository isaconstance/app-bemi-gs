package br.com.isaconstance.bemi.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val BemiColorScheme = lightColorScheme(
    primary = BemiColors.VerdeEscuro,
    onPrimary = BemiColors.Branco,

    secondary = BemiColors.Dourado,
    onSecondary = BemiColors.TextoPrincipal,

    tertiary = BemiColors.Verde,
    onTertiary = BemiColors.Branco,

    background = BemiColors.Bege,
    onBackground = BemiColors.TextoPrincipal,

    surface = BemiColors.Branco,
    onSurface = BemiColors.TextoPrincipal,

    error = BemiColors.Erro,
    onError = BemiColors.Branco
)

@Composable
fun BemiTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = BemiColorScheme,
        typography = Typography,
        content = content
    )
}
