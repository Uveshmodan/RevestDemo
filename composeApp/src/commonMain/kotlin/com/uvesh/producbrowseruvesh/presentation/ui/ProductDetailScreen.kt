package com.uvesh.productbrowser.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.uvesh.productbrowser.domain.model.Product
import org.jetbrains.compose.resources.painterResource
import producbrowseruvesh.composeapp.generated.resources.Res
import producbrowseruvesh.composeapp.generated.resources.ic_back
import producbrowseruvesh.composeapp.generated.resources.ic_star

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: Product?,
    onBack: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (product == null) {
            // show loading/empty state if no product present
            EmptyView(innerPadding = innerPadding)
        } else {
            DetailsView(innerPadding = innerPadding, product = product)
        }
    }
}

@Composable
private fun DetailsView(innerPadding: PaddingValues, product: Product) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        AsyncImage(
            model = product.thumbnail,
            contentDescription = product.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
                .clip(RoundedCornerShape(8.dp))
                .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = product.title ?: "",
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                fontWeight = FontWeight.Normal,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f).padding(end = 10.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_star),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    colorFilter = ColorFilter.tint(Color(0xFFFFD700))
                )
                Text(
                    text = " ${product.rating ?: "-"}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Brand: ${product.brand ?: "-"}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$${product.price}",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = ("Description: " + product.description),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun EmptyView(innerPadding: PaddingValues) {
    Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
        Text("No product selected.")
    }
}
