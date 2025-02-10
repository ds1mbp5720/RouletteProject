package com.example.rouletteproject.dialog

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.data.entity.RouletteEntity
import com.example.rouletteproject.MainViewModel
import com.example.rouletteproject.R
import com.example.rouletteproject.component.RouletteItem

@Composable
fun EditListDialog(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    itemList: RouletteEntity,
    dismissRequest: () -> Unit
) {
    val context = LocalContext.current
    val updateList = remember { mutableStateListOf<String>() }
    val title = remember { mutableStateOf(itemList.title) }
    updateList.addAll(itemList.rouletteData)
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
            Column(
                modifier = modifier
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                repeat(updateList.size) { index ->
                    RouletteItem(
                        updateEnable = true,
                        text = updateList[index],
                        onUpdateList = {
                            updateList[index] = it
                        },
                        onDeleteClick = {
                            updateList.remove(it)
                        }
                    )
                }
            }
            BasicDivider()
            Button(
                onClick = {
                    if(updateList.size > 1 && title.value.isNotEmpty()) {
                        mainViewModel.update(
                            RouletteEntity(
                                id = itemList.id,
                                title = title.value,
                                rouletteData = updateList
                            )
                        )
                        dismissRequest.invoke()
                    } else {
                        if(title.value.isEmpty())
                            Toast.makeText(context, context.getString(R.string.text_warning_title), Toast.LENGTH_SHORT).show()
                        if(updateList.size <= 1)
                            Toast.makeText(context, context.getString(R.string.text_warning_list_length), Toast.LENGTH_SHORT).show()
                    }

                }
            ) {
                Text(text = stringResource(id = R.string.btn_add))
            }
        }
    }
}