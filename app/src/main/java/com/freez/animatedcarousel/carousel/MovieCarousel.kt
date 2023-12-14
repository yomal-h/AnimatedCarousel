package com.example.moviesappmvvmkotlin.movieList.presentation.components.carousel

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size

import com.freez.animatedcarousel.model.MockData
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieCarousel(
    mockMovies: List<MockData>,
    modifier: Modifier = Modifier,
    imageCornerRadius: Dp = 16.dp,
    pagerPaddingValues: PaddingValues = PaddingValues(horizontal = 100.dp),
    onMovieClick: (MockData) -> Unit
) {
    val isDarkMode = isSystemInDarkTheme()

    // Pager state for handling horizontal pager
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { mockMovies.size }
    )

    // Extension function to format a Double with a specific number of decimal places
    fun Double.format(decimalPlaces: Int): String {
        return String.format(Locale.getDefault(), "%.${decimalPlaces}f", this)
    }

    // Alpha value for backdrop image animation
    var backdropAlpha by remember { mutableStateOf(1f) }

    // Effect to animate the backdrop image when the page changes
    LaunchedEffect(pagerState.currentPage) {
        backdropAlpha = 1f
        launch {
            animate(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = tween(durationMillis = 800)
            ) { value, _ ->
                backdropAlpha = value
            }
        }
    }

    // MovieCarousel layout
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Backdrop image for the top half of the screen
        val backdropImage = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(mockMovies[pagerState.currentPage].backdrop_path)
                .scale(Scale.FILL)
                .size(Size.ORIGINAL)
                .build(),
            contentScale = ContentScale.Fit,
        ).state

        backdropImage.painter?.let {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp) //edit this to change backdrop image height
                    .blur(8.dp) //blur the image
                    .clip(RoundedCornerShape(0.dp, 0.dp, imageCornerRadius, imageCornerRadius))
                    .alpha(backdropAlpha),
                painter = it,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        // Column for carousel content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                HorizontalPager(
                    state = pagerState,
                    contentPadding = pagerPaddingValues,
                    pageSpacing = 80.dp, // page item's spacing
                    modifier = modifier.weight(1f),


                ) { page ->
                    // Calculate page offset for scaling effect
                    val pageOffset =
                        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

                    // Calculate scale factor for the poster
                    val scaleFactor = 0.9f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)

                    // Target vertical offset for the poster
                    val targetVerticalOffset = if (page == pagerState.currentPage) 45.dp else 0.dp

                    // Animate vertical offset with spring animation
                    val animatedVerticalOffset by animateDpAsState(
                        targetValue = targetVerticalOffset,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessLow,
                        ), label = ""
                    )

                    Box(
                        modifier = modifier
                            .graphicsLayer {
                                scaleX = scaleFactor
                                scaleY = scaleFactor
                            }
                            .alpha(scaleFactor.coerceIn(0f, 1f))
                            .padding(8.dp)
                            .height(340.dp) //poster image height
                            .clip(RoundedCornerShape(imageCornerRadius))
                            .offset(y = animatedVerticalOffset)
                    ) {
                        MovieCard(
                            mockMovie = mockMovies[page],
                            onMovieClick = onMovieClick,
                        )
                    }
                }
            }

            // Title text below each poster
            Box(
                modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp)
            ) {
                Text(
                    text = mockMovies[pagerState.currentPage].title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 26.dp),
                    style = TextStyle(color = if (isDarkMode) Color.White else Color.Black), // Adjust text color based on theme                    maxLines = 1,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Rating row with star icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = mockMovies[pagerState.currentPage].vote_average.format(1),
                    fontSize = 18.sp,
                    style = TextStyle(color = if (isDarkMode) Color.White else Color.Black), // Adjust text color based on theme                    fontSize = 15.sp
                )
            }
        }
    }
}