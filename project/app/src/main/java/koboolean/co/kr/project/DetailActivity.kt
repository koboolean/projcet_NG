package koboolean.co.kr.project

import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_insert_food.*
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class DetailActivity : AppCompatActivity() {
    var mobile_idx : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mobile_idx = intent.getIntExtra("mobile_idx", 0)

        var thread = getDataThread()
        thread.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detale_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_delete -> {
                var editTextNewPassword = EditText(this)
                editTextNewPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                var alertDialog = AlertDialog.Builder(this)
                alertDialog.setTitle("삭제")
                alertDialog.setMessage("음식 정보를 삭제하시겠습니까?")
                alertDialog.setPositiveButton("삭제",{dialogInterface, i -> deleteMenu()})
                alertDialog.setNegativeButton("취소",{dialogInterface, i -> dialogInterface.dismiss()})
                alertDialog.show()
            }
            R.id.menu_upload ->{
                var editTextNewPassword = EditText(this)
                editTextNewPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                var alertDialog = AlertDialog.Builder(this)
                alertDialog.setTitle("업로드")
                alertDialog.setMessage("음식 정보를 재 업로드 하시겠습니까?")
                alertDialog.setPositiveButton("업로드",{dialogInterface, i -> uploadMenu()})
                alertDialog.setNegativeButton("취소",{dialogInterface, i -> dialogInterface.dismiss()})
                alertDialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun uploadMenu(){
        if(editText.text.toString().isEmpty() || editText2.text.toString().isEmpty()){
            Toast.makeText(this, "빈 정보가 들어가 있습니다. \n정보를 비워놓지 마세요", Toast.LENGTH_LONG).show()
        }else {
            var thread = reUploadThread()
            thread.start()
        }
    }
    fun deleteMenu(){
        var thread = DeleteThread()
        thread.start()
    }

    inner class reUploadThread : Thread(){
        override fun run() {
            var client = OkHttpClient()
            var builder = Request.Builder()

            var url = builder.url("http://203.244.145.214:8084/kobooleanWeb/get_Reupload.jsp")

            var bodyBuilder = FormBody.Builder()

            bodyBuilder.add("mobile_idx", "${mobile_idx}")
            bodyBuilder.add("mobile_food", editText.text.toString())
            bodyBuilder.add("mobile_pri", editText2.text.toString())
            var body = bodyBuilder.build()
            var post = url.post(body)

            var request = post.build()

            var callback = DeleteCallback()
            client.newCall(request).enqueue(callback)
        }
    }

    inner class DeleteThread : Thread(){
        override fun run() {
            var client = OkHttpClient()
            var builder = Request.Builder()

            var url = builder.url("http://203.244.145.214:8084/kobooleanWeb/postDelete_food.jsp")

            var bodyBuilder = FormBody.Builder()
            bodyBuilder.add("mobile_idx", "${mobile_idx}")

            var body = bodyBuilder.build()
            var post = url.post(body)

            var request = post.build()

            var callback = DeleteCallback()
            client.newCall(request).enqueue(callback)
        }
    }
    // 삭제를 눌렸을 경우 data를 삭제를 한 후 Callback으로 들어오는 값을 확인하고, 값이 있다면 삭제 완료
    inner class DeleteCallback : Callback{
        override fun onFailure(call: Call, e: IOException) {

        }

        override fun onResponse(call: Call, response: Response) {
            var result = response?.body()?.string()

            var obj = JSONObject(result)

            // database상에 data를 삭제 시킨 후 확인
            var resultOk = obj.getString("delete_ok")
            if(resultOk.equals("OK")){
                finish()
            }else{
                Toast.makeText(applicationContext, "통신이 원활하지 않습니다.", Toast.LENGTH_LONG).show()
            }

        }

    }

    inner class getDataThread : Thread(){
        override fun run() {
            var client = OkHttpClient()
            var builder = Request.Builder()

            var url = builder.url("http://203.244.145.214:8084/kobooleanWeb/get_data_food.jsp")

            var bodyBuilder = FormBody.Builder()
            bodyBuilder.add("mobile_idx", "${mobile_idx}")

            var body = bodyBuilder.build()
            var post = url.post(body)

            var request = post.build()

            var callback = Callback1()
            client.newCall(request).enqueue(callback)
        }
    }
    inner class Callback1 : Callback{
        override fun onFailure(call: Call, e: IOException) {

        }

        override fun onResponse(call: Call, response: Response) {
         var result = response?.body()?.string()

            var obj = JSONObject(result)

            var mobile_img = obj.getString("mobile_img")
            var mobile_str1 = obj.getString("mobile_str1")
            var mobile_str2 = obj.getString("mobile_str2")

            var imageThread = ImageNetworkThread(mobile_img)
            imageThread.start()

            runOnUiThread {
                editText.hint = mobile_str1
                editText2.hint = mobile_str2
            }
        }

    }
    inner class ImageNetworkThread(var fileName : String) : Thread(){
        override fun run() {
            var url = URL("http://203.244.145.214:8084/kobooleanWeb/food/${fileName}")

            var connection = url.openConnection()
            var stream = connection.getInputStream()

            var bitmap = BitmapFactory.decodeStream(stream)

            runOnUiThread {
                imageView2.setImageBitmap(bitmap)
            }
        }
    }
}















