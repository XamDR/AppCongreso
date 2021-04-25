package org.grupotres.appcongreso.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.grupotres.appcongreso.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

	private var binding: FragmentLoginBinding? = null
	private val loginViewModel by viewModels<LoginViewModel>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentLoginBinding.inflate(inflater, container, false)
		loginViewModel.text.observe(viewLifecycleOwner, {
			binding?.textLogin?.text = it
		})
		return binding?.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}
}