package com.myniprojects.compass.ui.composes.compass

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myniprojects.compass.R
import com.myniprojects.compass.ui.theme.ThemedPreview
import com.myniprojects.compass.vm.UPDATE_FREQUENCY

val COMPASS_PADDING = 16.dp

@Composable
fun Compass(
    direction: Int?,
    rotation: Int
)
{
    val (lastRotation, setLastRotation) = remember { mutableStateOf(0) }
    var newRotation = lastRotation
    val modLast = if (lastRotation > 0) lastRotation % 360 else 360 - (-lastRotation % 360)
    
    if (modLast != rotation)
    {
        // new rotation comes in
        val backward = if (rotation > modLast) modLast + 360 - rotation else modLast - rotation
        val forward = if (rotation > modLast) rotation - modLast else 360 - modLast + rotation
        
        newRotation = if (backward < forward)
        {
            // backward rotation is shorter
            lastRotation - backward
        }
        else
        {
            // forward rotation is shorter (or they are equals)
            lastRotation + forward
        }
        
        setLastRotation(newRotation)
    }
    
    val angle: Float by animateFloatAsState(
        targetValue = -newRotation.toFloat(),
        animationSpec = tween(
            durationMillis = UPDATE_FREQUENCY,
            easing = LinearEasing
        )
    )
    
    Rose(angle = angle, rotation = rotation)
    
    if (direction != null)
    {
        CompassDirectionPointer(
            angle = angle + direction.toFloat(),
            pointerIcon = R.drawable.ic_pointerdot,
            contentDsc = R.string.destination_direction
        )
    }
    
    CompassDirectionPointer(
        pointerIcon = R.drawable.ic_line,
        contentDsc = R.string.phone_direction
    )
}

// region previews

@Preview(showBackground = true)
@Composable
fun PreviewCompassWithDirection()
{
    ThemedPreview {
        Compass(direction = 45, rotation = -85)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCompassWithDirectionDark()
{
    ThemedPreview(
        darkTheme = true
    ) {
        Compass(direction = 45, rotation = -85)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCompassWithoutDirection()
{
    ThemedPreview {
        Compass(direction = null, rotation = -85)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCompassWithoutDirectionDark()
{
    ThemedPreview(
        darkTheme = true
    ) {
        Compass(direction = null, rotation = -85)
    }
}

// endregion