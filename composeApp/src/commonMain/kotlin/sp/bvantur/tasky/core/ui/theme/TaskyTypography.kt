package sp.bvantur.tasky.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.inter_bold
import tasky.composeapp.generated.resources.inter_extralight
import tasky.composeapp.generated.resources.inter_light
import tasky.composeapp.generated.resources.inter_medium
import tasky.composeapp.generated.resources.inter_regular
import tasky.composeapp.generated.resources.inter_thin

@Composable
private fun interFamily() = FontFamily(
    Font(Res.font.inter_light, FontWeight.Light),
    Font(Res.font.inter_regular, FontWeight.Normal),
    Font(Res.font.inter_thin, FontWeight.Thin),
    Font(Res.font.inter_extralight, FontWeight.ExtraLight),
    Font(Res.font.inter_medium, FontWeight.Medium),
    Font(Res.font.inter_bold, FontWeight.Bold)
)

@Composable
fun TaskyTypography() = Typography(
    displayLarge = TextStyle(
        fontFamily = interFamily(),
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
        fontSize = 28.sp
    ),
    displayMedium = TextStyle(
        fontFamily = interFamily(),
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
        fontSize = 26.sp
    ),
    displaySmall = TextStyle(
        fontFamily = interFamily(),
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = interFamily(),
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
        fontSize = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = interFamily(),
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Normal,
        fontSize = 18.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = interFamily(),
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = interFamily(),
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = interFamily(),
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Normal,
        fontSize = 18.sp
    ),
    labelMedium = TextStyle(
        fontFamily = interFamily(),
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
        fontSize = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = interFamily(),
        fontWeight = FontWeight.Medium,
        fontStyle = FontStyle.Normal,
        fontSize = 14.sp
    )
)
