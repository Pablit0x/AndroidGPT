package com.ps.androidgpt.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.ps.androidgpt.R
import com.ps.androidgpt.domain.model.ChatEntry

@Composable
fun ChatEntryItem(
    chatEntry: ChatEntry,
    onCopyClick: (String) -> Unit,
    onSaveClick: (ChatEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = 5.dp, shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .gradientSurface()
            .padding(8.dp)
    ) {


        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    append("${stringResource(id = R.string.you)}: ")
                }
                append(chatEntry.query)
            }, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Divider()

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    append("${stringResource(id = R.string.chat_gpt)}: ")
                }
                append(chatEntry.response)
            }, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(4.dp)
        )

        Row(
            modifier = Modifier
                .align(Alignment.End)
                .padding(8.dp)
        ) {
            IconButton(onClick = { onCopyClick(chatEntry.response) }) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            IconButton(onClick = {
                onSaveClick(chatEntry)
            }) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}