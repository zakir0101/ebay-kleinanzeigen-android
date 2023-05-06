package com.example.ebaykleinanzeigenzakir.window.add

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ebaykleinanzeigenzakir.EbayViewModel
import com.example.ebaykleinanzeigenzakir.PrimaryButton
import com.example.ebaykleinanzeigenzakir.window.navigateBack
import com.example.ebaykleinanzeigenzakir.window.publish.EbayInputField

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SendMessage(navController: NavHostController, viewModel: EbayViewModel) {
    val activeAdd = viewModel.addWindowState.collectAsState()
    Column(
        Modifier
            .fillMaxWidth()
            .padding(15.dp), verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {


        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {

        }

        val response by viewModel.messageWindowResoponse.collectAsState()
        if(response != null && response!!.status.lowercase()=="ok"){
            Toast.makeText(LocalContext.current,"Nachricht erfolgreich gesendet",Toast.LENGTH_SHORT).show()
            viewModel.addWindowSendMessageResponse.value = null
            navController.navigateBack()
        }

        if(response != null && response!!.status.lowercase()!="ok"){
            Toast.makeText(LocalContext.current,"leider könnte die Nachricht nicht gesendet werden",Toast.LENGTH_SHORT).show()
            viewModel.addWindowSendMessageResponse.value = null
        }
        EbayInputField(
            modifier = Modifier.fillMaxWidth(),
            label = "Nachricht", numLines = 3,
            value = viewModel.addWindowMessage
        ) { viewModel.addWindowMessage = it }

        EbayInputField(
            modifier = Modifier.fillMaxWidth(),
            label = "Nachricht", numLines=1,
            value = viewModel.addWindowContactName
        ) { viewModel.addWindowContactName = it }


        PrimaryButton(
            text = if (!viewModel.addWindowSending) "Senden" else "Nachricht wird gesendet...",
            onClick = { viewModel.sendMessageFromAddPage()  },
            modifier = Modifier.fillMaxWidth(),
            textModifier = Modifier.padding(vertical = 10.dp)
        ) {  viewModel.addWindowMessage.isBlank() || viewModel.addWindowContactName.isBlank() ||
                    viewModel.addWindowSending  }


    }
}
