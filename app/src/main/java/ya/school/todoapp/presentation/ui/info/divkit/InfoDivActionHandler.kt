package ya.school.todoapp.presentation.ui.info.divkit

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction

class InfoDivActionHandler(private val navigateBack: () -> Unit) : DivActionHandler() {
    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver
    ): Boolean {
        val url = action.url?.evaluate(resolver) ?: return super.handleAction(action, view, resolver)

        return if (url.scheme == SCHEME_DIV && handleExitAction(url, navigateBack) ||
            url.scheme == SCHEME_WEB && handleWebAction(url, view.view.context))
            true
        else
            super.handleAction(action, view, resolver)
    }

    private fun handleExitAction(action: Uri, navigateBack: () -> Unit): Boolean {
        return when (action.host) {
            "exit" -> {
                navigateBack()
                true
            }
            else -> false
        }
    }

    private fun handleWebAction(action: Uri, context: Context): Boolean {
        context.startActivity(
            Intent(Intent.ACTION_VIEW).apply {
                data = action
            }
        )
        return true
    }

    companion object {
        const val SCHEME_DIV = "div-action"
        const val SCHEME_WEB = "https"
    }
}