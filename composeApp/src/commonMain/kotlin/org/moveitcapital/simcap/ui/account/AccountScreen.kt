package org.moveitcapital.simcap.ui.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.LucideIcon
import com.composables.icons.lucide.SquareUserRound
import com.composables.icons.lucide.UserRound
import org.koin.compose.viewmodel.koinViewModel
import org.moveitcapital.simcap.core.mvi.BaseScreen
import org.moveitcapital.simcap.domain.model.Profile

object AccountScreen : BaseScreen<AccountState, AccountSideEffect, AccountIntent>() {

    @Composable
    override fun getViewModel() = koinViewModel<AccountViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(
        state: AccountState,
        intentHandler: (AccountIntent) -> Unit
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Cuenta") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                imageVector = Lucide.ArrowLeft,
                                contentDescription = "Back"
                            )
                        }
                    },
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                state.profile?.let { UserInfo(it) }?: Text(
                    text = "No hay información del usuario",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        LaunchedEffect(Unit) { intentHandler(AccountIntent.Init) }
    }

    @Composable
    private fun UserInfo(profile: Profile) {

        @Composable
        fun String.showField(text: String){
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "$text:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = this@showField,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(16.dp)
        ) {

            LucideIcon(
                id = Lucide.SquareUserRound.name,
                contentDescription = "User",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            profile.name?.let {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = it,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            profile.identification?.showField(text = "Documento")
            profile.position?.showField(text = "Cargo")
            profile.company?.showField(text = "Empresa")
            profile.email?.showField(text = "Email")
            profile.phoneNumber?.showField(text = "Teléfono")
            profile.process?.showField(text = "Proceso")
        }
    }

    override fun effectHandler(sideEffect: AccountSideEffect) {
        when (sideEffect) {
            else -> Unit
        }
    }
}