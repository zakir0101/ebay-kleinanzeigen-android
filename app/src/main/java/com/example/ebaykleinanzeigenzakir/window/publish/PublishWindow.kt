@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ebaykleinanzeigenzakir.window.publish

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.example.ebaykleinanzeigenzakir.City
import com.example.ebaykleinanzeigenzakir.EbayViewModel
import com.example.ebaykleinanzeigenzakir.PrimaryButton
import com.example.ebaykleinanzeigenzakir.WhiteCardWithPadding
import kotlinx.coroutines.flow.update

@Composable
fun PublishWindow(navController: NavHostController, viewModel: EbayViewModel) {
    if (viewModel.publishCurrent == "form") {
        PublishForm(viewModel)
    } else if (viewModel.publishCurrent == "waiting") {
        WaitingWindow(navController, viewModel)
    } else if (viewModel.publishCurrent == "error") {
        ErrorWindow()
    }
}

@Composable
fun ErrorWindow() {

}

@Composable
fun WaitingWindow(navController: NavHostController, viewModel: EbayViewModel) {

}

@Composable
fun PublishForm(viewModel: EbayViewModel) {
    Column(
        Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val extPadding = 1
        val modifier = Modifier.fillMaxWidth()

        val formState by viewModel.publishFormState.collectAsState()

        WhiteCardWithPadding(modifier = Modifier.padding(extPadding.dp)) {
            val categories = listOf<String>("Nachhlife", "andere Dienstleistung", "zu Verschenken")
            val setTitle =
                { s: String -> viewModel.publishFormState.update { data -> data.copy(title = s) } }
            EbayInputField(
                Modifier.fillMaxWidth(),
                label = "Title",
                value = formState.title,
                setValue = setTitle
            )
            InputHint(text = "Gib hier deinen Title ein (min 10 Zeichen)")
            EbayInputDropdown(Modifier.fillMaxWidth(), label = "Kategorie", list = categories)
        }

        WhiteCardWithPadding(Modifier.padding(extPadding.dp)) {

            val setDescription =
                { s: String -> viewModel.publishFormState.update { data -> data.copy(description = s) } }
            EbayInputField(
                Modifier.fillMaxWidth(),
                label = "Beschreibung",
                numLines = 5, value = formState.description,
                setValue = setDescription
            )
            InputHint(text = "Beschreibe heir dein Inserat (min 10 Zeichen, max 4000)")
        }

        WhiteCardWithPadding(Modifier.padding(extPadding.dp)) {
            val priceType = listOf<String>("Fest Preis", "VB", "zu Verschenken")

            Row(Modifier, horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                val setPrice =
                    { s: String ->
                        if (s.toDoubleOrNull() != null) {
                            viewModel.publishFormState.update { data -> data.copy(price = s) }
                        }
                        }
                EbayInputField(
                    Modifier.weight(1f),
                    label = "Preis",
                    value = formState.price,
                    setValue = setPrice
                )
                EbayInputDropdown(Modifier.weight(1f), label = "Preistype", priceType)
            }
        }

        WhiteCardWithPadding(Modifier.padding(extPadding.dp)) {
            val cities by viewModel.citiesList.collectAsState()
            val citiesOrEmpty = if (cities != null) cities!! else listOf<City>()


            var cityName by remember { mutableStateOf("") }
            val onSelectCity = { c: City ->
                val zip = c.name.split(" ")[0].trim()
                cityName = c.name.split(" ")[1].trim()
                c.zip = zip
                viewModel.formDataCity.value = c
                viewModel.citySearch = zip
            }

            val setCityAndSearch = { s: String ->
                if (s.toIntOrNull() != null ) {
                    viewModel.citySearch = s
                    viewModel.getCities()
                }
            }

            Row(Modifier, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                EbayAutoComplete(
                    Modifier.weight(0.3f), label = "PLZ",
                    list = citiesOrEmpty, value = viewModel.citySearch,
                    setValueAndSearch = setCityAndSearch, onSelect = onSelectCity
                )


                EbayInputField(Modifier.weight(0.7f),
                    label = "Stadt - Ort",
                    readOnly = true,
                    disabled = true,
                    value = cityName,
                    setValue = {})
            }

            val setName:(String)->Unit = { s: String ->
                    viewModel.publishFormState.update { state ->
                        state.copy(contact_name = s)
                    }
            }
            EbayInputField(
                Modifier.fillMaxWidth(),
                label = "Contact Name",
                value = formState.contact_name,
                setValue = setName
            )

        }
        Text(viewModel.publishFormError,color=Color.Red,style=MaterialTheme.typography.bodyMedium)
        PrimaryButton(
            onClick = {viewModel.publishAdd()} ,
            text = "Anzeige aufgeben",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), textModifier = Modifier.padding(vertical = 16.dp)
        ) { return@PrimaryButton false }

    }
}

