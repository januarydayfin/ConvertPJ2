package com.krayapp.convertpj2.present


import com.github.terrakok.cicerone.Router
import com.krayapp.convertpj2.view.IScreens
import com.krayapp.convertpj2.view.MainView
import moxy.MvpPresenter

class Presenter(val router: Router, val screens: IScreens):MvpPresenter<MainView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.mainFrag())
    }

}