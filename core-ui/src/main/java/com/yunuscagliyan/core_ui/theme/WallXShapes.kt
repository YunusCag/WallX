package com.yunuscagliyan.core_ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

val compactShapes:WallXShapes = WallXShapes(
    small = RoundedCornerShape(2.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(6.dp),
    bottomSheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
)
val mediumShapes:WallXShapes = WallXShapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(6.dp),
    large = RoundedCornerShape(8.dp),
    bottomSheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
)
val LargeShapes:WallXShapes = WallXShapes(
    small = RoundedCornerShape(6.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(10.dp),
    bottomSheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
)

@Immutable
class WallXShapes(
    val small: CornerBasedShape,
    val medium: CornerBasedShape,
    val large: CornerBasedShape,
    val bottomSheetShape: CornerBasedShape,
    val nonRound:CornerBasedShape = RoundedCornerShape(0.dp)
) {

    fun copy(
        small:CornerBasedShape = this.small,
        medium:CornerBasedShape = this.medium,
        large:CornerBasedShape = this.small,
        bottomSheetShape:CornerBasedShape = this.bottomSheetShape,
        nonRound:CornerBasedShape = this.nonRound,
    ):WallXShapes = WallXShapes(
        small = small,
        medium = medium,
        large = large,
        bottomSheetShape = bottomSheetShape,
        nonRound = nonRound
    )
}