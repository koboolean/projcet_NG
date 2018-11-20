package koboolean.co.kr.project

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    var permission_list = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Thread.sleep(2000)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        requestPermissions(permission_list, 0)
        }else{
            Thread.sleep(2000)
            startActivity(Intent(this, LoginActivity::class.java))
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for(result in grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return
            }
        }
        Thread.sleep(2000)
        startActivity(Intent(this, LoginActivity::class.java))
    }



}
