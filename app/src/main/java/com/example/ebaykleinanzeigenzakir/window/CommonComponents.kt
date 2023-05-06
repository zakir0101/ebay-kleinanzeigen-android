package com.example.ebaykleinanzeigenzakir


import com.google.accompanist.placeholder.placeholder
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ebaykleinanzeigenzakir.window.Toggler
import com.example.ebaykleinanzeigenzakir.window.search.FilterText
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade



@Composable
fun WhiteCardWithPadding(modifier: Modifier = Modifier, function: @Composable () -> Unit) {
    Card(
        modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RectangleShape
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            function()
        }
    }
}


@Composable
fun DarkCardWithPadding(modifier: Modifier = Modifier, function: @Composable () -> Unit) {
    Card(
        modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                3.dp
            )
        ),
        shape = RectangleShape
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            function()
        }
    }
}


@Composable
fun OutlinedPrimaryButtonWithIcon(text: String, icon: ImageVector, modifier: Modifier = Modifier,onClick: Toggler = {}) {
    OutlinedButton(
        onClick = { onClick()},
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(14),
        modifier = modifier
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text, color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )

    }
}


@Composable
fun PrimaryButtonWithIcon(text: String, icon: ImageVector, modifier: Modifier = Modifier,onClick: Toggler={}) {
    Button(
        onClick = { },
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(14),
        contentPadding = PaddingValues(horizontal = 4.dp),
        modifier = modifier
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = text, color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleSmall
        )

    }
}


@Composable
fun PrimaryButton(
    text: String,
    onClick: Toggler = {},
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    isDisabled: () -> Boolean = { false}
) {
    val enabled = ! isDisabled()
    Button(
        onClick = { onClick() },
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(14),
        contentPadding = PaddingValues(horizontal = 4.dp),
        modifier = modifier
    ) {


        Text(
            text = text, color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleSmall, modifier = textModifier
        )

    }
}


@Composable
fun EbayDropDown(
    suggestions: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(suggestions[0]) }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
    )
    {

        FilterText(text = selectedText,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                })
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                .background(MaterialTheme.colorScheme.surface),
        ) {
            suggestions.forEach { label ->
                androidx.compose.material.DropdownMenuItem(
                    onClick = {
                        selectedText = label
                        expanded = false
                    }) {
                    Text(
                        modifier = Modifier,
                        text = label, color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

            }
        }
    }
}


@Composable
fun EbaySwitch(checked: Boolean, setChecked: () -> Unit) {

    Switch(
        checked = checked,
        onCheckedChange = { setChecked() },
        Modifier.scale(0.9f),
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
            checkedTrackColor = MaterialTheme.colorScheme.primary,
            checkedBorderColor = MaterialTheme.colorScheme.onPrimary,
            uncheckedThumbColor = MaterialTheme.colorScheme.surface,
            uncheckedTrackColor = MaterialTheme.colorScheme.onBackground,
            uncheckedBorderColor = MaterialTheme.colorScheme.surface
        )
    )

}


@Composable
fun FilterHeader(onClick: Toggler) {
    Row(
        Modifier
            .height(52.dp)
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            Modifier
                .clickable { onClick() }
                .fillMaxHeight()
                .padding(horizontal = 0.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Check, contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "Ferting",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyMedium
            )

        }
        Divider(
            Modifier
                .fillMaxHeight()
                .width(1.dp)
                .padding(0.dp, 5.dp), color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = "3.000 Ergebnisse",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodySmall
        )
    }

}


@Composable
fun ConversationImage(size: Int = 55, conversation: Conversation) {
    Box(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                RoundedCornerShape(50)
            )
            .size(size.dp)
        , contentAlignment = Alignment.Center


    ) {
        var imageLoaded by remember { mutableStateOf(false) }
        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(conversation.adImage?.replace("{imageId}","2") ).crossfade(true).build()
            , contentDescription = null
            ,modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(50)),
            contentScale = ContentScale.Crop
            , onSuccess = {imageLoaded = true}

        )
        if (!imageLoaded) {
            Icon(
                contentDescription = null,
                modifier = Modifier,
                tint = MaterialTheme.colorScheme.onBackground,
                imageVector = Icons.Default.PhotoCamera
            )
        }
    }
}


@Composable
fun ConversationImagePlaceholder(size: Int = 55) {
    Box(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                RoundedCornerShape(50)
            )
            .size(size.dp)
        , contentAlignment = Alignment.Center


    ) {
        Icon(
                contentDescription = null,
                modifier = Modifier,
                tint = MaterialTheme.colorScheme.onBackground,
                imageVector = Icons.Default.PhotoCamera
            )

    }
}

@Composable
fun HisInitialCircle(text: String = "k", size: String = "sm", modifier: Modifier = Modifier) {
    val bgColor = if (size == "sm") MaterialTheme.colorScheme.onBackground
    else MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp)

    val textColor = if (size == "sm") MaterialTheme.colorScheme.surface
    else MaterialTheme.colorScheme.onSurface
    Row(
        modifier
            .size(if (size == "sm") 45.dp else 55.dp)
            .background(
                bgColor,
                RoundedCornerShape(50)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text, style = when (size) {
                "sm" -> MaterialTheme.typography.bodyLarge
                "lg" -> MaterialTheme.typography.titleLarge
                else -> {
                    MaterialTheme.typography.bodyLarge
                }
            },
            color = textColor
        )

    }
}


@Composable
fun MediumBoldTitle(text: String) {
    Text(
        text, Modifier, MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun LargeBolderTitleText(text: String) {
    val titleLg = MaterialTheme.typography.titleLarge
    val lgFontWeight = (titleLg.fontWeight?.weight?.times(1.3))
    Text(
        text = text,
        style = titleLg.copy(fontWeight = FontWeight(lgFontWeight?.toInt()!!)),
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun LargeBoldTitleText(text: String) {
    val titleLg = MaterialTheme.typography.titleLarge
    Text(
        text = text,
        style = titleLg,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun MediumBoldTitleText(text: String) {
    val titleLg = MaterialTheme.typography.titleMedium
    Text(
        text = text,
        style = titleLg,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun SmallBoldTitleText(text: String) {
    val titleLg = MaterialTheme.typography.titleMedium
    Text(
        text = text,
        style = titleLg,
        color = MaterialTheme.colorScheme.onSurface
    )
}


@Composable
fun LargeDarkText(text: String) {
    Text(
        text, color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun LargeLightText(text: String) {
    Text(
        text, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        style = MaterialTheme.typography.bodyLarge
    )
}


@Composable
fun EbayPlaceholder(modifier: Modifier) {
    Text(
        "", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier.placeholder(visible = true,color=MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    highlight = PlaceholderHighlight.fade(MaterialTheme.colorScheme.surface))
    )
}


@Composable
fun SmallDarkText(text: String) {
    Text(
        text, color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.labelLarge
    )
}


@Composable
fun SmallDarkTextPlaceholder(modifier: Modifier) {
    Text(
        "", color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.labelLarge
        , modifier = modifier.placeholder(visible = true,color=MaterialTheme.colorScheme.onSurface,
            highlight = PlaceholderHighlight.fade(MaterialTheme.colorScheme.surface))

    )
}

@Composable
fun SmallLightIcon(icon: ImageVector) {
    Icon(
        imageVector = icon, contentDescription = null,
        tint = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.size(18.dp)
    )
}

@Composable
fun MediumLightText(text: String) {
    Text(
        text, color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center
    )
}

@Composable
fun SmallLightText(text: String) {
    Text(
        text, color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center
    )
}

@Composable
fun SmallLightLabel(text: String) {
    Text(
        text, color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.Center
    )
}

