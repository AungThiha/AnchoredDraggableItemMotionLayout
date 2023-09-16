package thiha.aung.myapplication

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.layoutId

@OptIn(ExperimentalMotionApi::class, ExperimentalFoundationApi::class)
@Composable
fun RecipeDetail(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val motionSceneContent = remember {
        context.resources
            .openRawResource(R.raw.motion_scene)
            .readBytes()
            .decodeToString()
    }

    val draggedDownAnchorTop = with(LocalDensity.current) { 200.dp.toPx() }
    val anchors = DraggableAnchors {
        AnchoredDraggableCardState.DRAGGED_DOWN at draggedDownAnchorTop
        AnchoredDraggableCardState.DRAGGED_UP at 0f
    }
    val density = LocalDensity.current
    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            initialValue = AnchoredDraggableCardState.DRAGGED_DOWN,
            anchors = anchors,
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            animationSpec = tween(),
        )
    }

    val offset = if (anchoredDraggableState.offset.isNaN()) 0f else anchoredDraggableState.offset
    val progress = (1 - (offset / draggedDownAnchorTop)).coerceIn(0f, 1f)

    MotionLayout(
        motionScene = MotionScene(motionSceneContent),
        progress = progress,
        modifier = modifier
            .fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.cake),
            contentDescription = "cake",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .layoutId("headerImage")
        )

        Box(
            modifier = Modifier
                .anchoredDraggable(anchoredDraggableState, Orientation.Vertical)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .layoutId("contentBg")
        )

        Text(
            text = "Fresh Strawberry Cake", fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold, modifier = Modifier
                .layoutId("title")
                .padding(10.dp)
        )

        Divider(
            Modifier
                .layoutId("titleDivider")
                .padding(horizontal = 34.dp)
        )

        Text(
            text = "by John Kanell", fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray, fontStyle = FontStyle.Italic,
            modifier = Modifier
                .layoutId("subTitle")
                .padding(6.dp)
        )

        Divider(
            Modifier
                .layoutId("subTitleDivider")
                .padding(horizontal = 34.dp)
        )

        Text(
            modifier = Modifier
                .layoutId("date")
                .fillMaxWidth()
                .padding(6.dp),
            text = "September, 2022", fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        val actionsProperties = motionProperties("actions")

        Box(
            modifier = Modifier
                .background(actionsProperties.value.color("background"))
                .layoutId("actionsTopBg")
        )

        Row(
            modifier = Modifier
                .background(actionsProperties.value.color("background"))
                .layoutId("actions")
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Share, contentDescription = "", tint = Color.White)
                Text(text = "SHARE", color = Color.White, fontSize = 12.sp)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Outlined.ThumbUp, contentDescription = "", tint = Color.White)
                Text(text = "LIKE", color = Color.White, fontSize = 12.sp)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Outlined.Star, contentDescription = "", tint = Color.White)
                Text(text = "SAVE", color = Color.White, fontSize = 12.sp)
            }
        }

        Box(
            modifier = Modifier
                .background(actionsProperties.value.color("background"))
                .layoutId("actionsBottomBg")
        )

        Text(
            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
                .layoutId("text"),
            fontSize = 12.sp,
        )
    }
}