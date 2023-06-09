package com.example.resttestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.resttestapp.data.models.LocalListItemModel
import com.example.resttestapp.data.repositories.ListRepository
import com.example.resttestapp.data.sources.JsonApi
import com.example.resttestapp.data.sources.LocalListDataSource
import com.example.resttestapp.data.sources.RemoteListDataSource
import com.example.resttestapp.ui.theme.RESTTestAppTheme
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by lazy {
        val app = this.application as RestTestApplication
        val remoteListDataSource = RemoteListDataSource(JsonApi.getInstance(), Dispatchers.IO)
        val localListDataSource = LocalListDataSource(app.database, Dispatchers.IO)
        val listRepository = ListRepository(remoteListDataSource, localListDataSource)
        ViewModelProvider(
            this,
            MainViewModel.Factory(listRepository)
        )[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RESTTestAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JsonList(
                        mainViewModel.localListResponse
                    )
                    mainViewModel.getLocalDataList()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JsonItem(
    localListItem: LocalListItemModel,
    index: Int,
    selectedIndex: Int,
    onClick: (Int) -> (Unit)
) {
    // Color change on click
    val containerColor: Color
    val textColor: Color

    if (index == selectedIndex) {
        containerColor = MaterialTheme.colorScheme.primary
        textColor = MaterialTheme.colorScheme.onPrimary
    } else {
        containerColor = MaterialTheme.colorScheme.secondary
        textColor = MaterialTheme.colorScheme.onSecondary
    }

    ListItem(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(96.dp)
            .clickable {
                onClick(index)
            }
            .clip(RoundedCornerShape(8.dp)),
        leadingContent = {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(localListItem.photoThumbnailUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.image_not_found_icon),
                error = painterResource(R.drawable.image_not_found_icon),
                fallback = painterResource(R.drawable.image_not_found_icon),
                contentDescription = localListItem.photoTitle
            )
        },
        headlineText = {
            Text(
                text = localListItem.postTitle,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        supportingText = {
            Text(
                text = "User ID: ${localListItem.userId}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.outlineVariant
                    )
                    .padding(4.dp),
            )
            Text(
                text = localListItem.photoTitle,
                style = MaterialTheme.typography.labelMedium,
                color = textColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = containerColor

        )
    )
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
                .wrapContentHeight()
                .width(300.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(localListItem.photoUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = localListItem.photoTitle,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> {
                        CircularProgressIndicator()
                    }

                    is AsyncImagePainter.State.Success -> {
                        SubcomposeAsyncImageContent()
                    }

                    else -> {
                        SubcomposeAsyncImageContent(painter = painterResource(R.drawable.image_not_found_icon))
                    }
                }
            }

            Text(
                text = localListItem.photoTitle,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 4.dp
                ),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
            )

            Text(
                text = localListItem.postTitle,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            Text(
                text = localListItem.postBody,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify
            )
        }
    }
}

private class LocalItemPreviewParameterProvider : PreviewParameterProvider<LocalListItemModel> {
    override val values = sequenceOf(
        LocalListItemModel(
            id = 0,
            photoAlbumId = 1,
            photoThumbnailUrl = "testphoto.com",
            photoTitle = "photo",
            photoUrl = "testphoto.com",
            postBody = "body",
            postTitle = "title",
            userId = 2
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ListItemPreview(
    @PreviewParameter(LocalItemPreviewParameterProvider::class) localListItem: LocalListItemModel
) {
    ListItem(
        leadingContent = {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(localListItem.photoThumbnailUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.image_not_found_icon),
                error = painterResource(R.drawable.image_not_found_icon),
                fallback = painterResource(R.drawable.image_not_found_icon),
                contentDescription = localListItem.photoTitle,
            )},
        headlineText = {
            Text(
                text = localListItem.postTitle,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        supportingText = {
            Text(
                text = "User ID: ${localListItem.userId}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.outlineVariant
                    )
                    .padding(4.dp)
            )
            Text(
                text = localListItem.photoTitle,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    )
}

@Preview
@Composable
private fun JsonDetailPreview(
    @PreviewParameter(LocalItemPreviewParameterProvider::class) localListItem: LocalListItemModel,
) {
    Card(
        modifier = Modifier
            .padding(all = 8.dp)
            .wrapContentHeight()
            .width(300.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(localListItem.photoUrl)
                .crossfade(true)
                .build(),
            contentDescription = localListItem.photoTitle,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator()
                }

                is AsyncImagePainter.State.Success -> {
                    SubcomposeAsyncImageContent()
                }

                else -> {
                    SubcomposeAsyncImageContent(painter = painterResource(R.drawable.image_not_found_icon))
                }
            }
        }

        Text(
            text = localListItem.photoTitle,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 4.dp
            ),
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
        )

        Text(
            text = localListItem.postTitle,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Text(
            text = localListItem.postBody,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify
        )
    }
}