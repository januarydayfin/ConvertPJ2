package com.krayapp.convertpj2

import android.os.Bundle
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.krayapp.convertpj2.view.AndroidScreens
import com.krayapp.convertpj2.view.MainView
import com.krayapp.convertpj2.present.Presenter
import com.krayapp.convertpj2.databinding.ActivityMainBinding
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    val navigator = AppNavigator(this, R.id.container)
    private val presenter by moxyPresenter { Presenter(App.instance.router, AndroidScreens()) }
    private var binding: ActivityMainBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigatorHolder.removeNavigator()
    }
}