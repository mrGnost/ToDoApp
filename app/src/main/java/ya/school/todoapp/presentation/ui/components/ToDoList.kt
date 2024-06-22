package ya.school.todoapp.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ya.school.todoapp.data.TodoItem

@Composable
fun ToDoListColumn(
    tasks: List<TodoItem>,
    onDelete: (String) -> Unit,
    onCompleteChange: (String, Boolean) -> Unit,
    onPickItem: (String) -> Unit,
    modifier: Modifier = Modifier,
    fadeOnComplete: Boolean
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(
            items = tasks,
            key = { _, item -> item.id }
        ) { _, task ->
            SwipeContainer(
                item = task,
                onDelete = onDelete,
                onComplete = { onCompleteChange(it, true) },
                fadeOnComplete = fadeOnComplete
            ) {
                ToDoListItem(
                    item = it,
                    onCheckedChange = onCompleteChange,
                    onInfoClick = onPickItem
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeContainer(
    item: TodoItem,
    onDelete: (String) -> Unit,
    onComplete: (String) -> Unit,
    fadeOnComplete: Boolean,
    animationDuration: Int = 500,
    content: @Composable (TodoItem) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    var isComplete by remember {
        mutableStateOf(item.isDone)
    }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    isComplete = true
                    fadeOnComplete
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    isRemoved = true
                    true
                }
                SwipeToDismissBoxValue.Settled -> false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item.id)
        }
    }

    LaunchedEffect(key1 = isComplete) {
        if (isComplete) {
            if (fadeOnComplete)
                delay(animationDuration.toLong())
            onComplete(item.id)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved && !(isComplete && fadeOnComplete),
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                SwipeBackground(swipeDismissState = state)
            }
        ) {
            content(item)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBackground(
    swipeDismissState: SwipeToDismissBoxState
) {
    val color = when (swipeDismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.secondary
        SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }
    val alignment = when (swipeDismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
        else -> Alignment.CenterEnd
    }
    val icon = when (swipeDismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Icons.Filled.Done
        else -> Icons.Filled.Delete
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = alignment
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}