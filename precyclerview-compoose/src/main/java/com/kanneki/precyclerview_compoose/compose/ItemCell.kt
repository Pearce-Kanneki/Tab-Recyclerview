package com.kanneki.precyclerview_compoose.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanneki.precyclerview_compoose.data.DemoData
import com.kanneki.precyclerview_compoose.data.DemoDataType

/**
 * LazyColumn item 設置
 *
 * @param data 要顯示的資料
 */
@Composable
fun ItemCell(data: DemoData) {
    when (data.type) {
        DemoDataType.TITLE -> {
            Text(
                text = data.title,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp)
            )
        }
        DemoDataType.DETAIL -> {
            Card {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.background(Color.LightGray)
                ) {
                    Icon(
                        imageVector = Icons.Filled.MailOutline,
                        contentDescription = "icon",
                        modifier = Modifier.height(100.dp).width(100.dp)
                    )
                    Text(text = data.des, style = MaterialTheme.typography.body1)
                }
            }
        }
        else -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun ItemCellByTitlePreview() {
    val data = DemoData(
        id = 0,
        title = "Title",
        type = DemoDataType.TITLE
    )
    ItemCell(data = data)
}

@Preview(showBackground = true)
@Composable
fun ItemCellByDetailPreview() {
    val data = DemoData(
        id = 0,
        title = "Detail",
        des = "Detail",
        type = DemoDataType.DETAIL
    )
    ItemCell(data = data)
}