package koboolean.co.kr.project

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import kotlinx.android.synthetic.main.activity_get_gps_get_post.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class GetGPS_getPost : AppCompatActivity() {

    var web_id : String? = null
    var locManager : LocationManager? = null
    var order_latis : String? = null
    var order_longs : String? = null

    var permission_list = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_gps_get_post)


        var intent = getIntent()
        web_id = intent.getStringExtra("user_id")

        var user_name = intent.getStringExtra("user_name")

        user_id.text = user_name + "님 반갑습니다."


        web_server_gps_pushs.setOnClickListener { view ->
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(permission_list, 0)
            }else {

                getByLocation()
            }
        }
        insert_food.setOnClickListener { view ->
            var intent = Intent(this, InsertFoodActivity::class.java)
            intent.putExtra("user_id", web_id)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for(result in grantResults){
            if(result == PackageManager.PERMISSION_DENIED){
                return
            }
        }
        getByLocation()
    }

    inner class NetworkThread : Thread(){
        override fun run() {
            var client = OkHttpClient()
            // 요청할 페이지 주소
            var builder = Request.Builder()

            var url = builder.url("http://203.244.145.214:8084/kobooleanWeb/insertGPS.jsp")

            var bodyBuilder = FormBody.Builder()

            bodyBuilder.add("order_id",web_id)
            bodyBuilder.add("order_latis",order_latis)
            bodyBuilder.add("order_longs",order_longs)

            var body = bodyBuilder.build()
            var post = url.post(body)

            var request = post.build()

            client.newCall(request).enqueue(CallBack1())
        }
    }
    inner class CallBack1 : Callback {
        // 서버와의 통신이 실패되었을 때
        override fun onFailure(call: Call, e: IOException) {
            textView4.text = "네트워크 통신을 실패하였습니다."
        }
        // 서버와의 통신이 잘 마무리 되었을 때
        override fun onResponse(call: Call, response: Response) {
            var result = response?.body()?.string()

            runOnUiThread{
                Log.i("this",result);
                var obj = JSONObject(result)
                var name = obj.getString("order_id")
                if(!name.equals("?")){
                    textView4.text = "위치정보 저장 완료"
                }else{
                    textView4.text = "위치정보 저장 실패"
                }
            }
        }
    }

    // GPS 측정이 시간이 걸릴 수 있기에, 이전에 찍은 곳에 먼저 찍고 측정 이후 그곳으로 이동한다.
    fun setMyLocation(location : Location){

        Log.d("test123", "위도 : ${location.latitude}")
        Log.d("test123", "경도 : ${location.longitude}")

        order_latis = location.latitude.toString()
        order_longs = location.longitude.toString()

        var thread = NetworkThread()
        thread.start()

    }

    fun getByLocation(){
        locManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
                return
            }
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED){
                return
            }
        }
        // 네트워크와 GPS 두개 지정 후 GPS를 못받는 위치라면 NETWORK를 사용한다.
        var location = locManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        var location2 =locManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if(location != null){
            setMyLocation(location)
        }else{
            if(location2 != null){
                setMyLocation(location2)
            }
        }

        var listner = GetMyLocationListner()
        // GPS 요청으로 측정이 가능할 때
        if(locManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)!! == true)
            locManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10f, listner)
        // NETWORK로 측정할 때
        else if(locManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)!! == true)
            locManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10f, listner)
    }

    inner class GetMyLocationListner : LocationListener {
        // GPS 측정이 성공하였다...
        override fun onLocationChanged(p0: Location?) {
            setMyLocation(p0!!)
            locManager?.removeUpdates(this)
        }

        override fun onProviderDisabled(p0: String?) {

        }

        override fun onProviderEnabled(p0: String?) {

        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

        }
    }

}
