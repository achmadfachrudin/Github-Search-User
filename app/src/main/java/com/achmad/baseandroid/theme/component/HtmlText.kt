package com.achmad.baseandroid.theme.component

import android.graphics.Typeface
import android.text.Spanned
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat

private const val URL_TAG = "url_tag"

/**
 * Format a styled string.
 *
 * @param text the text that will be formatted
 * @return the formatted string data
 */
@Composable
fun annotatedHtmlString(text: String): AnnotatedString {
    val spanned = remember(text) {
        HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
    return buildAnnotatedString(spanned)
}

@Composable
private fun buildAnnotatedString(spanned: Spanned) = remember(spanned) {
    buildAnnotatedString {
        append(spanned.toString())
        spanned.getSpans(0, spanned.length, Any::class.java).forEach { span ->
            val start = spanned.getSpanStart(span)
            val end = spanned.getSpanEnd(span)
            when (span) {
                is BulletSpan -> {
                    // not supported yet on Compose
                }
                is StyleSpan -> {
                    when (span.style) {
                        Typeface.BOLD ->
                            addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                        Typeface.ITALIC ->
                            addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                        Typeface.BOLD_ITALIC ->
                            addStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic
                                ),
                                start,
                                end
                            )
                    }
                }
                is UnderlineSpan -> {
                    addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
                }
                is ForegroundColorSpan -> {
                    addStyle(SpanStyle(color = Color(span.foregroundColor)), start, end)
                }
                is RelativeSizeSpan -> addStyle(
                    SpanStyle(fontSize = span.sizeChange.em),
                    start,
                    end
                )
                is StrikethroughSpan -> addStyle(
                    SpanStyle(textDecoration = TextDecoration.LineThrough),
                    start,
                    end
                )
                is SuperscriptSpan -> addStyle(
                    SpanStyle(baselineShift = BaselineShift.Superscript),
                    start,
                    end
                )
                is SubscriptSpan -> addStyle(
                    SpanStyle(baselineShift = BaselineShift.Subscript),
                    start,
                    end
                )
                is URLSpan -> {
                    addStyle(
                        style = SpanStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        ),
                        start = start,
                        end = end
                    )
                    addStringAnnotation(
                        tag = URL_TAG,
                        annotation = span.url,
                        start = start,
                        end = end
                    )
                }
            }
        }
    }
}

/**
 * Load a styled string resource with formatting.
 *
 * @param id the resource identifier
 * @param formatArgs the format arguments
 * @return the string data associated with the resource
 */
@Composable
fun annotatedStringResource(@StringRes id: Int, vararg formatArgs: Any): AnnotatedString {
    val text = stringResource(id, *formatArgs)
    return annotatedHtmlString(text)
}

/**
 * Load a styled string resource with formatting.
 *
 * @param id the resource identifier
 * @return the string data associated with the resource
 */
@Composable
fun annotatedStringResource(@StringRes id: Int): AnnotatedString {
    val text = stringResource(id)
    return annotatedHtmlString(text)
}

@Composable
fun createOrderedListAnnotatedString(@StringRes id: Int, vararg formatArgs: Any): AnnotatedString {
    val text = stringResource(id, *formatArgs)
    return createOrderedListAnnotatedString(text)
}

