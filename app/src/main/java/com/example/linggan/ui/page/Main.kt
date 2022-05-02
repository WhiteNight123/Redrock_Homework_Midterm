package com.example.linggan.ui.Nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.linggan.ui.page.MainViewModel
import com.example.linggan.ui.page.idea.Idea
import com.example.linggan.ui.page.spectrum.SpectrumNav
import com.example.linggan.ui.page.star.Star

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
    Scaffold(backgroundColor = Color(0xFFC8DBB8),

        //backgroundColor = MaterialTheme.colors.primary,
        bottomBar = {
            BottomNavigation (backgroundColor = Color(0xFFC8DBB8)){

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
                SpectrumNav(modifier)
            }
            Tabs.IDEA_PAGE -> {
                Idea(modifier)
            }
            Tabs.STAR_PAGE -> {
                Star()
            }
            else -> {}
        }
    }
}