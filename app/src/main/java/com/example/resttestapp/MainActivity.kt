package com.example.resttestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.resttestapp.data.models.LocalListItemModel
import com.example.resttestapp.ui.theme.RESTTestAppTheme

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RESTTestAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    // TODO figure out why it won't read from theme
                    color = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                    // color = MaterialTheme.colorScheme.background
                ) {
                    JsonList(
                        mainViewModel.mergeRemoteSources(
                            mainViewModel.photoListResponse,
                            mainViewModel.detailListResponse
                        )
                    )
                    mainViewModel.getPhotoList()
                    mainViewModel.getDetailList()
                }
            }
        }
    }
}
@Composable
fun JsonList(
    localListItems: List<LocalListItemModel>
) {
    var selectedIndex by remember { mutableStateOf(-1) }
    var detailControl by remember { mutableStateOf(false) }

    LazyColumn {
        itemsIndexed(items = localListItems) { index, item ->
            JsonItem(localListItem = item, index, selectedIndex) { i ->
                selectedIndex = i
                detailControl = true
            }
            // Detail Popup UI
            if (detailControl && index == selectedIndex)
                JsonDetail(localListItem = item) {b ->
                    detailControl = b
                }
        }
    }
}

@Composable
fun JsonItem(
    localListItem: LocalListItemModel,
    index: Int,
    selectedIndex: Int,
    onClick: (Int) -> (Unit)
) {
    // Color change on click
    val backgroundColor: Color = if (index == selectedIndex) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.background
    }

    // List Item UI
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(110.dp)
            .clickable {
                onClick(index)
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Surface(color = backgroundColor) {

            Row(
                Modifier
                    .padding(4.dp)
                    .fillMaxSize()
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(localListItem.photoThumbnailUrl)
                        .crossfade(true)
                        .build(),
                    // TODO add placeholder image
                    // placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = localListItem.photoTitle,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(0.8f)
                ) {
                    localListItem.postTitle?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    localListItem.userId.toString().let {
                        Text(
                            text = "User ID: $it",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .background(
                                    Color.LightGray
                                )
                                .padding(4.dp),
                            color = Color.DarkGray
                        )
                    }
                    localListItem.photoTitle?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun JsonDetail(
    localListItem: LocalListItemModel,
    onDismissRequest: (Boolean) -> Unit
) {
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { onDismissRequest(false) },
        properties = PopupProperties(focusable = true)
    ) {
        Card(
            modifier = Modifier
                .padding(all = 8.dp)
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(localListItem.photoUrl)
                    .crossfade(true)
                    .build(),
                // TODO add placeholder image
                // placeholder = painterResource(R.drawable.placeholder),
                contentDescription = localListItem.photoTitle,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )

            localListItem.photoTitle?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 4.dp
                    ),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                )
            }

            localListItem.postTitle?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 16.dp
                    ),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }

            localListItem.postBody?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 16.dp
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}