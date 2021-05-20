package org.grupotres.appcongreso.ui.lectures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.firebase.auth.FirebaseAuth
import org.grupotres.appcongreso.databinding.FragmentLectureDetailBinding

class LectureDetailFragment : Fragment() {

	private var binding: FragmentLectureDetailBinding? = null
	private val args: LectureListFragmentArgs by navArgs()
	lateinit var auth: FirebaseAuth

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentLectureDetailBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initLectureDetails()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	private fun initLectureDetails() {
		binding?.lectureTitle?.text = args.lectureSpeaker.lecture.title
		binding?.speakerName?.text = args.lectureSpeaker.speakers[0].name
		binding?.speakerPhoto?.load(args.lectureSpeaker.speakers[0].uriPhoto)
		binding?.btnEnroll?.setOnClickListener { enrollToLecture() }
	}

	private fun enrollToLecture() {

		auth = FirebaseAuth.getInstance()

		val userEmail = auth.currentUser?.email

		val mail: String = userEmail.toString()
		val message: String = "Hola, este es un mensaje de verificacion de su inscripcion al congreso"
		val subject: String = "App Congreso "


		//Send Mail
		val javaMailAPI = JavaMailAPI(context, mail, subject, message)

		javaMailAPI.execute()
		//Toast.makeText(requireContext(), "Se envio un mensaje de verificacion a su correo", Toast.LENGTH_SHORT).show()
		//VERIFICAR CON FIREBASE
		//val user = auth.currentUser
		//user.sendEmailVerification()

	}
}