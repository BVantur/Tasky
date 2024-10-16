package sp.bvantur.tasky.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sp.bvantur.tasky.splash.domain.SplashRepository

class SplashViewModel(private val splashRepository: SplashRepository) : ViewModel() {
    fun isUserAuthorized(): Boolean = splashRepository.isUserAuthorized().also {
        if (it) {
            viewModelScope.launch {
                splashRepository.clearDatabaseContent()
            }
        }
    }
}
