package com.krayapp.convertpj2.present

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulersList: com.krayapp.convertpj2.present.Scheduler {
    override fun io(): Scheduler = Schedulers.io()

    override fun background(): Scheduler = Schedulers.computation()

    override fun main(): Scheduler = AndroidSchedulers.mainThread()

}