package com.example.weecompanyjctest

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.*
import com.example.weecompanyjctest.model.Country
import com.example.weecompanyjctest.ui.theme.ColorPrimary
import com.example.weecompanyjctest.ui.theme.WeeCompanyJCTestTheme
import java.util.*


@Composable
fun ListCountryScreen(
    navController: NavController,
    viewModel: ListCountryScreenViewModel = hiltViewModel()
) {

    val openDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val countrySelect = remember { mutableStateOf(Country(UUID.randomUUID(), "")) }
    val countriesList by viewModel.getNews().observeAsState(initial = emptyList())

    ListCountryScreen(navController, countriesList) {
        countrySelect.value = it
        openDialog.value = true
    }

    DialogScreen(country= countrySelect.value, showDialog = openDialog.value, dismissDialog = {
        openDialog.value = false
        val uri: String =
            java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", MainActivity.lat, MainActivity.lng)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context.startActivity(intent)
    })
}

@Composable
fun ListCountryScreen(
    navController: NavController,
    countries: List<Country>,
    openDialog: (country: Country) -> Unit
) {

    val loandingList by rememberLottieComposition(LottieCompositionSpec.Asset("loandingList.json"))
    val progress by animateLottieCompositionAsState(
        loandingList,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        restartOnPlay = false
    )

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = ColorPrimary,
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        text = "Wee Company ®",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.gotha_pro_bol)),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            lineHeight = 16.sp,
                            letterSpacing = 0.4.sp
                        ),
                        color = Color(0xFFFFFFFF)
                    )
                }
            )
        }
    ) {

        val listState = rememberLazyListState()
        var selectedIndex by remember{mutableStateOf(UUID.randomUUID())}

        if (countries.isEmpty()){
            LottieAnimation(
                loandingList,
                progress,
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
            )
        }else{
            LazyColumn(state = listState) {
                items(countries) { country ->
                    Card(
                        shape = RoundedCornerShape(0.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = country.id == selectedIndex,
                                onClick = {
                                    if (selectedIndex != country.id)
                                        selectedIndex = country.id else selectedIndex =
                                        UUID.randomUUID()
                                    openDialog(country)
                                }
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 12.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp),
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                text = country.country,
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.gotham_book_regular)),
                                    fontSize = 12.sp
                                ),
                                color = Color(0xFF707070)
                            )
                            if (country.id == selectedIndex) {
                                Image(
                                    modifier = Modifier
                                        .width(22.dp)
                                        .height(22.dp)
                                        .aspectRatio(1f / 1f),
                                    painter = rememberImagePainter(
                                        data = R.drawable.img_new_ok
                                    ),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillWidth
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    WeeCompanyJCTestTheme {
        ListCountryScreen(
            navController = rememberNavController(),
            countries = arrayListOf(
                Country(UUID.randomUUID(), "")
            ),
            openDialog = {}
        )
    }
}