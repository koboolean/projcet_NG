package koboolean.co.kr.project


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import koboolean.co.kr.project.dataDAO.UserDAO
import kotlinx.android.synthetic.main.activity_get_gps__for_login.*
import kotlinx.android.synthetic.main.fragment_user_interface.view.*



class Fragment_account : Fragment() {
    var fragmentView: View? = null
    var userDAO : UserDAO? = null
    var web_id : String? = null
    var web_pw : String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        userDAO = UserDAO(FirebaseAuth.getInstance().currentUser?.email)

        fragmentView = inflater.inflate(R.layout.fragment_user_interface, container, false)

        if(FirebaseAuth.getInstance().currentUser?.email == null){
            activity?.finish()
        }

        fragmentView?.logout_button?.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            activity?.finish()
        }
        fragmentView?.checkEmail_button?.setOnClickListener {
            sendEmailVerification()
        }

        fragmentView?.manager_user?.setOnClickListener { view ->
            startActivity(Intent(activity, GetGPS_ForLogin::class.java))
        }

        fragmentView?.changepassword_button?.setOnClickListener {
            var editTextNewPassword = EditText(activity)
            editTextNewPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            var alertDialog = AlertDialog.Builder(activity)
            alertDialog.setTitle("패스워드 변경")
            alertDialog.setMessage("변경하고 싶은 패스워드를 입력하세요")
            alertDialog.setView(editTextNewPassword)
            alertDialog.setPositiveButton("변경",{dialogInterface, i -> changePassword(editTextNewPassword.text.toString())})
            alertDialog.setNegativeButton("취소",{dialogInterface, i -> dialogInterface.dismiss()})
            alertDialog.show()
        }

        return fragmentView
    }

    fun changePassword(password : String){
        FirebaseAuth.getInstance().currentUser!!.updatePassword(password).addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                Toast.makeText(activity, "비밀번호가 변경되었습니다.", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(activity, task.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun sendEmailVerification(){

        if(FirebaseAuth.getInstance().currentUser!!.isEmailVerified){
            Toast.makeText(activity, "이메일 인증이 완료 되었습니다.",Toast.LENGTH_LONG).show()
            return
        }
        FirebaseAuth.getInstance().currentUser!!.sendEmailVerification().addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(activity, "확인 메일을 보냈습니다.", Toast.LENGTH_LONG).show()
                FirebaseAuth.getInstance().signOut()
                var intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(activity, task.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }



}