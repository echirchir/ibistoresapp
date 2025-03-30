package dev.chirchir.feature.auth.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.components.MyButton
import dev.chirchir.core.ui.components.ScreenLayout
import dev.chirchir.feature.auth.R
import dev.chirchir.feature.auth.viewmodels.SignInViewModel
import org.koin.androidx.compose.getViewModel

@Composable
internal fun SignInScreen(
    viewModel: SignInViewModel = getViewModel(),
    onSuccess: () -> Unit
) {
    ScreenLayout(
        color = MaterialTheme.colorScheme.background,
        header = {
            HeaderView(
                title = R.string.sign_in_title,
                color = MaterialTheme.colorScheme.background
            )
        },
        footer = {
            MyButton(
                modifier = Modifier.padding(horizontal = 28.dp),
                text = R.string.sign_in_title
            ) {
                onSuccess()
            }
        }
    ) {

    }
}