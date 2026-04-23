package com.ailurusrp.panda_todo.features.home.ui

import android.R.attr.maxWidth
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Drawer() {
    ModalDrawerSheet(drawerContainerColor = Color.White) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Views", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        }

        HorizontalDivider(
            color = Color.LightGray,
            thickness = .5.dp,
            modifier = Modifier.fillMaxWidth(0.75f)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = {},
                modifier = Modifier.width(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(4.dp)
            ) { Text("Export") }

            Button(
                onClick = {},
                modifier = Modifier.width(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(4.dp)
            ) { Text("Import") }
        }
    }
}

enum class HomeViews(val text: String) {
    OpenTasks("Open Tasks"), CompletedTasks("Completed Tasks")
}
