import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.codebyyosry.samples.apps.core.designsystem.icon.DemoIcons
import com.codebyyosry.samples.apps.core.domain.model.Movie

@Composable
fun MovieItem(movie: Movie, onFavoriteClicked: (Boolean) -> Unit) {
    var isFav by rememberSaveable { mutableStateOf(movie.isFavorite)}

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
    ) {
        // Movie image
        Image(
            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500/${movie.posterPath}"),
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Title and favorite row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = movie.title,
                modifier = Modifier.weight(1f),
                maxLines = 1
            )

            IconButton(onClick = {
                isFav = !isFav
                onFavoriteClicked(isFav)
            }) {

                Icon(
                    imageVector = if (isFav) DemoIcons.Favorite else DemoIcons.UnFavorite,
                    contentDescription = "Favorite"
                )
            }
        }

        // Rating
        Text(text = "⭐ ${movie.voteAverage}", modifier = Modifier.padding(top = 4.dp))
    }
}
