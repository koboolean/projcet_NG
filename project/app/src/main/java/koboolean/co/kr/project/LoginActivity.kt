package koboolean.co.kr.project

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class LoginActivity : AppCompatActivity() {

    var authStateListner : FirebaseAuth.AuthStateListener? = null
    var callbackManager : CallbackManager? = null
    var auth : FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        callbackManager = CallbackManager.Factory.create() // 페이스북 로그인이 완료됬을 때 토큰을 받는부분
        auth = FirebaseAuth.getInstance();
        authStateListner = FirebaseAuth.AuthStateListener { firebaseAuth ->
            var user = firebaseAuth.currentUser
            if(user != null){
                startActivity(Intent(this, GoogleMapActivity::class.java))
            }


        }

        printHashKey(this)

        email_signin_button.setOnClickListener { view ->
            createId()
        }
        email_login_button.setOnClickListener { view ->
            loginId()
        }
        facebook_login_button.setOnClickListener { view ->
            facebookLogin()
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

    fun facebookLogin(){
        LoginManager
                .getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile","email"))
        LoginManager
                .getInstance()
                .registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
                    override fun onSuccess(result: LoginResult?) {
                        handleFacebookAccessToken(result?.accessToken)
                    }

                    override fun onCancel() {

                    }

                    override fun onError(error: FacebookException?) {

                    }


                })
    }
    fun handleFacebookAccessToken(token: AccessToken?){
        var credential = FacebookAuthProvider.getCredential(token?.token!!)
        auth?.signInWithCredential(credential)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode,resultCode,data)
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

    fun printHashKey(pContext: Context) {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i("hello123", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("hello123", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("hello123", "printHashKey()", e)
        }
    }

}