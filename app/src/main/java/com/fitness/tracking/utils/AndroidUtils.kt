package com.fitness.tracking.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.fitness.tracking.App
import com.fitness.tracking.domain.exceptions.FileReaderException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets


/**
 * Provides helping android static methods
 */
object AndroidUtils {
    /**
     * Remove empty space of the picture
     * @param bmp
     * @return
     */

    fun trimBitmap(bmp: Bitmap?): Bitmap? {
        if (bmp == null) {
            return null
        }
        val imgHeight = bmp.height
        val imgWidth = bmp.width
        //TRIM WIDTH - LEFT
        var startWidth = 0
        for (x in 0 until imgWidth) {
            if (startWidth == 0) {
                for (y in 0 until imgHeight) {
                    if (bmp.getPixel(x, y) != Color.TRANSPARENT) {
                        startWidth = x
                        break
                    }
                }
            } else break
        }
        //TRIM WIDTH - RIGHT
        var endWidth = 0
        for (x in imgWidth - 1 downTo 0) {
            if (endWidth == 0) {
                for (y in 0 until imgHeight) {
                    if (bmp.getPixel(x, y) != Color.TRANSPARENT) {
                        endWidth = x
                        break
                    }
                }
            } else break
        }
        //TRIM HEIGHT - TOP
        var startHeight = 0
        for (y in 0 until imgHeight) {
            if (startHeight == 0) {
                for (x in 0 until imgWidth) {
                    if (bmp.getPixel(x, y) != Color.TRANSPARENT) {
                        startHeight = y
                        break
                    }
                }
            } else break
        }
        //TRIM HEIGHT - BOTTOM
        var endHeight = 0
        for (y in imgHeight - 1 downTo 0) {
            if (endHeight == 0) {
                for (x in 0 until imgWidth) {
                    if (bmp.getPixel(x, y) != Color.TRANSPARENT) {
                        endHeight = y
                        break
                    }
                }
            } else break
        }
        return Bitmap.createBitmap(
                bmp,
                startWidth,
                startHeight,
                endWidth - startWidth,
                endHeight - startHeight
        )
    }

    /**
     * Return string value from a specify file
     * @param filename
     * @param context
     * @return
     * @throws FileReaderException
     */
    @Throws(FileReaderException::class)
    fun readFile(filename: String, context: Context): String {
        var reader: BufferedReader? = null
        var result = ""
        try {
            reader = BufferedReader(
                    InputStreamReader(context.assets.open(filename), StandardCharsets.UTF_8))
            var mLine: String
            while (reader.readLine().also { mLine = it } != null) {
                result += mLine + "\n"
            }
        } catch (e: IOException) {
            throw FileReaderException(e.message)
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    throw FileReaderException(e.message)
                }
            }
        }
        return result
    }

    /**
     * Get string from id
     * @param id
     * @return
     */
    fun getString(id: Int): String {
        return App.instance!!.resources.getString(id)
    }

    fun getDrawable(id: Int): Drawable? {
        return ResourcesCompat.getDrawable(App.instance!!.resources, id, null)
    }

    fun loadJSONFromAsset(context: Context, filename: String): String? {
        var json: String?
        json = try {
            val `is` = context.assets.open(filename)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun getVersionApp(): String {
        try {
            App.appContext?.let {
                val pInfo: PackageInfo = App.appContext?.packageManager?.getPackageInfo(it.packageName, 0)!!
                return pInfo.versionName
            } ?: return ""
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }
}