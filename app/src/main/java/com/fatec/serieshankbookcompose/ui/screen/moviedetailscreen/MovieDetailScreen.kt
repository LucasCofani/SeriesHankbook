package com.fatec.serieshankbookcompose.ui.screen.moviedetailscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import java.lang.Float

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun MovieDetailScreen(
    navController: NavController,
    id: Int,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val detailSerie by remember {
        viewModel.detail
    }
    viewModel.getDetail(id)
    if (detailSerie != null) {
        val id = detailSerie?.id!!
        val nome = detailSerie?.title!!
        val nomeOriginal = detailSerie?.original_title!!
        val imgSRC = "https://image.tmdb.org/t/p/original/" + detailSerie?.poster_path!!
        val lancamento = "" + detailSerie?.release_date!!
        val nota = "" + detailSerie?.vote_average!!
        val status = detailSerie?.status!!
        val sobre = detailSerie?.overview!!
        val genero = detailSerie?.genres!!
        val network = detailSerie?.production_companies!!

        val scrollState = rememberScrollState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .verticalScroll(scrollState)
        ) {
            GlideImage(
                imageModel = imgSRC,
                contentScale = ContentScale.FillHeight,
                contentDescription = nome,
                modifier = Modifier
                    .height(600.dp)
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = Float.min(1f, 1 - (scrollState.value / 2000f))
                        translationY = -scrollState.value * 0.1f
                    }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 500.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                    .background(MaterialTheme.colors.primary)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = nome,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Justify,
                        fontSize = 28.sp
                    )
                    Text(text = sobre, textAlign = TextAlign.Justify, fontSize = 16.sp)

                    Text(text = "Produzido por:")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        network.forEach {
                            GlideImage(
                                imageModel = "https://image.tmdb.org/t/p/w185/" + it.logo_path,
                                contentScale = ContentScale.Fit,
                                contentDescription = it.name,
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(80.dp)
                                    .padding(end = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}