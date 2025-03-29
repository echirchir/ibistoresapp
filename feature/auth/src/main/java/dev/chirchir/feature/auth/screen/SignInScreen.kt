package dev.chirchir.feature.auth.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.components.MyButton
import dev.chirchir.core.ui.components.ScreenLayout
import dev.chirchir.feature.auth.R

@Composable
internal fun SignInScreen(
    onSuccess: () -> Unit
) {
    ScreenLayout(
        header = {
            HeaderView(title = R.string.sign_in_title)
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