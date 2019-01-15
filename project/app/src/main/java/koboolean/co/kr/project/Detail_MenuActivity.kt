package koboolean.co.kr.project

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import koboolean.co.kr.project.R.id.detail_menu_item
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail__menu.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class Detail_MenuActivity : AppCompatActivity() {
    var mobile_idx : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail__menu)

        mobile_idx = intent.getIntExtra("mobile_idx", 0)

        var thread = getDataThread()
        thread.start()

        insert_menu.setOnClickListener { view ->

        }

        finish_menu.setOnClickListener { view ->

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
    inner class Callback1 : Callback {
        override fun onFailure(call: Call, e: IOException) {

        }

        override fun onResponse(call: Call, response: Response) {
            var result = response?.body()?.string()

            var obj = JSONObject(result)

            var mobile_img = obj.getString("mobile_img")
            var mobile_str1 = obj.getString("mobile_str1")


            var imageThread = ImageNetworkThread(mobile_img)
            imageThread.start()

            runOnUiThread {
                detail_menu_item.text = "음식 명 : " + mobile_str1
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
                detail_menu.setImageBitmap(bitmap)
            }
        }
    }
}
