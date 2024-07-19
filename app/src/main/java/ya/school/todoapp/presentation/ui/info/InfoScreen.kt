package ya.school.todoapp.presentation.ui.info

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.div.core.Div2Context
import org.json.JSONObject
import ya.school.todoapp.presentation.ui.info.divkit.Div2ViewFactory
import ya.school.todoapp.presentation.ui.util.AssetUtil.readJson

@Composable
fun InfoScreen(divContext: Div2Context) {
    AndroidView(
        factory = { context ->
            val divJson = JSONObject(context.readJson("info_layout.json"))
            val templatesJson = divJson.optJSONObject("templates")
            val cardJson = divJson.getJSONObject("card")

            Div2ViewFactory(divContext, templatesJson).createView(cardJson)
        },
        modifier = Modifier
            .fillMaxSize()
    )
}