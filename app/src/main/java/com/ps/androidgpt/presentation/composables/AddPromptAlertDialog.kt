package com.ps.androidgpt.presentation.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ps.androidgpt.R

@Composable
fun AddPromptAlertDialog(
    onCloseDialog: () -> Unit, title: String, onConfirmAction: (String) -> Unit
) {

    var prompt by remember { mutableStateOf("") }

    AlertDialog(onDismissRequest = {
        onCloseDialog()
    }, title = {
        Text(text = title, fontWeight = FontWeight.SemiBold)
    }, text = {
        OutlinedTextField(
            value = prompt, onValueChange = { prompt = it }, placeholder = {
            Text(text = stringResource(id = R.string.enter_prompt))
        }, minLines = 3,
            modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .gradientSurface())
    }, confirmButton = {
        TextButton(
            onClick = {
                onConfirmAction(prompt)
                onCloseDialog()
            }, modifier = Modifier.padding(4.dp)
        ) {
            Text(
                stringResource(id = R.string.add),
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.width(12.dp))
        }
    }, dismissButton = {
        TextButton(
            onClick = {
                onCloseDialog()
            }, modifier = Modifier.padding(4.dp)
        ) {
            Text(
                stringResource(id = R.string.cancel),
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }, modifier = Modifier
        .padding(16.dp)
        .clip(RoundedCornerShape(20))
        .gradientSurface()
        .border(
            1.dp, color = MaterialTheme.colorScheme.onBackground, shape = RoundedCornerShape(20)
        )
    )
}