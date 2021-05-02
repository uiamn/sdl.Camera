package jp.ac.titech.itpro.sdl.camera

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import jp.ac.titech.itpro.sdl.camera.MainActivity

class MainActivity : AppCompatActivity() {
    private var photoImage: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val photoButton = findViewById<Button>(R.id.photo_button)
        photoButton.setOnClickListener { v: View? ->
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val manager = packageManager
            @SuppressLint("QueryPermissionsNeeded") val activities = manager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            if (!activities.isEmpty()) {
                startActivityForResult(intent, REQ_PHOTO)
            } else {
                Toast.makeText(this@MainActivity, R.string.toast_no_activities, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showPhoto() {
        if (photoImage == null) {
            return
        }
        val photoView = findViewById<ImageView>(R.id.photo_view)
        photoView.setImageBitmap(photoImage)
    }

    override fun onActivityResult(reqCode: Int, resCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resCode, data)
        if (reqCode == REQ_PHOTO) {
            if (resCode == RESULT_OK) {
                photoImage = data?.extras?.get("data") as Bitmap
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showPhoto()
    }

    companion object {
        private const val REQ_PHOTO = 1234
    }
}