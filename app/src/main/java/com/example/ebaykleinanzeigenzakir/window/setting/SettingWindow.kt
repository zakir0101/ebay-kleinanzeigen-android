package com.example.ebaykleinanzeigenzakir.window.setting

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ebaykleinanzeigenzakir.*
import com.example.ebaykleinanzeigenzakir.ui.theme.EbayKleinanzeigenZakirTheme

@Composable
fun SettingWindow() {
    WhiteCardWithPadding(Modifier.padding(10.dp).fillMaxHeight()) {
        LargeBolderTitleText(text = "Einstellung")
        Spacer(modifier = Modifier.height(20.dp))
        MediumBoldTitleText(text = "Übersicht deine Aktivitäte")
        Spacer(modifier = Modifier.height(10.dp))
        DarkCardWithPadding {
            LargeBoldTitleText(text = "Mein Konto")
            Spacer(modifier = Modifier.height(5.dp))

            LargeLightText(text = "Du hast aktuell 1 Anzeige online." )
            LargeLightText(text = "Du hast in den letzten 30 Tagen 59 Anzeigen aufgegeben." )

        }
    }

}


@Preview
@Composable
fun ShoPublishWindow() {

    EbayKleinanzeigenZakirTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SettingWindow()

        }

    }
}

