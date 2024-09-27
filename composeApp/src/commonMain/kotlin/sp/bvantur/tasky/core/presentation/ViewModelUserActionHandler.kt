package sp.bvantur.tasky.core.presentation

internal interface ViewModelUserActionHandler<Action : UserAction> {
    fun onUserAction(userAction: Action)
}
