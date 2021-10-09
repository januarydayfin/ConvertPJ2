package com.krayapp.convertpj2

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.InputStream

object Mapper {
    fun map(inputStream: InputStream): Bitmap {
        return BitmapFactory.decodeStream(inputStream)
    }
}