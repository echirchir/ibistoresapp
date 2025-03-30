package dev.chirchir.feature.home.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import dev.chirchir.core.ui.theme.grey05
import dev.chirchir.core.ui.theme.grey50
import dev.chirchir.core.ui.theme.primary70
import dev.chirchir.core.ui.theme.white

internal enum class Pages(val value: Int?, val title: Int, val icon: Int) {
    HOME(0, dev.chirchir.core.ui.R.string.str_menu_home, dev.chirchir.core.ui.R.drawable.ic_home),
    PRODUCTS(1, dev.chirchir.core.ui.R.string.str_menu_products, dev.chirchir.core.ui.R.drawable.ic_list),
    FAVORITES(2, dev.chirchir.core.ui.R.string.str_menu_favorites, dev.chirchir.core.ui.R.drawable.ic_favorites),
    SETTINGS(3, dev.chirchir.core.ui.R.string.str_menu_settings, dev.chirchir.core.ui.R.drawable.ic_setting);

    companion object {
        fun fromInt(value: Int): Pages {
            return Pages.entries.find { it.value == value } ?: HOME
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun NavigationBarView(
    pagerState: PagerState,
    onSelectPage: (Pages) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(68.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Divider(thickness = 1.dp, color = grey05)
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Pages.entries.forEach { page ->
                TabItem(
                    modifier = Modifier.weight(1f),
                    title = page.title,
                    icon = page.icon,
                    isSelected = page == Pages.fromInt(pagerState.currentPage)
                ) {
                    onSelectPage(page)
                }
            }
        }
    }
}

@Composable
private fun TabItem(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @DrawableRes icon: Int,
    isSelected: Boolean = false,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .clip(CircleShape)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier.size(22.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = if (isSelected) primary70 else grey50,
        )
        Text(
            text = stringResource(id = title),
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) primary70 else grey50,
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
