package ya.school.todoapp.data.system

import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Класс, отвечающий за мониторинг состояния сети
 */
@Singleton
class ConnectivitySource @Inject constructor() {
    val isConnectedFlow: Flow<Boolean> = callbackFlow {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(true)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                trySend(false)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(false)
            }
        }
    }
}