package com.krayapp.convertpj2.present

import io.reactivex.Scheduler


interface Scheduler {
    fun io():Scheduler
    fun background(): Scheduler
    fun main():Scheduler
}