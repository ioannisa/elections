package eu.anifantakis.elections.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import eu.anifantakis.elections.R
import eu.anifantakis.elections.databinding.FragmentMainBinding
import eu.anifantakis.elections.detail.DetailViewModel
import eu.anifantakis.elections.detail.DetailViewModelFactory
import eu.anifantakis.elections.network.NETWORK_STATUS
import eu.anifantakis.elections.sharedviewmodel.SharedViewModel
import eu.anifantakis.elections.sharedviewmodel.SharedViewModelFactory

class MainFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    // we use a shared view model because we want the assigned repository
    // to be accessible across every fragment
    private val sharedViewModel: SharedViewModel by activityViewModels{
        SharedViewModelFactory(requireActivity().application)
    }
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = DataBindingUtil.inflate<FragmentMainBinding>(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this

        val viewModelFactory = MainViewModelFactory(sharedViewModel.electionsRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
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

        // Display a "No Network" message if unable to download data
        viewModel.networkStatus.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (viewModel.networkStatus.value==NETWORK_STATUS.DISCONNECTED && viewModel.notifyAboutStatus.value == true) {
                    // clear the event
                    viewModel.onNetworkStatusNotified()

                    // display the notification
                    Toast.makeText(context, getText(R.string.no_network), Toast.LENGTH_SHORT).show()
                }
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