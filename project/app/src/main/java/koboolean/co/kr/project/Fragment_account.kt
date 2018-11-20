package koboolean.co.kr.project


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import koboolean.co.kr.project.dataDAO.UserDAO
import kotlinx.android.synthetic.main.fragment_user_interface.*
import kotlinx.android.synthetic.main.fragment_user_interface.view.*


class Fragment_account : Fragment() {
    var fragmentView: View? = null
    var userDAO : UserDAO? = null
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

        return fragmentView
    }


}
