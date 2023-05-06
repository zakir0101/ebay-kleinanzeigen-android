package com.example.ebaykleinanzeigenzakir.window.setting

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ebaykleinanzeigenzakir.*
import com.example.ebaykleinanzeigenzakir.ui.theme.EbayKleinanzeigenZakirTheme

@Composable
fun SettingWindow(viewModel: EbayViewModel) {
    val settingState by viewModel.settingState.collectAsState()

    WhiteCardWithPadding(
        Modifier
            .padding(10.dp)
            .fillMaxHeight()) {
        LargeBolderTitleText(text = "Einstellung")
        Spacer(modifier = Modifier.height(20.dp))
        MediumBoldTitleText(text = "Übersicht deine Aktivitäte")
        Spacer(modifier = Modifier.height(10.dp))
        DarkCardWithPadding {
            LargeBoldTitleText(text = "Mein Konto")
            Spacer(modifier = Modifier.height(5.dp))
            if (settingState != null) {
                LargeLightText(text = settingState!!.add_num_online.trim())
                LargeLightText(text = settingState!!.add_num_total.trim())
            }else{
                EbayPlaceholder(Modifier.fillMaxWidth(0.6f))
                EbayPlaceholder(Modifier.fillMaxWidth(.9f ))
            }
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
            SettingWindow(viewModel(factory = EbayViewModel.Factory))

        }

    }
}

