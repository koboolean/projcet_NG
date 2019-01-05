package koboolean.co.kr.project

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_insert_food.*
import kotlinx.android.synthetic.main.activity_nfc_tag_get.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class NfcTagGetActivity : AppCompatActivity() {
    var area_id : String? = null
    var area_table_num : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc_tag_get)
        var intent : Intent = getIntent()
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            var uri: Uri = intent.getData()
            area_id = uri.getQueryParameter("area_id")
            var area_table_num = uri.getQueryParameter("area_table_num")
        }
        var thread = NfcByIdThread()
        thread.start()

    }

    inner class NfcByIdThread : Thread(){
        override fun run() {
            var client = OkHttpClient()
            var builder = Request.Builder()
            var url = builder.url("http://203.244.145.214:8084/kobooleanWeb/checkId.jsp")

            var bodyBuilder = FormBody.Builder()

            bodyBuilder.add("user_id",area_id)

            var body = bodyBuilder.build()
            var post = url.post(body)

            var request = post.build()

            var callback = Callback1()
            client.newCall(request).enqueue(callback)
        }
    }
    inner class Callback1 : Callback {
        override fun onFailure(call: Call?, e: IOException?) {

        }

        override fun onResponse(call: Call?, response: Response?) {
            var result = response?.body()?.string()



            Log.i("hello", result)

            var obj = JSONObject(result)
            if (obj.getString("area_name").equals("?")) {
                Toast.makeText(applicationContext, "현재 네트워크가 문제가 있습니다.", Toast.LENGTH_LONG).show()
            } else
            {
                areaName.text =  "음식점 명 : " + obj.getString("area_name")
            }
        }
    }
}


