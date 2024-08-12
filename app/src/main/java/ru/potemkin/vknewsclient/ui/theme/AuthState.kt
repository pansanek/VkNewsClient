package ru.potemkin.vknewsclient.ui.theme

sealed class AuthState {

    object Authorized:AuthState()

    object NotAuthorized:AuthState()

    object Initial:AuthState()
}