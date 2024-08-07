package ya.school.todoapp.presentation.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import ya.school.todoapp.presentation.ui.theme.AppTheme

@Composable
fun RemoveButton(
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple()
            ) {
                if (isActive)
                    onClick()
            }
            .semantics {
                role = Role.Button
            }
    ) {
        val color = with (AppTheme.colors) {
            if (isActive)
                red
            else
                labelDisable
        }

        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = null,
            tint = color
        )
        Text(
            text = "Удалить",
            color = color
        )
    }
}

@Preview
@Composable
fun RemoveButtonPreview() {
    Column {
        RemoveButton(isActive = true, onClick = {})
        RemoveButton(isActive = false, onClick = {})
    }
}