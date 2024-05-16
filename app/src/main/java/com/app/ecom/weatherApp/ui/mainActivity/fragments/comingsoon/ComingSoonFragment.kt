package com.app.ecom.weatherApp.ui.mainActivity.fragments.comingsoon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.ecom.weatherApp.databinding.FragmentComingSoonBinding
import com.app.ecom.weatherApp.factories.SharedVMF
import com.app.ecom.weatherApp.helpers.PreferenceManager
import com.app.ecom.weatherApp.viewModels.SharedVM
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

    class ComingSoonFragment : Fragment() , KodeinAware {
        private lateinit var binding: FragmentComingSoonBinding
        override val kodeinContext = kcontext<Fragment>(this)
        override val kodein: Kodein by kodein()
        private val factory: SharedVMF by instance()
        private lateinit var viewModel: SharedVM
        private lateinit var preferenceManager: PreferenceManager

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = FragmentComingSoonBinding.inflate(layoutInflater)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            preferenceManager = PreferenceManager.instance
            viewModel = ViewModelProvider(requireActivity(), factory)[SharedVM::class.java]
            setupViews()
        }

        private fun setupViews() {
            setHasOptionsMenu(true)
//        (activity as MainActivity?)?.setToolbarVisibility(false)
            val clickType = arguments?.getString("type").toString()
        }
    }