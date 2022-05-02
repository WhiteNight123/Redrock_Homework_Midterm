package com.example.linggan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.linggan.ui.Nav.Main
import com.example.linggan.ui.theme.LingGanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LingGanTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Main()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LingGanTheme {
        Greeting("Android")
    }
}
//@OptIn(ExperimentalPagerApi::class)
//@Composable
//private fun Sample() {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("banzai") },
//                backgroundColor = MaterialTheme.colors.surface,
//            )
//        },
//        modifier = Modifier.fillMaxSize()
//    ) { padding ->
//        Column(Modifier.fillMaxSize().padding(padding)) {
//            val pagerState = rememberPagerState()
//
//            // Display 10 items
//            HorizontalPager(
//                count = 10,
//                state = pagerState,
//                // Add 32.dp horizontal padding to 'center' the pages
//                contentPadding = PaddingValues(horizontal = 32.dp),
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxWidth(),
//            ) { page ->
//                PagerSampleItem(
//                    page = page,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .aspectRatio(1f)
//                )
//            }
//
//            HorizontalPagerIndicator(
//                pagerState = pagerState,
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//                    .padding(16.dp),
//            )
//
//            ActionsRow(
//                pagerState = pagerState,
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
//        }
//    }
//}