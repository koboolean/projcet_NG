package koboolean.co.kr.project

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import android.Manifest
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_google_map.*

class GoogleMapActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{

    var googleMap : GoogleMap? = null
    var locManager : LocationManager? = null

    var permission_list = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_map)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permission_list, 0)
        }else {
            init()
        }

        bottom_navigation.setOnNavigationItemSelectedListener(this)
        bottom_navigation.selectedItemId = R.id.action_home
        // 이 화면에서 권한 받기.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_account ->{
                var account = Fragment_account()
                supportFragmentManager.beginTransaction().replace(R.id.main_layout,account).commit()
                return true
            }
            R.id.action_home ->{
                var home = Fragment_home()
                supportFragmentManager.beginTransaction().replace(R.id.main_layout,home).commit()
                return true
            }
            R.id.action_favorite_alarm ->{
                var favorite = Fragment_favorite()
                supportFragmentManager.beginTransaction().replace(R.id.main_layout,favorite).commit()
                return true
            }
            R.id.action_search -> {
                var search = Fragment_search()
                supportFragmentManager.beginTransaction().replace(R.id.main_layout,search).commit()
                return true
            }
        }
        return false
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for(result in grantResults){
            if(result == PackageManager.PERMISSION_DENIED){
                return
            }
        }
        init()
    }



    fun init(){
        var callback = MapReadyCallback()
        var mapFragment = fragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(callback)
    }

    // 구글 지도가 화면상에 뜨기 전까지 시간이 걸리기에, 준비가 완료되었을 때 설정을 해주어야 하기에 준비가 되었는지 확인하는 class를 만들어야 한다.
    inner class MapReadyCallback : OnMapReadyCallback {
        override fun onMapReady(p0: GoogleMap?) {
            googleMap = p0
            getByLocation()
        }
    }



    // GPS 측정이 시간이 걸릴 수 있기에, 이전에 찍은 곳에 먼저 찍고 측정 이후 그곳으로 이동한다.
    fun setMyLocation(location : Location){

        Log.d("test123", "위도 : ${location.latitude}")
        Log.d("test123", "경도 : ${location.longitude}")

        var position = LatLng(location.latitude, location.longitude)


        // 업데이트에 현재 위치의 포지션을 지정한 후 그 값으로 googlemap 위치를 옮긴다.
        var updata1 = CameraUpdateFactory.newLatLng(position)
        // 구글 맵의 줌을 15f로 확대한다.
        var update2 = CameraUpdateFactory.zoomBy(15f)

        googleMap?.moveCamera(updata1)
        googleMap?.animateCamera(update2)

        // 권한 체크는 계속 해주어야 오류가 나지 않는다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
                return
            }
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED){
                return
            }
        }
        // 현재 위치 아이콘을 표시한다.
        googleMap?.isMyLocationEnabled = true

        // 기본 타입의 맵을 연다.
        googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL

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
