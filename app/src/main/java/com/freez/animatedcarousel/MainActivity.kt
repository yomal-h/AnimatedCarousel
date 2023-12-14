package com.freez.animatedcarousel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesappmvvmkotlin.movieList.presentation.components.carousel.MovieCarousel
import com.freez.animatedcarousel.model.mockMovies
import com.freez.animatedcarousel.ui.theme.AnimatedCarouselTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            AnimatedCarouselTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(bottomBar = {
                    BottomNavigationBar()
                }, topBar = {
                    TopAppBar(
                        
                        title = {
                            Text(
                                text = "Movies",
                                fontSize = 20.sp
                            )
                        },
                        modifier = Modifier.shadow(2.dp),
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            MaterialTheme.colorScheme.inverseOnSurface
                        )
                    )
                }) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        MovieCarousel(
                            mockMovies = mockMovies,

                            onMovieClick = { /* Handle movie click event */ }
                        )
                    }
                }

            }
            }
        }
    }




@Composable
fun BottomNavigationBar(

) {

    val items = listOf(
        BottomItem(
            title = "Movies",
            icon = Icons.Filled.Movie
        ), BottomItem(
            title = "TV Shows",
            icon = Icons.Outlined.Tv
        )
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(selected = selected.intValue == index, onClick = {
                    selected.intValue = index
                    when (selected.intValue) {
                        0 -> {

                        }

                        1 -> {

                        }
                    }
                }, icon = {
                    Icon(
                        imageVector = bottomItem.icon,
                        contentDescription = bottomItem.title,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }, label = {
                    Text(
                        text = bottomItem.title, color = MaterialTheme.colorScheme.onBackground
                    )
                })
            }
        }
    }

}

data class BottomItem(
    val title: String, val icon: ImageVector
)