@Composable
fun EbayInputField(
    modifier: Modifier,
    label: String,
    numLines: Int = 1,
    readOnly: Boolean = false,
    disabled: Boolean = false,
    value: String,
    setValue: (String) -> Unit

) {

    OutlinedTextField(
        value = value, onValueChange = { setValue(it) },
        modifier = modifier, readOnly = readOnly, label = { TextFieldLabel(label) },
        colors = getTextFieldColors(), shape = RoundedCornerShape(10),
        maxLines = numLines, minLines = numLines, singleLine = numLines == 1,
        enabled = !disabled
    )

}


@Composable
fun EbayInputDropdown(modifier: Modifier, label: String, list: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    val onDismissRequest2 = { expanded = false }
    var value by rememberSaveable { mutableStateOf("") }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    Box(
        modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)

    ) {

        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            modifier = modifier
                .onFocusChanged { if (it.isFocused) expanded = true }
                .onGloballyPositioned {
                    textfieldSize = it.size.toSize()
                }, label = { TextFieldLabel(label) },
            colors = getTextFieldColors(), shape = RoundedCornerShape(10),
            maxLines = 1, minLines = 1,

            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,

                    )
            }
        )

        val setValue = { s: String -> value = s }
        val setExpanded = { b: Boolean -> expanded = b }
        EbayDropDownMenu(expanded, onDismissRequest2, textfieldSize, list, setValue, setExpanded)

    }
}


@Composable
fun EbayDropDownMenu(
    expanded: Boolean,
    onDismissRequest2: () -> Unit,
    textfieldSize: Size,
    list: List<String>,
    onSelect: (String) -> Unit,
    setExpanded: (Boolean) -> Unit
) {

    DropdownMenu(
        expanded = expanded,
        properties = PopupProperties(
            focusable = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        onDismissRequest = onDismissRequest2,
        modifier = Modifier
            .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
            .background(MaterialTheme.colorScheme.surface)

    ) {
        list.forEach { s ->
            DropdownMenuItem(
                onClick = { onSelect(s);setExpanded(false) },
                text = { DropDownItemText(s) },
                colors = getMenuItemsColor()
            )
        }


    }

}


@Composable
fun EbayAutoComplete(
    modifier: Modifier,
    label: String,
    list: List<City>,
    value: String,
    setValueAndSearch: (String) -> Unit,
    onSelect: (City) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val onDismissRequest2 = { expanded = false }

//    var value by rememberSaveable { mutableStateOf("") }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    Box(
        modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)

    ) {

        OutlinedTextField(
            value = value,
            onValueChange = {
                setValueAndSearch(it)
                if (it.length > 0)
                    expanded = true
            },
            modifier = modifier
                .onFocusChanged {
                    if (!it.isFocused) {
                        onDismissRequest2()
                    }
                }
                .onGloballyPositioned {
                    textfieldSize = it.size.toSize()
                }, label = { TextFieldLabel(label) },
            colors = getTextFieldColors(), shape = RoundedCornerShape(10),
            maxLines = 1, minLines = 1
        )
        val setExpanded = { b: Boolean -> expanded = b }
        EbayCityDropDownMenu(
            expanded,
            onDismissRequest2,
            textfieldSize,
            list,
            onSelect,
            setExpanded
        )

    }
}

@Composable
fun EbayCityDropDownMenu(
    expanded: Boolean,
    onDismissRequest2: () -> Unit,
    textfieldSize: Size,
    list: List<City>,
    onSelect: (City) -> Unit,
    setExpanded: (Boolean) -> Unit
) {

    DropdownMenu(
        expanded = expanded,
        properties = PopupProperties(
            focusable = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        onDismissRequest = onDismissRequest2,
        modifier = Modifier
            .width(with(LocalDensity.current) { textfieldSize.width.toDp()*3 })
            .background(MaterialTheme.colorScheme.surface)

    ) {
        list.forEach { city ->
            DropdownMenuItem(
                onClick = { onSelect(city);setExpanded(false) },
                text = { DropDownItemText(city.name) },
                colors = getMenuItemsColor()
            )
        }


    }

}

@Composable
fun DropDownItemText(label: String) {

    androidx.compose.material3.Text(
        modifier = Modifier,
        text = label, color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.labelLarge
    )

}


@Composable
fun InputHint(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(start = 20.dp)
    )
}

@Composable
fun TextFieldLabel(label: String) {
    Text(
        label,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.background(Color.Transparent)

    )
}

@Composable
fun getTextFieldColors(): TextFieldColors {
    return TextFieldDefaults.outlinedTextFieldColors(
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
        cursorColor = MaterialTheme.colorScheme.primary,
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)

    )
}


@Composable
fun getMenuItemsColor(): MenuItemColors {
    return MenuDefaults.itemColors(
        textColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
    )
}


//@Preview
//@Composable
//fun ShoPublishWindow() {
//    val navController = rememberNavController()
//    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
//    EbayKleinanzeigenZakirTheme() {
//        androidx.compose.material.Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            PublishWindow(navController, viewModel)
//
//        }
//
//    }
//}
//
