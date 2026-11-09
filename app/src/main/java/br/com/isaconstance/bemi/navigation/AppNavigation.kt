package br.com.isaconstance.bemi.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.isaconstance.bemi.data.local.UserPreferences
import br.com.isaconstance.bemi.ui.conta.ContaScreen
import br.com.isaconstance.bemi.ui.screens.avisos.AvisosScreen
import br.com.isaconstance.bemi.ui.screens.consultas.ConsultasScreen
import br.com.isaconstance.bemi.ui.screens.documentos.DocumentosScreen
import br.com.isaconstance.bemi.ui.screens.emergencia.EmergenciaScreen
import br.com.isaconstance.bemi.ui.screens.home.HomeScreen
import br.com.isaconstance.bemi.ui.screens.intro.IntroScreen
import br.com.isaconstance.bemi.ui.screens.splash.SplashScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        composable(Routes.SPLASH) {
            SplashScreen(
                onTimeout = {
                    val destino = if (prefs.introVista()) {
                        Routes.HOME
                    } else {
                        Routes.INTRO
                    }

                    navController.navigate(destino) {
                        popUpTo(Routes.SPLASH) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.INTRO) {
            IntroScreen(
                onFinish = {
                    prefs.marcarIntroVista()

                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.INTRO) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                onAbrirDocumentos = {
                    navController.navigate(Routes.DOCUMENTOS)
                },
                onAbrirConsultas = {
                    navController.navigate(Routes.CONSULTAS)
                },
                onAbrirAvisos = {
                    navController.navigate(Routes.AVISOS)
                },
                onAbrirEmergencia = {
                    navController.navigate(Routes.EMERGENCIA)
                },
                onAbrirConta = {
                    navController.navigate(Routes.CONTA)
                }
            )
        }

        composable(Routes.CONSULTAS) {
            ConsultasScreen(
                onVoltarInicio = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.DOCUMENTOS) {
            DocumentosScreen(
                onVoltarInicio = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.AVISOS) {
            AvisosScreen(
                onVoltarInicio = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.EMERGENCIA) {
            EmergenciaScreen(
                onVoltarInicio = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.CONTA) {
            ContaScreen(
                onVoltarInicio = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                onEntrar = {
                    Toast.makeText(
                        context,
                        "Login estará disponível em breve.",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onCadastrar = {
                    Toast.makeText(
                        context,
                        "Cadastro estará disponível em breve.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }
}
