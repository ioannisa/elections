package eu.anifantakis.elections.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import eu.anifantakis.elections.R
import eu.anifantakis.elections.sharedviewmodel.SharedViewModel
import eu.anifantakis.elections.databinding.FragmentMainBinding
import eu.anifantakis.elections.sharedviewmodel.SharedViewModelFactory

class MainFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    // we use a shared view model because we want the assigned repository
    // to be accessible across every fragment
    private val sharedViewModel: SharedViewModel by activityViewModels{
        SharedViewModelFactory(requireActivity().application)
    }

    private val viewModel: MainViewModel by viewModels{
        MainViewModelFactory(sharedViewModel.electionsRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = DataBindingUtil.inflate<FragmentMainBinding>(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        // Setup Recycler View
        val adapter = ElectionAdapter(ElectionAdapter.ElectionClickListener {
            viewModel.onPartyItemClicked(it)
        })
        binding.resultsRecycler.adapter = adapter

        viewModel.navigateToPartyDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                // clear the event
                viewModel.onPartyDetailNavigated()

                // navigate to the detail screen of the party
                this.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailFragment(it)
                )
            }
        })

        binding.swiperefresh.setOnRefreshListener(this)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onRefresh() {
        sharedViewModel.refreshData()
    }
}