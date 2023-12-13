package com.freez.animatedcarousel.model

data class MockData(
    val title: String,
    val poster_path: String,
    val backdrop_path: String,
    val vote_average: Double
)

// Mock data for testing
val mockMovies = listOf(
    MockData(
        "Wonka",
        "https://image.tmdb.org/t/p/w500/xAKI0O5hUXxbmHRBNg72IzFQ06a.jpg",
        "https://image.tmdb.org/t/p/w500/yOm993lsJyPmBodlYjgpPwBjXP9.jpg",
        7.1224
    ),

    MockData(
        "Oppenheimer",
        "https://image.tmdb.org/t/p/w500/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg",
        "https://image.tmdb.org/t/p/w500/fm6KqXpk3M2HVveHwCrBSSBaO0V.jpg",
        8.1
    ),
    MockData(
        "The Marvels",
        "https://image.tmdb.org/t/p/w500/Ag3D9qXjhJ2FUkrlJ0Cv1pgxqYQ.jpg",
        "https://image.tmdb.org/t/p/w500/feSiISwgEpVzR1v3zv2n2AU4ANJ.jpg",
        6.5
    ),
    MockData(
        "TAYLOR SWIFT | THE ERAS TOUR",
        "https://image.tmdb.org/t/p/w500/a5EreVlyB9fXzZ2Rf9ugOLrW5YI.jpg",
        "https://image.tmdb.org/t/p/w500/wVJG3u5Ru9tInxYiTCrJr4MpJ7Z.jpg",
        8.3
    ),
)
