package com.hazratbilal.notecraft.compose

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.hazratbilal.notecraft.compose.model.NotesResponse
import com.hazratbilal.notecraft.compose.theme.AppTheme
import com.hazratbilal.notecraft.compose.theme.DefaultBackground
import com.hazratbilal.notecraft.compose.ui.SplashScreen
import com.hazratbilal.notecraft.compose.ui.components.CustomTopAppBar
import com.hazratbilal.notecraft.compose.ui.components.Drawer
import com.hazratbilal.notecraft.compose.ui.components.ExitDialog
import com.hazratbilal.notecraft.compose.ui.notes.NotesScreen
import com.hazratbilal.notecraft.compose.ui.users.LoginScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.hazratbilal.notecraft.compose.ui.notes.CreateNoteScreen
import com.hazratbilal.notecraft.compose.ui.notes.EditNoteScreen
import com.hazratbilal.notecraft.compose.ui.users.ChangePasswordScreen
import com.hazratbilal.notecraft.compose.ui.users.EditProfileScreen
import com.hazratbilal.notecraft.compose.ui.users.ProfileScreen
import com.hazratbilal.notecraft.compose.ui.users.RegistrationScreen
import com.hazratbilal.notecraft.compose.utils.Constant
import com.hazratbilal.notecraft.compose.utils.SharedPrefs
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = DefaultBackground.toArgb(),
                darkScrim = DefaultBackground.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = DefaultBackground.toArgb(),
                darkScrim = DefaultBackground.toArgb()
            )
        )

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DefaultBackground
                ) {
                    MainScreen(sharedPrefs)
                }
            }
        }

    }
}

@Composable
fun MainScreen(sharedPrefs: SharedPrefs) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var showExitDialog by remember { mutableStateOf(false) }

    var userName by remember { mutableStateOf(sharedPrefs.getString(Constant.FULL_NAME)) }
    var userEmail by remember { mutableStateOf(sharedPrefs.getString(Constant.EMAIL)) }

    LaunchedEffect(navController.currentBackStackEntry) {
        userName = sharedPrefs.getString(Constant.FULL_NAME)
        userEmail = sharedPrefs.getString(Constant.EMAIL)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BackHandler(enabled = currentRoute == "notes") {
        if (drawerState.isOpen) {
            scope.launch { drawerState.close() }
        } else {
            showExitDialog = true
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer(
                userName = userName,
                userEmail = userEmail,
                onProfileClick = {
                    scope.launch {
                        drawerState.close()
                        navController.navigate("profile")
                    }
                },
                onLogoutClick = {
                    scope.launch {
                        drawerState.close()
                        sharedPrefs.clearAll()
                        navController.navigate("login") {
                            popUpTo(0)
                        }
                    }
                }
            )
        }

    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Scaffold(
                    topBar = {
                        CustomTopAppBar(
                            currentRoute = currentRoute,
                            onMenuClick = {
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }
                                }
                            },
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        AppNavigation(navController = navController, sharedPrefs = sharedPrefs)
                    }
                }
            }
        }
    }

    if (showExitDialog) {
        ExitDialog(
            onConfirm = {
                showExitDialog = false
                (navController.context as? ComponentActivity)?.finish()
            },
            onCancel = {
                showExitDialog = false
            }
        )
    }
}

@Composable
private fun AppNavigation(navController: NavHostController, sharedPrefs: SharedPrefs) {
    NavHost(navController = navController, startDestination = "splash") {
        composable(route = "splash") {
            SplashScreen(navController = navController, sharedPrefs = sharedPrefs)
        }
        composable(route = "login") {
            LoginScreen(navController = navController, sharedPrefs = sharedPrefs)
        }
        composable(route = "registration") {
            RegistrationScreen(navController = navController)
        }
        composable(route = "change_password") {
            ChangePasswordScreen(navController = navController, sharedPrefs = sharedPrefs)
        }
        composable(route = "profile") {
            ProfileScreen(navController = navController, sharedPrefs = sharedPrefs)
        }
        composable(route = "edit_profile") {
            EditProfileScreen(navController = navController, sharedPrefs = sharedPrefs)
        }
        composable(route = "create_note") {
            CreateNoteScreen(navController = navController, sharedPrefs = sharedPrefs)
        }
        composable(route = "notes") {
            NotesScreen(navController = navController, sharedPrefs = sharedPrefs)
        }
        composable(
            "edit_note/{noteJson}",
            arguments = listOf(navArgument("noteJson") {
                type = NavType.StringType
            })
        ) {
            val noteJson = it.arguments?.getString("noteJson") ?: ""
            val note = Gson().fromJson(Uri.decode(noteJson), NotesResponse.Note::class.java)
            EditNoteScreen(navController, note, sharedPrefs = sharedPrefs)
        }

    }
}

