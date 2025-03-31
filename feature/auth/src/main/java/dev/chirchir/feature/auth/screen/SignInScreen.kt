package dev.chirchir.feature.auth.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.chirchir.core.ui.base.UiState
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.components.IBITextField
import dev.chirchir.core.ui.components.ICONPOSITION
import dev.chirchir.core.ui.components.MyButton
import dev.chirchir.core.ui.components.ScreenLayout
import dev.chirchir.core.ui.components.SignInAnimation
import dev.chirchir.core.ui.theme.green100
import dev.chirchir.core.ui.toast.MyToast
import dev.chirchir.feature.auth.R
import dev.chirchir.feature.auth.viewmodels.SignInViewModel
import org.koin.androidx.compose.getViewModel

@Composable
internal fun SignInScreen(
    viewModel: SignInViewModel = getViewModel(),
    onSuccess: () -> Unit
) {
    val context = LocalContext.current
    val biometricUiState by viewModel.biometricUiState.collectAsState()

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
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var showPassword by remember { mutableStateOf(false) }

            SignInAnimation(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .size(200.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            IBITextField(
                value = "",
                onChange = {},
                isValid = true,
                iconPosition = ICONPOSITION.END,
                isPassword = false,
                label = "Email",
                hint = "Email",
                errorMessage = "You entered the wrong email",
                onShowPasswordClicked = {

                },
                fieldDescription = "Enter company issued email",
                onDone = {},
                enabled = true,
                readOnly = false,
                boldLabel = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            IBITextField(
                value = "",
                onChange = {},
                isValid = true,
                iconPosition = ICONPOSITION.END,
                isPassword = true,
                errorMessage = "Please check your password and try again",
                label = "Password",
                hint = "Password",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                showPassword = showPassword,
                onShowPasswordClicked = {
                    showPassword = it
                },
                fieldDescription = "Enter your password below",
                onDone = {},
                enabled = true,
                readOnly = false,
                boldLabel = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            if(viewModel.isBiometricEnabled) {
                BiometricSection {
                    println("The biometric icon was clicked")
                    viewModel.showBiometricPrompt(context)
                }
            }

            if(viewModel.isBiometricEnabled) {
                when(val state = biometricUiState) {
                    is UiState.Success -> {
                        LaunchedEffect(Unit) {
                            onSuccess()
                        }
                    }
                    is UiState.Failure -> MyToast.SweetError(state.message)
                    else -> Unit
                }
            }
        }
    }
}

@Composable
private fun BiometricSection(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clickable(onClick = onClick ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = dev.chirchir.core.ui.R.drawable.ic_finger_printer),
            contentDescription = "Enable biometric authentication",
            colorFilter = ColorFilter.tint(green100),
            modifier = Modifier.size(24.dp)
        )
    }
}