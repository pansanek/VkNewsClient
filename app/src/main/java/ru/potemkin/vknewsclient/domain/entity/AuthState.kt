package ru.potemkin.vknewsclient.domain.entity

sealed class AuthState {

    object Authorized: AuthState()

    object NotAuthorized: AuthState()

    object Initial: AuthState()
}