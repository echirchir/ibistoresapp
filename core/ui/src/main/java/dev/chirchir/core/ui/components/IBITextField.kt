package dev.chirchir.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chirchir.core.ui.R
import dev.chirchir.core.ui.theme.gray00

enum class ICONPOSITION {
    START,
    END;
}

@Composable
fun IBITextField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onDone: () -> Unit = {},
    isValid: Boolean = true,
    showErrorState: Boolean = true,
    errorMessage: String = "",
    showPassword: Boolean = false,
    onShowPasswordClicked: (Boolean) -> Unit = {},
    label: String,
    hint: String,
    fieldDescription: String,
    isPassword: Boolean,
    isLongText: Boolean = false,
    fieldPainter: Painter? = null,
    iconPosition: ICONPOSITION = ICONPOSITION.START,
    onTrailingIconClicked: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    boldLabel: Boolean = true,
    visualTransformation: VisualTransformation? = null,
    keyboardActions: KeyboardActions? = null,
    action: (@Composable () -> Unit)? = null
) {
    val current = LocalFocusManager.current
    val softKeyboard = LocalSoftwareKeyboardController.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        ResponsiveText(
            text = label,
            maxLines = 1,
            color = MaterialTheme.colorScheme.primary,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (boldLabel) FontWeight.Medium else FontWeight.Medium
            )
        )
        if (fieldDescription.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = fieldDescription,
                color = if (isValid || isPassword) Color.Unspecified else MaterialTheme.colorScheme.error
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        val _visualTransformation = visualTransformation
            ?: if (isPassword) {
                if (showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation('\u002A')
                }
            } else {
                VisualTransformation.None
            }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                readOnly = readOnly,
                enabled = enabled,
                modifier = Modifier
                    .weight(1f)
                    .height(if (isLongText) 150.dp else TextFieldDefaults.MinHeight)
                    .testTag(label + "1"),
                singleLine = !isLongText,
                shape = RoundedCornerShape(8.dp),
                value = value,
                onValueChange = onChange,
                leadingIcon = if (fieldPainter == null) null else {
                    when(iconPosition) {
                        ICONPOSITION.END -> { null }
                        ICONPOSITION.START -> {
                            val leading: @Composable () -> Unit = {
                                Image(
                                    modifier = Modifier.clickable {
                                        onTrailingIconClicked?.invoke()
                                    },
                                    painter = fieldPainter,
                                    contentDescription = ""
                                )
                            }
                            leading
                        }
                    }
                },
                trailingIcon = if (!isPassword) {
                    when(iconPosition) {
                        ICONPOSITION.START -> { null }
                        ICONPOSITION.END -> {
                            if (fieldPainter != null) {
                                val trailing: @Composable () -> Unit = {
                                    Image(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .clickable {
                                                onTrailingIconClicked?.invoke()
                                            },
                                        painter = fieldPainter,
                                        contentDescription = ""
                                    )
                                }
                                trailing
                            } else null
                        }
                    }
                } else {
                    val icon: @Composable () -> Unit = {
                        if (showPassword) {
                            Image(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable { onShowPasswordClicked(!showPassword) },
                                painter = painterResource(id = R.drawable.ic_show_password),
                                contentDescription = "show password"
                            )
                        } else {
                            Image(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable { onShowPasswordClicked(!showPassword) },
                                painter = painterResource(id = R.drawable.ic_hide_password),
                                contentDescription = "show password"
                            )
                        }
                    }
                    icon
                },
                keyboardOptions = keyboardOptions,
                placeholder = { Text(text = hint, textAlign = TextAlign.Center) },
                visualTransformation = _visualTransformation,
                isError = if(showErrorState) !isValid else false,
                keyboardActions = keyboardActions ?: KeyboardActions(
                    onNext = {
                        current.moveFocus(FocusDirection.Down)
                        if (isPassword) {
                            onShowPasswordClicked(false)
                        }
                    },
                    onDone = {
                        softKeyboard?.hide()
                        if (isPassword) {
                            onShowPasswordClicked(false)
                        }
                        onDone()
                    }),

                )
            if (action != null) {
                Spacer(modifier = Modifier.width(8.dp))
                action()
            }
        }
        if (!isValid) {
            Spacer(modifier = Modifier.height(8.dp))
            ResponsiveText(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("${label} error"),
                text = errorMessage,
                textStyle = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                maxLines = 2
            )
        }
    }
}

@Composable
fun ResponsiveText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = gray00,
    textAlign: TextAlign = TextAlign.Start,
    textStyle: TextStyle,
    targetTextSizeHeight: TextUnit = textStyle.fontSize,
    maxLines: Int = 1,
) {
    var textSize by remember { mutableStateOf(targetTextSizeHeight) }
    var textSizeReduced by remember{ mutableStateOf(false) }

    Text(
        modifier = modifier,
        text = text,
        color = color,
        textAlign = textAlign,
        fontSize = textSize,
        fontFamily = textStyle.fontFamily,
        fontStyle = textStyle.fontStyle,
        fontWeight = textStyle.fontWeight,
        lineHeight = textStyle.lineHeight,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = { textLayoutResult ->
            val maxCurrentLineIndex: Int = textLayoutResult.lineCount - 1
            if (textLayoutResult.isLineEllipsized(maxCurrentLineIndex)) {
                textSize = (textSize.value - 0.1).sp
                textSizeReduced = true
            }
        }
    )
}