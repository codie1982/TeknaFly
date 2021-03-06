package com.erol.teknafly.ui.Detail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erol.teknafly.R
import com.erol.teknafly.data.model.Satellite
import com.erol.teknafly.ui.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.recycler_item.*

class DetailFragment : Fragment() {

    private  var selectedId = 0
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
          selectedId = DetailFragmentArgs.fromBundle(it).id
            mainViewModel.setIsLoading(true)
            mainViewModel.getSatelliteDetail(selectedId)
            mainViewModel.getSatellitePosition(selectedId,0)
            mainViewModel.setLastPositionTimer(selectedId)
        }

        observeSharedLiveData()
    }

    @SuppressLint("SetTextI18n")
    private fun observeSharedLiveData() {
        mainViewModel._isLoading.observe(viewLifecycleOwner, Observer {
            if(it){
                hideAll()
                txtLoadingDetail.visibility = View.VISIBLE
                progressBarDetail.visibility = View.VISIBLE
            }else {
                txtLoadingDetail.visibility = View.GONE
                progressBarDetail.visibility = View.GONE
                showAll()
            }
        })

        mainViewModel.selectedSatellite.observe(viewLifecycleOwner, Observer {
            if (it !=null){
                txtName.text = it.name
                txtFirstFlight.text = it.first_flight
                txtCost.text = it.cost_per_launch.toString()
                txtMass.text = "${it.height }/${it.mass}"
                mainViewModel.setIsLoading(false)
            }
        })
        mainViewModel.satellitePosition.observe(viewLifecycleOwner, Observer {
            txtPosition.text = "(${it.X},${it.Y})"
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.stopGetLastPosition()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel = activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }!!
        mainViewModel.setContext(context)
    }
    fun hideAll(){
        txtName.visibility = View.GONE
        txtFirstFlight.visibility = View.GONE
        txtCostTitle.visibility = View.GONE
        txtCost.visibility = View.GONE
        txtMass.visibility = View.GONE
        txtMassTitle.visibility = View.GONE
        txtPosition.visibility = View.GONE
        txtPositionTitle.visibility = View.GONE

    }
    fun showAll(){
        txtName.visibility = View.VISIBLE
        txtFirstFlight.visibility = View.VISIBLE
        txtCostTitle.visibility = View.VISIBLE
        txtCost.visibility = View.VISIBLE
        txtMass.visibility = View.VISIBLE
        txtMassTitle.visibility = View.VISIBLE
        txtPosition.visibility = View.VISIBLE
        txtPositionTitle.visibility = View.VISIBLE
    }
}
