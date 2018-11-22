package koboolean.co.kr.project

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_get__gps.*

class Get_GPSActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get__gps)

        var intent = getIntent()
        var name = intent.getStringExtra("name")
        textView2.text = name + "님 반갑습니다."
        var web_id = intent.getStringExtra("web_id")
    }
}
