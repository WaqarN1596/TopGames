package com.waqar.topgames

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import com.waqar.topgames.ui.theme.TopGamesTheme

@Composable
fun HyperlinkText(
    fullText: String,
    theWebsite: String,
    linkPart: String,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    TopGamesTheme {
        val annotatedString = buildAnnotatedString {
            append(fullText)

            val start = fullText.indexOf(linkPart)
            val end = start + fullText.length
            addStyle(
                SpanStyle(
                    color = Color(4278226658),
                    textDecoration = TextDecoration.Underline,
                    fontSize = fontSize
                ),
                start,
                end
            )
            addStringAnnotation(
                "url",
                annotation = theWebsite,
                start,
                end
            )
        }
        val uriHandler = LocalUriHandler.current
        ClickableText(text = annotatedString,
            onClick = { offset ->
                val uri =
                    annotatedString.getStringAnnotations("url", offset, offset).firstOrNull()?.item
                if (uri != null) {
                    uriHandler.openUri(uri)
                }
            }
        )

    }
}

val String.color
    get() = Color(android.graphics.Color.parseColor(this))

