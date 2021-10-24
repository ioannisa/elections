package eu.anifantakis.elections.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import eu.anifantakis.elections.databinding.FragmentDetailBinding
import eu.anifantakis.elections.main.MainViewModel
import eu.anifantakis.elections.main.MainViewModelFactory
import eu.anifantakis.elections.sharedviewmodel.SharedViewModel
import eu.anifantakis.elections.sharedviewmodel.SharedViewModelFactory


class DetailFragment : Fragment() {

    // we use a shared view model because we want the assigned repository
    // to be accessible across every fragment
    private val sharedViewModel: SharedViewModel by activityViewModels{
        SharedViewModelFactory(requireActivity().application)
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val partyResult = DetailFragmentArgs.fromBundle(requireArguments()).selectedParty

        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModelFactory = DetailViewModelFactory(sharedViewModel.electionsRepository, partyResult)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        binding.viewModel = viewModel


        binding.electionResult = partyResult

        // Inflate the layout for this fragment
        return binding.root
    }
}