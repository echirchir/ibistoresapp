package dev.chirchir.feature.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.chirchir.core.ui.components.HeaderView
import dev.chirchir.core.ui.components.ScreenLayout
import dev.chirchir.feature.home.R
import dev.chirchir.feature.home.viewmodels.HomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = getViewModel()
) {
    ScreenLayout(
        color = Color.Transparent,
        header = {
            HeaderView(
                title = R.string.home_title,
                color = MaterialTheme.colorScheme.surface
            )
        }
    ) {
        Box(
            modifier = Modifier.fillParentMaxSize()
        ){
            AsyncImage(
                model = "https://images.unsplash.com/photo-1647083237445-b689d7b5efce?q=80&w=3087&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                contentDescription = "Background",
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f))
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "To.. Ibi!",
                    style = MaterialTheme.typography.displayLarge.copy(
                        color = Color.White,
                        fontSize = 150.sp,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.5f),
                            offset = Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}