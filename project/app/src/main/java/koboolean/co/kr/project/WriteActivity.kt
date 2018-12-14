package koboolean.co.kr.project

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_write.*
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class WriteActivity : AppCompatActivity() {
    var web_id : String? = null
    var dirPath : String? = null
    var contentURI : Uri? = null
    var pic_path : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        var intent = getIntent()
        web_id = intent.getStringExtra("user_id")


        var tempPath = Environment.getExternalStorageDirectory().absolutePath
        dirPath = "${tempPath}/Android/data/${packageName}"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.write_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.menu_camera ->{
                var camera_intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                var fileName = "temp_${System.currentTimeMillis()}_${web_id}.jpg"
                var picPath = "${dirPath}/${fileName}"

                var file = File(picPath)

                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
                    contentURI = FileProvider.getUriForFile(this, "koboolean.co.kr.project.file_provider", file)
                }else{
                    contentURI = Uri.fromFile(file)
                }
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, contentURI)
                startActivityForResult(camera_intent , 1)
            }
            R.id.menu_gallery -> {
                var gallery_intent = Intent(Intent.ACTION_PICK)
                gallery_intent.type = MediaStore.Images.Media.CONTENT_TYPE
                startActivityForResult(gallery_intent, 2)
            }
            R.id.menu_upload -> {
                var thread = NetworkThread()
                thread.start()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                var bitmap = BitmapFactory.decodeFile(contentURI?.path)

                bitmap = resizeBitmap(1024, bitmap)
                var degree = getDegree(contentURI?.path)

                pic_path = contentURI?.path
                rebuildImage(bitmap, pic_path!!, degree)

                imageView2.setImageBitmap(bitmap)
                imageView2.rotation = degree
            }
        }else if(requestCode == 2){
            if(resultCode == Activity.RESULT_OK){
                var c = contentResolver.query(data?.data, null, null, null, null)
                c.moveToNext()

                var index = c.getColumnIndex(MediaStore.Images.Media.DATA)
                var source = c.getString(index);



                var bitmap = BitmapFactory.decodeFile(source)

                bitmap = resizeBitmap(1024,bitmap)
                var degree = getDegree(source)

                var fileName = "temp_${System.currentTimeMillis()}_${web_id}.jpg"
                pic_path = "${dirPath}/${fileName}"

                rebuildImage(bitmap, pic_path!!, degree)

                imageView2.setImageBitmap(bitmap)
                imageView2.rotation = degree
            }
        }
    }

    fun resizeBitmap(targetWidth : Int, source : Bitmap):Bitmap{
        var ratio = source.height.toDouble() / source.width.toDouble()
        var targetHeight = (targetWidth * ratio).toInt()

        var result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false)
        if(result != source){
            source.recycle()
        }
        return result
    }

    fun getDegree(path : String?) : Float{
        var exif = ExifInterface(path)
        var degree = 0
        var ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)
        when(ori){
            ExifInterface.ORIENTATION_ROTATE_90 ->{
                degree = 90
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                degree = 180
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                degree = 270
            }
        }
        return degree.toFloat()
    }

    fun rebuildImage(source: Bitmap, path: String, angle:Float){

        var matrix = Matrix()
        matrix.postRotate(angle)

        var bitmap2 = Bitmap.createBitmap(source, 0,0, source.width, source.height, matrix, false)

        var out = FileOutputStream(path)
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.flush()
        out.close()

    }

    inner class NetworkThread : Thread(){
        override fun run() {
            var client = OkHttpClient()
            var request_builder = Request.Builder()
            var url = request_builder.url("http://203.244.145.214:8084/kobooleanWeb/uploadFood.jsp")
            // 멀티파트 폼 데이터 지정
            var mutipart_builder = MultipartBody.Builder()
            mutipart_builder.setType(MultipartBody.FORM)
            mutipart_builder.addFormDataPart("user_id", web_id)
            mutipart_builder.addFormDataPart("food_write",food_write.text.toString())
            mutipart_builder.addFormDataPart("price_write",price_write.text.toString())

            var file = File(pic_path)
            mutipart_builder.addFormDataPart("mobile_img", file.name, RequestBody.create(MultipartBody.FORM, file))
            var body = mutipart_builder.build()

            var post = url.post(body)

            var request = post.build()
            var callback1 = Callback_Food()

            client.newCall(request).enqueue(callback1)
        }
    }
    inner class Callback_Food : Callback{
        override fun onFailure(call: Call, e: IOException) {

        }

        override fun onResponse(call: Call, response: Response) {
            var result = response?.body()?.string()

            if(result?.trim().equals("OK")){
                finish()
            }
        }

    }
}
