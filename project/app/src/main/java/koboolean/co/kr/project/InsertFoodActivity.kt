package koboolean.co.kr.project

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_insert_food.*
import java.io.File

class InsertFoodActivity : AppCompatActivity() {

    var web_id : String? = null

    var  listData = ArrayList<HashMap<String, Any>>()

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

            var mobile_img = map.get("mobile_img") as Int
            var mobile_str1 = map.get("mobile_str1") as String
            var mobile_str2 = map.get("mobile_str2") as String

            img1?.setImageResource(mobile_img)
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
        var map1 = HashMap<String, Any>()

        map1.put("mobile_img", android.R.drawable.ic_menu_agenda)
        map1.put("mobile_str1", "짜장면")
        map1.put("mobile_str2", "5000원")

        listData.add(map1)

        var adapter = main_list.adapter as ListAdapter
        adapter.notifyDataSetChanged()

        main_list.setOnItemClickListener{ adapterView, view, i, l ->
            startActivity(Intent(this, DetailActivity::class.java))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        for(result in grantResults){
            if(result == PackageManager.PERMISSION_DENIED){
                return
            }
        }
        init()
    }
}
