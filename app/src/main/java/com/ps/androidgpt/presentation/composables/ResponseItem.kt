package com.ps.androidgpt.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ps.androidgpt.R

@Composable
fun ResponseItem(
    response: String,
    query: String,
    onCopyClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .gradientSurface()
            .padding(8.dp)
    ) {

        Row(modifier.fillMaxWidth()){

            Icon(imageVector = Icons.Outlined.Person, contentDescription = null)

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = query,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider()

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier.fillMaxWidth()){

            Icon(imageVector = Icons.Outlined.Android, contentDescription = null)

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = response,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(4.dp)
            )
        }

        Row(
            modifier = Modifier.align(Alignment.End)
        ) {
            IconButton(onClick = { onCopyClick(response) }) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surfaceTint
                )
            }

            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surfaceTint
                )
            }
        }
    }
}