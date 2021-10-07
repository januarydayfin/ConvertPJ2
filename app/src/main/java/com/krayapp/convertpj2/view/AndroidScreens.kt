package com.krayapp.convertpj2.view


import com.github.terrakok.cicerone.androidx.FragmentScreen

class AndroidScreens : IScreens {
    override fun mainFrag() = FragmentScreen { MainFragment.newInstance() }
}
