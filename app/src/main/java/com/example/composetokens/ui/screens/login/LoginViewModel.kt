package com.example.composetokens.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetokens.CredentialsRegister
import com.example.composetokens.domain.usecases.LoginUseCase
import com.example.composetokens.domain.usecases.RegisterUseCase
import com.example.plantillaexamen.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase,private val registerUseCase: RegisterUseCase) : ViewModel() {
    private val _state =
        MutableStateFlow(PantallaLoginState(logged = false, loading = false, error = null))
    val state: StateFlow<PantallaLoginState> get() = _state

    fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                login()
            }
            is LoginEvent.Register -> {
                register(event.register)
            }
        }
    }

    private fun register(register: CredentialsRegister) {

        viewModelScope.launch {
            registerUseCase(register)
                .catch { cause ->
                    _state.value = _state.value.copy(
                        error = cause.message ?: "Error",
                        loading = false
                    )
                }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> {
                            _state.value = _state.value.copy(loading = true)
                        }

                        is NetworkResult.Success -> {
                            _state.value = _state.value.copy(error="Registrado con exito", loading = false)
                        }

                        is NetworkResult.Error -> {
                            _state.value = _state.value.copy(
                                error = result.message ?: "Error",
                                loading = false
                            )
                        }
                    }
                }
        }
    }

    private fun login() {
        viewModelScope.launch {
            loginUseCase(state.value.username, state.value.password)
                .catch { cause ->
                    _state.value = _state.value.copy(
                        error = cause.message ?: "Error",
                        loading = false
                    )
                }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> {
                            _state.value = _state.value.copy(loading = true)
                        }

                        is NetworkResult.Success -> {
                            _state.value = _state.value.copy(logged = true, loading = false)
                        }

                        is NetworkResult.Error -> {
                              _state.value = _state.value.copy(
                                    error = result.message ?: "Error",
                                    loading = false
                                )

                        }
                    }
                }
        }

    }
}
