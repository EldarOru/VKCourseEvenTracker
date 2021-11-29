package com.example.eventracker.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eventracker.data.GeneralRepositoryImpl
import com.example.eventracker.domain.usecases.*

class ViewModelFactory: ViewModelProvider.Factory{

    private val generalRepository by lazy {
        GeneralRepositoryImpl()
    }

    private val createEventUseCase by lazy {
        CreateEventUseCase(generalRepository = generalRepository)
    }

    private val deleteEventUseCase by lazy {
        DeleteEventUseCase(generalRepository = generalRepository)
    }

    private val getFirebaseInfoUseCase by lazy {
        GetFirebaseInfoUseCase(generalRepository = generalRepository)
    }

    private val getUserAccUseCase by lazy {
        GetUserAccUseCase(generalRepository = generalRepository)
    }

    private val getUserDatabaseUseCase by lazy {
        GetUserDatabaseUseCase(generalRepository = generalRepository)
    }

    private val loginUseCase by lazy {
        LoginUseCase(generalRepository = generalRepository)
    }

    private val registrationUseCase by lazy {
        RegistrationUseCase(generalRepository = generalRepository)
    }



    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginFragmentViewModel::class.java)){
            return LoginFragmentViewModel(
                loginUseCase = loginUseCase,
                getUserAccUseCase = getUserAccUseCase,
                getFirebaseInfoUseCase = getFirebaseInfoUseCase) as T
        }
        if (modelClass.isAssignableFrom(AddEventFragmentViewModel::class.java)){
            return AddEventFragmentViewModel(
                createEventUseCase = createEventUseCase) as T
        }
        if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)){
            return MainFragmentViewModel(
                getUserDatabaseUseCase = getUserDatabaseUseCase,
                deleteEventUseCase = deleteEventUseCase,
                getUserAccUseCase = getUserAccUseCase) as T
        }
        if (modelClass.isAssignableFrom(RegistrationFragmentViewModel::class.java)){
            return RegistrationFragmentViewModel(
                registrationUseCase = registrationUseCase,
                getFirebaseInfoUseCase = getFirebaseInfoUseCase,
                getUserAccUseCase = getUserAccUseCase) as T
        }
        throw IllegalAccessException("ViewModel class is not found")
    }
}