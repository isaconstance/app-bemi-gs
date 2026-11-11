package br.com.isaconstance.bemi.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.isaconstance.bemi.data.local.UserPreferences
import br.com.isaconstance.bemi.ui.screens.acesso.AcessoScreen
import br.com.isaconstance.bemi.ui.screens.conta.ContaScreen
import br.com.isaconstance.bemi.ui.screens.avisos.AvisosScreen
import br.com.isaconstance.bemi.ui.screens.cadastro.CadastroScreen
import br.com.isaconstance.bemi.ui.screens.cadastro.EstadoUsuario
import br.com.isaconstance.bemi.ui.screens.cadastro.UsuarioViewModel
import br.com.isaconstance.bemi.ui.screens.cadastro.UsuarioViewModelFactory
import br.com.isaconstance.bemi.ui.screens.consultas.ConsultasScreen
import br.com.isaconstance.bemi.ui.screens.documentos.DocumentosScreen
import br.com.isaconstance.bemi.ui.screens.emergencia.EmergenciaScreen
import br.com.isaconstance.bemi.ui.screens.emergencia.EmergenciaViewModel
import br.com.isaconstance.bemi.ui.screens.home.HomeScreen
import br.com.isaconstance.bemi.ui.screens.intro.IntroScreen
import br.com.isaconstance.bemi.ui.screens.login.LoginScreen
import br.com.isaconstance.bemi.ui.screens.splash.SplashScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }

    val emergenciaViewModel: EmergenciaViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(
                modelClass: Class<T>
            ): T {
                @Suppress("UNCHECKED_CAST")
                return EmergenciaViewModel(context.applicationContext) as T
            }
        }
    )

    val usuarioViewModel: UsuarioViewModel = viewModel(
        factory = UsuarioViewModelFactory(context.applicationContext)
    )

    val estadoUsuario by usuarioViewModel.estado.collectAsState()

    // Verifica se o usuário está logado antes de abrir áreas protegidas.
    fun abrirAreaProtegida(rota: String) {
        if (prefs.estaLogado()) {
            navController.navigate(rota)
        } else {
            Toast.makeText(
                context,
                "Entre ou crie sua conta para acessar esta área.",
                Toast.LENGTH_LONG
            ).show()

            navController.navigate(Routes.LOGIN)
        }
    }

    LaunchedEffect(estadoUsuario) {
        when (val estado = estadoUsuario) {

            is EstadoUsuario.Sucesso -> {
                Toast.makeText(
                    context,
                    "Bem-vindo(a), ${estado.nome}!",
                    Toast.LENGTH_SHORT
                ).show()

                prefs.salvarUsuario(estado.nome)

                navController.navigate(Routes.HOME) {
                    launchSingleTop = true
                }

                usuarioViewModel.limparEstado()
            }

            is EstadoUsuario.Erro -> {
                Toast.makeText(
                    context,
                    estado.mensagem,
                    Toast.LENGTH_LONG
                ).show()

                usuarioViewModel.limparEstado()
            }

            else -> Unit
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        composable(Routes.SPLASH) {
            SplashScreen(
                onTimeout = {
                    val destino = when {
                        prefs.estaLogado() -> Routes.HOME
                        prefs.introVista() -> Routes.ACESSO
                        else -> Routes.INTRO
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

                    navController.navigate(Routes.ACESSO) {
                        popUpTo(Routes.INTRO) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.ACESSO) {
            AcessoScreen(
                onEntrar = {
                    navController.navigate(Routes.LOGIN)
                },
                onCadastrar = {
                    navController.navigate(Routes.CADASTRO)
                },
                onContinuarVisitante = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.ACESSO) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onVoltar = {
                    if (!navController.popBackStack()) {
                        navController.navigate(Routes.ACESSO) {
                            launchSingleTop = true
                        }
                    }
                },
                onEntrar = { email, senha ->
                    usuarioViewModel.entrar(email, senha)
                },
                onCadastrar = {
                    navController.navigate(Routes.CADASTRO)
                }
            )
        }

        composable(Routes.CADASTRO) {
            CadastroScreen(
                onVoltar = {
                    if (!navController.popBackStack()) {
                        navController.navigate(Routes.ACESSO) {
                            launchSingleTop = true
                        }
                    }
                },
                onCadastrar = { nome, email, senha ->
                    usuarioViewModel.cadastrar(nome, email, senha)
                },
                onIrLogin = {
                    navController.navigate(Routes.LOGIN)
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                nomeUsuario = prefs.nomeUsuario(),

                onAbrirDocumentos = {
                    abrirAreaProtegida(Routes.DOCUMENTOS)
                },

                onAbrirConsultas = {
                    abrirAreaProtegida(Routes.CONSULTAS)
                },

                onAbrirAvisos = {
                    navController.navigate(Routes.AVISOS)
                },

                onAbrirEmergencia = {
                    abrirAreaProtegida(Routes.EMERGENCIA)
                },

                onAbrirConta = {
                    navController.navigate(Routes.CONTA)
                }
            )
        }

        composable(Routes.CONSULTAS) {
            if (prefs.estaLogado()) {
                ConsultasScreen(
                    onVoltarInicio = {
                        navController.popBackStack()
                    }
                )
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            }
        }

        composable(Routes.DOCUMENTOS) {
            if (prefs.estaLogado()) {
                DocumentosScreen(
                    onVoltarInicio = {
                        navController.popBackStack()
                    }
                )
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            }
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
            if (prefs.estaLogado()) {
                EmergenciaScreen(
                    viewModel = emergenciaViewModel,
                    onVoltarInicio = {
                        navController.popBackStack()
                    }
                )
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            }
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
                    navController.navigate(Routes.LOGIN)
                },

                onCadastrar = {
                    navController.navigate(Routes.CADASTRO)
                },

                estaLogado = prefs.estaLogado(),
                nomeUsuario = prefs.nomeUsuario(),

                onSair = {
                    prefs.sair()

                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
