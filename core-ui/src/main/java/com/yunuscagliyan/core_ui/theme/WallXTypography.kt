package com.yunuscagliyan.core_ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yunuscagliyan.core_ui.R


val compactTypography:WallXTypography = WallXTypography(
    title1 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 20.sp
    ),
    title2 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 20.sp
    ),
    normal1 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 16.sp
    ),
    normal2 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.sp
    ),
    normal3 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.sp
    ),
    small1 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 12.sp
    ),
    small2 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.Light,
        fontSize = 10.sp,
        lineHeight = 12.sp
    ),
)

val MediumTypography:WallXTypography = WallXTypography(
    title1 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 24.sp
    ),
    title2 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 24.sp
    ),
    normal1 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 18.sp
    ),
    normal2 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 18.sp
    ),
    normal3 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp
    ),
    small1 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 14.sp
    ),
    small2 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        lineHeight = 14.sp
    ),
)
val LargeTypography:WallXTypography = WallXTypography(
    title1 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 28.sp
    ),
    title2 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 26.sp,
        lineHeight = 28.sp
    ),
    normal1 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 22.sp
    ),
    normal2 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 22.sp
    ),
    normal3 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 22.sp
    ),
    small1 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 20.sp
    ),
    small2 = TextStyle(
        fontFamily = WallXAppFontFamily.Monserrat,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        lineHeight = 18.sp
    ),
)

@Immutable
class WallXTypography(
    val title1: TextStyle,
    val title2: TextStyle,
    val normal1: TextStyle,
    val normal2: TextStyle,
    val normal3: TextStyle,
    val small1: TextStyle,
    val small2: TextStyle
){
    fun copy(
        title1: TextStyle = this.title1,
        title2: TextStyle = this.title2,
        normal1: TextStyle = this.normal1,
        normal2: TextStyle = this.normal2,
        normal3: TextStyle = this.normal3,
        small1: TextStyle = this.small1,
        small2: TextStyle = this.small2
    ):WallXTypography = WallXTypography(
        title1 = title1,
        title2 = title2,
        normal1 = normal1,
        normal2 = normal2,
        normal3 = normal3,
        small1 = small1,
        small2 = small2
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is WallXTypography) return false

        if (title1 != other.title1) return false
        if (title2 != other.title2) return false
        if (normal1 != other.normal1) return false
        if (normal2 != other.normal2) return false
        if (normal3 != other.normal3) return false
        if (small1 != other.small1) return false
        if (small2 != other.small2) return false
        return true
    }
}

object WallXAppFontFamily {
    @Stable
    val Monserrat = FontFamily(
        Font(
            R.font.montserrat_light,
            weight = FontWeight.Light
        ),
        Font(
            R.font.montserrat_regular,
            weight = FontWeight.Normal
        ),
        Font(
            R.font.montserrat_medium,
            weight = FontWeight.Medium
        ),
        Font(
            R.font.montserrat_semi_bold,
            weight = FontWeight.SemiBold
        ),
        Font(
            R.font.montserrat_bold,
            weight = FontWeight.Bold
        )
    )
}