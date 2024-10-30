package sp.bvantur.tasky.core.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val Black = Color(0xFF000000)
private val Light = Color(0xFFEEF6FF)
private val Light2 = Color(0xFFF2F3F7)
private val LightBlue = Color(0xFFB7C6DE)
private val White = Color(0xFFFFFFFF)
private val Bittersweet = Color(0xFFFF7272)
private val Silver = Color(0xFFBEBEBE)
private val DarkGray = Color(0xFF5C5D5A)
private val LightGreen = Color(0xFFCAEF45)

@Suppress("UnusedPrivateProperty")
private val Gray = Color(0xFFA9B4BE)

@Suppress("UnusedPrivateProperty")
private val DarkGreen = Color(0xFF06572A)

@Suppress("UnusedPrivateProperty")
private val Green = Color(0xFF279F70)

@Suppress("UnusedPrivateProperty")
private val Brown = Color(0xFF40492D)

@Suppress("UnusedPrivateProperty")
private val Orange = Color(0xFFFDEFA8)

internal val ColorScheme.textFieldPlaceholder: Color
    get() = Silver

internal val ColorScheme.eventChoreType: Color
    get() = LightGreen

internal val ColorScheme.onEventType: Color
    get() = Black

internal val ColorScheme.eventChoreTitleType: Color
    get() = DarkGray

internal val ColorScheme.appBarAction: Color
    get() = Green

val TaskyColorPalette = lightColorScheme(
    primary = Black,
    onPrimary = Light,
    secondary = LightBlue,
    tertiary = Light2,
    onTertiary = Gray,
    surface = White,
    error = Bittersweet,
    background = Light2,
    onBackground = White,
)
