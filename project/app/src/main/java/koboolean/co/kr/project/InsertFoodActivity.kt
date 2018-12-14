package koboolean.co.kr.project

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_insert_food.*
import okhttp3.*
import org.json.JSONArray
import java.io.File
import java.io.IOException
import java.net.URL
import java.util.*
import kotlin.collections.HashMap

class InsertFoodActivity : AppCompatActivity() {

    var web_id : String? = null

    var  listData = ArrayList<HashMap<String, Any>>()
    var imageMap = HashMap<String, Bitmap>()

    var permission_list = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_food)

        var intent = getIntent()
        web_id = intent.getStringExtra("user_id")

        var adapter = ListAdapter()
        main_list.adapter = adapter
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            requestPermissions(permission_list, 0)
        }else{
            init()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    inner class ListAdapter : BaseAdapter(){
        override fun getCount(): Int {
            return listData.size
        }

        override fun getItem(p0: Int): Any {
            return 0
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var convertView = p1
            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.row_list, null)
            }
            var img1 = convertView?.findViewById<ImageView>(R.id.imageView)
            var str1 = convertView?.findViewById<TextView>(R.id.food)
            var str2 = convertView?.findViewById<TextView>(R.id.price)

            var map = listData.get(p0)

            var mobile_img = map.get("mobile_img") as String
            var mobile_str1 = map.get("mobile_str1") as String
            var mobile_str2 = map.get("mobile_str2") as String

            var bitmap:Bitmap? = imageMap.get(mobile_img)

            if(bitmap == null){

                var thread2 = ImageNetworkThread(mobile_img as String)
                thread2.start()
            }else{
                img1?.setImageBitmap(bitmap)
            }
            str1?.text = mobile_str1
            str2?.text = mobile_str2

            return convertView!!
        }
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_write -> {
                var intent = Intent(this,WriteActivity::class.java)
                intent.putExtra("user_id", web_id)
                startActivity(intent)
            }
            R.id.menu_reload ->{
                var thread = getDataThread()
                thread.start()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun init() {
        var tempPath = Environment.getExternalStorageDirectory().absolutePath
        var dirPath = "${tempPath}/Android/data/${packageName}"

        var file = File(dirPath)
        if(file.exists() == false){
            file.mkdir()
        }

        var adapter = main_list.adapter as ListAdapter
        adapter.notifyDataSetChanged()

        main_list.setOnItemClickListener{ adapterView, view, i, l ->
            var intent = Intent(this, DetailActivity::class.java)
            var map = listData.get(i) as HashMap<String, Any>
            var mobile_idx = map.get("mobile_idx") as Int
            intent.putExtra("mobile_idx", mobile_idx)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        var thread = getDataThread()
        thread.start()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        for(result in grantResults){
            if(result == PackageManager.PERMISSION_DENIED){
                return
            }
        }
        init()
    }
    inner class getDataThread : Thread(){
        override fun run() {
            var client = OkHttpClient()
            var builder = Request.Builder()
            var url = builder.url("http://203.244.145.214:8084/kobooleanWeb/get_list_food.jsp")

            var bodyBuilder = FormBody.Builder()

            bodyBuilder.add("user_id",web_id)

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

            listData.clear()

            Log.i("hello",result)

            var array = JSONArray(result)

            for (i in 0 until array.length()) {
                var obj = array.getJSONObject(i)

                var mobile_idx = obj.getInt("mobile_idx")
                var mobile_img = obj.getString("mobile_img")
                var mobile_str1 = obj.getString("mobile_str1")
                var mobile_str2 = obj.getString("mobile_str2")

                var map = HashMap<String, Any>()
                map.put("mobile_idx", mobile_idx)
                map.put("mobile_img", mobile_img)
                map.put("mobile_str1", mobile_str1)
                map.put("mobile_str2", mobile_str2)

                listData.add(map)
            }

            runOnUiThread {
                var adapter = main_list.adapter as ListAdapter
                adapter.notifyDataSetChanged()
            }
        }
    }
    inner class ImageNetworkThread(var fileName : String) : Thread(){
        override fun run() {
            var url = URL("http://203.244.145.214:8084/kobooleanWeb/food/${fileName}")

            var connection = url.openConnection()
            var stream = connection.getInputStream()

            var bitmap = BitmapFactory.decodeStream(stream)

            imageMap.put(fileName, bitmap)

            runOnUiThread {
                var adapter = main_list.adapter as ListAdapter
                adapter.notifyDataSetChanged()
            }
        }
    }
}













