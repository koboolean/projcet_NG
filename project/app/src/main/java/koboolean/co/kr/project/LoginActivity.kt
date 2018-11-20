package koboolean.co.kr.project

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var authStateListner : FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authStateListner = FirebaseAuth.AuthStateListener { firebaseAuth ->
            var user = firebaseAuth.currentUser
            if(user != null){
                startActivity(Intent(this, GoogleMapActivity::class.java))
            }
        }


        email_signin_button.setOnClickListener { view ->
            createId()
        }
        email_login_button.setOnClickListener { view ->
            loginId()
        }

        // 구글 로그인
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        var googleSignInClient = GoogleSignIn.getClient(this,gso)
        google_login_button.setOnClickListener { view ->
            var signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent,1)
        }

    }

    fun createId(){
        if(email_edittext.text.toString().isEmpty() || password_edittext.text.isEmpty()) {
            Toast.makeText(this, "회원가입 정보를 입력하세요", Toast.LENGTH_LONG).show()
        }
        else{
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_edittext.text.toString(), password_edittext.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "회원가입 성공", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
        }
    }
    fun loginId(){
        if(email_edittext.text.toString().isEmpty() || password_edittext.text.isEmpty()){
            Toast.makeText(this, "로그인 정보를 입력하세요", Toast.LENGTH_LONG).show()
        }else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email_edittext.text.toString(), password_edittext.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            // 구글 로그인에 성공했을 때 넘어오는 토큰 값을 가지고 있는 Task
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            // ApiException 캐스팅
            var account = task.getResult(ApiException::class.java)
            // Credentail 구글 로그인에 성공했다는 인증서 생성
            var credential = GoogleAuthProvider.getCredential(account.idToken,null)
            // 구글 아이디 파이어베이스에 등록
            FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "구글 아이디 연동이 성공하였습니다.", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
        }
    }

    override fun onResume() {
        super.onResume()
        FirebaseAuth.getInstance().addAuthStateListener(authStateListner!!)
    }
    // 구글로그인은 onStop을 실행하지 않기에 onPause를 사용한다.
    override fun onPause() {
        super.onPause()
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListner!!)
    }
}
