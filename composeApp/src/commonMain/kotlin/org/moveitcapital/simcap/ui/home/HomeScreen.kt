package org.moveitcapital.simcap.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Menu
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.moveitcapital.simcap.core.AppError
import org.moveitcapital.simcap.core.mvi.BaseScreen
import org.moveitcapital.simcap.domain.model.MenuModule
import org.moveitcapital.simcap.domain.model.Provider
import org.moveitcapital.simcap.domain.model.UserDTO
import org.moveitcapital.simcap.ui.account.AccountScreen
import org.moveitcapital.simcap.ui.analytics.AnalyticsScreen
import org.moveitcapital.simcap.ui.commons.AlertDialogError
import org.moveitcapital.simcap.ui.commons.CommonNetworkImage
import org.moveitcapital.simcap.ui.home.HomeIntent.Init
import org.moveitcapital.simcap.ui.home.HomeIntent.OnLogout
import org.moveitcapital.simcap.ui.home.HomeIntent.OnSelectMenuItem
import org.moveitcapital.simcap.ui.home.components.HeaderSubModule
import org.moveitcapital.simcap.ui.home.components.MenuModuleItem
import org.moveitcapital.simcap.ui.home.components.NavigationDrawerBody
import org.moveitcapital.simcap.ui.home.components.NavigationDrawerHeader
import org.moveitcapital.simcap.ui.home.components.UpdatePasswordDialog
import org.moveitcapital.simcap.ui.login.LoginScreen

object HomeScreen : BaseScreen<HomeState, HomeSideEffect, HomeIntent>() {

    private lateinit var drawerState: DrawerState
    private var showUpdatePassword by mutableStateOf(false)

    @Composable
    override fun getViewModel() = koinViewModel<HomeViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(
        state: HomeState,
        intentHandler: (HomeIntent) -> Unit
    ) {

        drawerState = rememberDrawerState(DrawerValue.Closed)

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {

                    state.auth?.let {
                        NavigationDrawerHeader(
                            modifier = Modifier.fillMaxWidth(),
                            auth = it
                        ) {
                            moveDrawer()
                            navigator.push(AccountScreen)
                        }
                    }

                    NavigationDrawerBody(
                        modifier = Modifier.fillMaxWidth(),
                        menu = state.menu,
                        menuSelected = state.menuSelected,
                        openUpdatePass = {
                            showUpdatePassword = true
                            moveDrawer()
                        },
                        intentHandler = intentHandler
                    )
                }
            },
        ) {
            Scaffold(topBar = { TopBar(state.auth?.userDTO) }) {
                Body(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    menuSelected = state.menuSelected,
                    intentHandler = intentHandler
                )
            }
        }

        if (showUpdatePassword) {
            state.auth?.let { auth ->
                UpdatePasswordDialog(
                    username = auth.user.username,
                    error = state.updatePassError,
                    onDismiss = { showUpdatePassword = false },
                    onUpdate = { intentHandler(it) }
                )
            }
        }

        AlertDialogError(error = state.error) {
            if (state.error is AppError.Session) intentHandler(OnLogout)
            else intentHandler(HomeIntent.OnCloseError)
        }

        state.passChangedResponse?.let {
            AlertDialog(
                title = { Text(it) },
                text = { Text("Se debe iniciar session nuevamente con la contraseña actualizada") },
                onDismissRequest = { intentHandler(OnLogout) },
                confirmButton = {
                    TextButton({ intentHandler(OnLogout) }) {
                        Text("Ir al Login")
                    }
                }
            )
        }

        LaunchedEffect(Unit) { intentHandler(Init) }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopBar(userDTO: List<UserDTO>?) {
        TopAppBar(
            title = { Text(text = userDTO?.firstOrNull()?.company.orEmpty()) },
            navigationIcon = {
                IconButton(
                    onClick = { moveDrawer() }
                ) {
                    Icon(imageVector = Lucide.Menu, contentDescription = null)
                }
            }
        )
    }

    @Composable
    private fun Body(
        modifier: Modifier,
        menuSelected: MenuModule?,
        intentHandler: (HomeIntent) -> Unit
    ) {

        val navigator = LocalNavigator.currentOrThrow

        menuSelected?.let {

            val listMenuSelected = remember(menuSelected) { mutableStateListOf(menuSelected) }

            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                listMenuSelected.lastOrNull()?.let { menu ->

                    HeaderSubModule(
                        modifier = Modifier.fillMaxWidth(),
                        title = menu.name,
                        onBack = { listMenuSelected.removeLast() }
                    )

                    menu.subMenu?.forEach { subMenu ->
                        MenuModuleItem(
                            subMenu = subMenu,
                            listMenuSelected = listMenuSelected
                        )
                    } ?: run {
                        menu.urlAnalytics?.let { navigator.push(AnalyticsScreen(menu)) }
                    }

                } ?: run {
                    LaunchedEffect(Unit) {
                        intentHandler(OnSelectMenuItem(null))
                    }
                }
            }
        } ?: run {
            CommonNetworkImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .wrapContentSize(Alignment.Center),
                url = "https://masivocapital.co/webmasivo/wp-content/uploads/2024/03/LOGO-MASIVO_CONTORNO.png",
                messageError = "SimCap",
                backgroundColor = Color.Transparent,
                contentScale = ContentScale.Fit
            )
        }
    }

    fun moveDrawer(shouldOpen: Boolean = drawerState.isClosed) {
        scope.launch {
            if (shouldOpen) {
                drawerState.open()
            } else {
                drawerState.close()
            }
        }
    }

    override fun effectHandler(sideEffect: HomeSideEffect) {
        when (sideEffect) {
            is HomeSideEffect.CloseDrawer -> moveDrawer()
            is HomeSideEffect.OpenModuleAnalytics -> navigator.push(AnalyticsScreen(sideEffect.module))
            else -> Unit
        }
    }
}