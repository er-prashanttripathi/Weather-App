package com.app.ecom.weatherApp.ui.mainActivity.fragments.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.ecom.weatherApp.databinding.FragmentSearchBinding
import com.app.ecom.weatherApp.factories.SharedVMF
import com.app.ecom.weatherApp.helpers.PreferenceManager
import com.app.ecom.weatherApp.viewModels.SharedVM
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext


class SearchFragment : Fragment(), KodeinAware {
    private lateinit var binding: FragmentSearchBinding
    override val kodeinContext = kcontext<Fragment>(this)
    override val kodein: Kodein by kodein()
    private val factory: SharedVMF by instance()
    private lateinit var viewModel: SharedVM
    private lateinit var PreferenceManager: PreferenceManager

    var search_text = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        binding.textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {


                if (s.length < 3) {
                } else {
//                    getSearchProduct(searchlistRequest(s.toString(),PreferenceManager.userid.toString()))
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        PreferenceManager = com.app.ecom.weatherApp.helpers.PreferenceManager.instance
        viewModel = ViewModelProvider(requireActivity(), factory)[SharedVM::class.java]
        setupViews()
    }

    private fun setupViews() {

        binding.btnBack.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }


}