@Composable
fun createOrderedListAnnotatedString(text: String): AnnotatedString {
    val orderedString = text.substringBefore("</ol>")
        .removePrefix("<ol>")

    val orderedListString = orderedString.splitToSequence("</li>")
        .filter { it.isNotBlank() }
        .map { it.replace("<li>", "").trimIndent() }
        .toList()

    return remember(orderedListString) {
        buildAnnotatedString {
            orderedListString.forEachIndexed { index, content ->
                val startIndex = length

                val numberLabel = "${(index + 1)}. "
                val spannedContent = HtmlCompat.fromHtml(
                    numberLabel + content,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                append(spannedContent.toString())

                val endIndex = length

                spannedContent.getSpans(0, spannedContent.length, Any::class.java)
                    .forEach { span ->
                        val start = spannedContent.getSpanStart(span) + startIndex
                        val end = spannedContent.getSpanEnd(span) + startIndex
                        when (span) {
                            is StyleSpan -> when (span.style) {
                                Typeface.BOLD -> {
                                    addStyle(
                                        style = SpanStyle(fontWeight = FontWeight.Bold),
                                        start = start,
                                        end = end
                                    )
                                }
                                Typeface.ITALIC -> {
                                    addStyle(
                                        style = SpanStyle(fontStyle = FontStyle.Italic),
                                        start = start,
                                        end = end
                                    )
                                }
                                Typeface.BOLD_ITALIC ->
                                    addStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight.Bold,
                                            fontStyle = FontStyle.Italic
                                        ),
                                        start = start,
                                        end = end
                                    )
                            }
                            is UnderlineSpan -> {
                                addStyle(
                                    style = SpanStyle(
                                        textDecoration = TextDecoration.Underline
                                    ),
                                    start = start,
                                    end = end
                                )
                            }
                            is ForegroundColorSpan -> {
                                addStyle(
                                    style = SpanStyle(color = Color(span.foregroundColor)),
                                    start = start,
                                    end = end
                                )
                            }
                        }
                    }
                addStyle(
                    style = ParagraphStyle(
                        lineHeight = 21.sp,
                        textIndent = TextIndent(firstLine = 0.sp, restLine = 16.sp)
                    ),
                    start = startIndex,
                    end = endIndex
                )
            }
        }
    }
}

@Composable
fun createUnorderedListAnnotatedString(text: String, textStyle: TextStyle): AnnotatedString {
    val copyText = text.replace("<li>", "<li>\u2022 ")

    val spanned = remember(copyText) {
        HtmlCompat.fromHtml(copyText, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    return buildAnnotatedStringWithStyle(spanned, textStyle)
}

@Composable
private fun buildAnnotatedStringWithStyle(spanned: Spanned, textStyle: TextStyle) =
    remember(spanned) {
        val spanStyle = SpanStyle(
            fontFamily = textStyle.fontFamily,
            fontWeight = textStyle.fontWeight,
            fontSize = textStyle.fontSize,
            color = textStyle.color
        )
        buildAnnotatedString {
            append(spanned.toString())
            spanned.getSpans(0, spanned.length, Any::class.java).forEach { span ->
                val start = spanned.getSpanStart(span)
                val end = spanned.getSpanEnd(span)
                when (span) {
                    is BulletSpan -> {
                        // not supported yet on Compose
                    }
                    is StyleSpan -> {
                        when (span.style) {
                            Typeface.BOLD ->
                                addStyle(
                                    spanStyle.copy(fontWeight = FontWeight.Bold),
                                    start,
                                    end
                                )
                            Typeface.ITALIC ->
                                addStyle(spanStyle.copy(fontStyle = FontStyle.Italic), start, end)
                            Typeface.BOLD_ITALIC ->
                                addStyle(
                                    spanStyle.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Italic
                                    ),
                                    start,
                                    end
                                )
                        }
                    }
                    is UnderlineSpan -> {
                        addStyle(
                            spanStyle.copy(textDecoration = TextDecoration.Underline),
                            start,
                            end
                        )
                    }
                    is ForegroundColorSpan -> {
                        addStyle(spanStyle.copy(color = Color(span.foregroundColor)), start, end)
                    }
                    is RelativeSizeSpan -> addStyle(
                        spanStyle.copy(fontSize = span.sizeChange.em),
                        start,
                        end
                    )
                    is StrikethroughSpan -> addStyle(
                        spanStyle.copy(textDecoration = TextDecoration.LineThrough),
                        start,
                        end
                    )
                    is SuperscriptSpan -> addStyle(
                        spanStyle.copy(baselineShift = BaselineShift.Superscript),
                        start,
                        end
                    )
                    is SubscriptSpan -> addStyle(
                        spanStyle.copy(baselineShift = BaselineShift.Subscript),
                        start,
                        end
                    )
                    is URLSpan -> {
                        addStyle(
                            style = spanStyle.copy(
                                color = Color.Blue,
                                textDecoration = TextDecoration.Underline
                            ),
                            start = start,
                            end = end
                        )
                        addStringAnnotation(
                            tag = URL_TAG,
                            annotation = span.url,
                            start = start,
                            end = end
                        )
                    }
                }
            }
        }
    }
