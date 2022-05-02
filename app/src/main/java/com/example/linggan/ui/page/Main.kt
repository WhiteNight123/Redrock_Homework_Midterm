package com.example.linggan.ui.Nav

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.linggan.LingGanApp.Companion.context
import com.example.linggan.R
import com.example.linggan.ui.page.MainViewModel
import com.example.linggan.ui.page.idea.Idea
import com.example.linggan.ui.page.spectrum.Spectrum
import com.example.linggan.ui.page.star.Star
import com.example.linggan.utils.showToast

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/4/30
 */
@Composable
fun Main() {
    val viewModel: MainViewModel = hiltViewModel()
    val position by viewModel.position.observeAsState()
    val tabs = Tabs.values()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "标题") },
                navigationIcon = {
                    IconButton(onClick = { showToast(context, "返回") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_arrowleft),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )

                    }
                },
                actions = {
                    IconButton(onClick = { showToast(context, "搜索") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_search_48),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                })
        },
        //backgroundColor = MaterialTheme.colors.primary,
        bottomBar = {
            BottomNavigation {
                tabs.forEach { tab ->
                    BottomNavigationItem(
                        //modifier = Modifier.background(MaterialTheme.colors.secondary),
                        icon = {
                            Icon(
                                painter = painterResource(id = tab.icon),
                                contentDescription = null
                            )
                        },
                        label = { Text(text = tab.title) },
                        alwaysShowLabel = false,
                        selected = tab == position,
                        onClick = { viewModel.onPositionChanged(tab) },
                    )
                }

            }
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        when (position) {
            Tabs.SPECTRUM_PAGE -> {
                Spectrum(modifier)
            }
            Tabs.IDEA_PAGE -> {
                Idea()
            }
            Tabs.STAR_PAGE -> {
                Star()
            }
        }
    }
}