package com.example.weecompanyjctest

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.weecompanyjctest.model.Country
import com.example.weecompanyjctest.ui.theme.WeeCompanyJCTestTheme
import java.util.*

@Composable
fun DialogScreen(
    country: Country,
    showDialog: Boolean,
    dismissDialog: () -> Unit
){
    if(showDialog){
        AlertDialog(
            onDismissRequest = { dismissDialog() },
            title = null,
            text = null,
            buttons = {
                Column(
                    modifier = Modifier.padding(50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Click, ${country.country}!",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.gotha_pro_bol)),
                            fontSize = 15.sp
                        ),
                        color = Color(0xFF057CB7)
                    )

                    Image(
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                            .aspectRatio(1f / 1f),
                        painter = rememberImagePainter(
                            data = R.drawable.img_like
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                    Text(
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 50.dp),
                        text = "Has dado clic en el pais de ${country.country}",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.gotham_book_regular)),
                            fontSize = 12.sp
                        ),
                        color = Color(0xFF057CB7)
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DislogScreenPreview() {
    WeeCompanyJCTestTheme {
        DialogScreen(
            country = Country(UUID.randomUUID(), ""),
            showDialog = true,
            dismissDialog = {}
        )
    }
}