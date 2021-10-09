package com.krayapp.convertpj2.present


import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.github.terrakok.cicerone.Router
import com.krayapp.convertpj2.Mapper
import com.krayapp.convertpj2.view.MainFragView
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.RuntimeException
import kotlin.random.Random

class FragPresenter(private val scheduler: Scheduler) :
    MvpPresenter<MainFragView>() {
    private var filePermission = false
    private var disposables = CompositeDisposable()


    @RequiresApi(Build.VERSION_CODES.M)
    fun checkPermission(activity: Activity?) {
        activity?.let {
            when {
                ContextCompat
                    .checkSelfPermission(it, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    filePermission = true
                    println("$filePermission")
                }
                activity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                    AlertDialog.Builder(it)
                        .setTitle("Доступ к памяти устройства")
                        .setMessage("Доступ необходим, чтобы открыть файл")
                        .setPositiveButton("Предоставить доступ") { _, _ ->
                            requestPermission(activity)
                        }
                        .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
                else -> requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
                )
            }
        }
    }

    private fun requestPermission(activity: Activity?) {
        requestPermissions(activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
    }

    fun convertFile(inputStream: InputStream) {
        val randomName = Random.nextInt(0, 100000)
        val filePath = File("/storage/emulated/0/Pictures/converted${randomName}.png")
        val fileOutputStream =
            FileOutputStream(filePath)

        disposables.add(
        Single
            .just(inputStream)
            .observeOn(scheduler.background())
            .map(Mapper::map)
            .map { bmp ->
                BitmapFactory.decodeStream(inputStream)
                bmp.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                fileOutputStream.close()
            }
            .observeOn(scheduler.main())
            .map { viewState.setTextView("Почти готово") }
            .subscribeOn(scheduler.io())
            .doOnSuccess { viewState.setTextView("Выполнено") }
            .subscribe()
        )
    }
}