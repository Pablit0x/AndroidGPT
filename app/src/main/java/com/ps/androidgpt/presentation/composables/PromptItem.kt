package com.ps.androidgpt.presentation.composables

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ps.androidgpt.R


@Composable
fun PromptItem(
    prompt: String, onSelect: () -> Unit, onRemove: () -> Unit
) {

    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current

    Box(modifier = Modifier.onSizeChanged {
        itemHeight = with(density) { it.height.toDp() }
    }) {
        Column(modifier = Modifier
            .fillMaxWidth()

            .padding(8.dp)
            .clip(RoundedCornerShape(20))
            .gradientSurface()
            .indication(interactionSource, LocalIndication.current)
            .pointerInput(true) {
                detectTapGestures(onLongPress = {
                    isContextMenuVisible = true
                    pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                }, onPress = {
                    val press = PressInteraction.Press(it)
                    interactionSource.emit(press)
                    tryAwaitRelease()
                    interactionSource.emit(PressInteraction.Release(press))
                })
            }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = prompt,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp,
                    modifier = Modifier.weight(3f)
                )

                IconButton(onClick = { onSelect() }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier
                            .size(42.dp)
                            .background(
                                color = MaterialTheme.colorScheme.background, shape = CircleShape
                            )
                            .padding(8.dp)
                            .weight(1f)
                    )
                }
            }
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(0.5f),
            expanded = isContextMenuVisible,
            onDismissRequest = {
                isContextMenuVisible = false
            },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            ),
        ) {
            DropdownMenuItem(text = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(id = R.string.delete_prompt))
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }, onClick = {
                isContextMenuVisible = false
                onRemove()
            })

        }
    }
}