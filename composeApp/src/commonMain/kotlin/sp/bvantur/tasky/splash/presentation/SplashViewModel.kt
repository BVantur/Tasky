package sp.bvantur.tasky.splash.presentation

import androidx.lifecycle.ViewModel
import sp.bvantur.tasky.splash.data.SplashRepository

class SplashViewModel(private val splashRepository: SplashRepository) : ViewModel() {

    fun isUserAuthorized(): Boolean = splashRepository.isUserAuthorized()
}
