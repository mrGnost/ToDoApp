package ya.school.todoapp.presentation.ui.components.topbars

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ya.school.todoapp.presentation.ui.components.ToDoSubText
import ya.school.todoapp.presentation.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    text: String,
    subtext: String = "",
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = { }
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = text,
                    color = AppTheme.colors.labelPrimary
                )
                ToDoSubText(text = subtext)
            }
        },
        scrollBehavior = scrollBehavior,
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppTheme.colors.backPrimary,
            scrolledContainerColor = AppTheme.colors.backPrimary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainTopBarPreview() {
    MainTopBar(text = "Заголовок", subtext = "Подзаголовок", scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior())
}