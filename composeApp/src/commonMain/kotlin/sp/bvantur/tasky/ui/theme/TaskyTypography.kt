package sp.bvantur.tasky.ui.theme

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
import tasky.composeapp.generated.resources.inter_regular
import tasky.composeapp.generated.resources.inter_thin

@Composable
private fun interFamily() = FontFamily(
    Font(Res.font.inter_light, FontWeight.Light, FontStyle.Normal),
    Font(Res.font.inter_regular, FontWeight.Normal, FontStyle.Normal),
    Font(Res.font.inter_thin, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.inter_extralight, FontWeight.Medium, FontStyle.Normal),
    Font(Res.font.inter_bold, FontWeight.Bold, FontStyle.Normal)
)

@Composable
fun InspektifyTypography() = Typography(
    displayLarge = TextStyle(
        fontFamily = interFamily(),
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
        fontSize = 28.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = interFamily(),
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Normal,
        fontSize = 18.sp
    ),
    labelMedium = TextStyle(
        fontFamily = interFamily(),
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Normal,
        fontSize = 18.sp
    ),
    labelSmall = TextStyle(
        fontFamily = interFamily(),
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Normal,
        fontSize = 14.sp
    )
)
