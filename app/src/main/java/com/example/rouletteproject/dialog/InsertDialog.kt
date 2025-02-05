package com.example.rouletteproject.dialog

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.rouletteproject.R
import com.example.rouletteproject.component.RouletteItem

@Composable
fun InsertRouletteListDialog(
    modifier: Modifier = Modifier,
    addRouletteList: (String, List<String>) -> Unit,
    dismissRequest: () -> Unit
) {
    val context = LocalContext.current
    val rouletteList = remember { mutableStateListOf<String>() }
    val title = remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = dismissRequest
    ) {
        Column(
            modifier = modifier
                .padding(vertical = 8.dp)
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicTextField(
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp),
                value = title.value,
                onValueChange = { textValue ->
                    title.value = textValue
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.titleMedium
            ) { innerTextField ->
                innerTextField()
                if(title.value.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.title_basic_list)
                    )
                }
            }
            BasicDivider()
            if(rouletteList.size > 0 ) {
                Column(
                    modifier = modifier
                        .padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    repeat(rouletteList.size) { index ->
                        RouletteItem(
                            updateEnable = true,
                            text = rouletteList[index],
                            onUpdateList = {
                                rouletteList[index] = it
                            },
                            onDeleteClick = {
                                rouletteList.remove(it)
                            }
                        )
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(10.dp))
            }
            IconButton(
                onClick = {
                    if (rouletteList.size < 9) {
                        rouletteList.add("")
                    } else {
                        Toast.makeText(context, context.getString(R.string.text_warning_list_max_size), Toast.LENGTH_SHORT).show()
                    }
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "add_string",
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = CircleShape
                        )
                )
            }
            BasicDivider()
            Button(
                onClick = {
                    if(rouletteList.size > 1 && title.value.isNotEmpty()) {
                        addRouletteList.invoke(
                            title.value, rouletteList
                        )
                        dismissRequest.invoke()
                    } else {
                        if(title.value.isEmpty())
                            Toast.makeText(context, context.getString(R.string.text_warning_title),Toast.LENGTH_SHORT).show()
                        if(rouletteList.size <= 1)
                            Toast.makeText(context, context.getString(R.string.text_warning_list_length),Toast.LENGTH_SHORT).show()
                    }

                }
            ) {
                Text(text = stringResource(id = R.string.btn_add))
            }
        }
    }
}

@Composable
fun BasicDivider(
    height: Int = 4,
    color: Color = Color.Black
) {
    Spacer(modifier = Modifier.height(height.dp))
    Divider(modifier = Modifier.fillMaxWidth(), color = color)
    Spacer(modifier = Modifier.height(height.dp))
}

@Preview
@Composable
fun DialogPreview() {
    InsertRouletteListDialog(addRouletteList = { _, _ -> }) {}
}