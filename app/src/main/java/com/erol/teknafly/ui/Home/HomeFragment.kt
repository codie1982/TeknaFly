package com.erol.teknafly.ui.Home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.erol.teknafly.R
import com.erol.teknafly.data.di.SatelliteListener
import com.erol.teknafly.data.model.Satellite
import com.erol.teknafly.ui.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(),SatelliteListener {

    private var satellites : ArrayList<Satellite> = ArrayList<Satellite>()
    private var tempSatellites : ArrayList<Satellite> = ArrayList<Satellite>()
    private lateinit var satelliteAdapter:SatelliteAdapter
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        view.edtTxtSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                println("onQueryTextSubmit->"+ p0.toString() )
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                var searchtext = p0.toString()
                tempSatellites.clear()
                if(searchtext.length >2){
                    if(satellites !=null){
                        satellites.forEach {
                            if(it.name.toLowerCase(Locale.getDefault()).contains(searchtext)){
                                tempSatellites.add(it)
                            }
                        }
                    }
                    satelliteAdapter.setData(tempSatellites)
                }else{
                    satelliteAdapter.setData(satellites)
                }
                return true
            }
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.setIsLoading(true)
        mainViewModel.getSatellites()
        satelliteAdapter = SatelliteAdapter(this)
        satelliteList.adapter = satelliteAdapter
        satelliteList.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        observeSharedLiveData()
    }

    private fun observeSharedLiveData() {
        mainViewModel._isLoading.observe(viewLifecycleOwner, Observer {
           if(it){
                hideAll()
               txtLoading.visibility = View.VISIBLE
               progressBar.visibility = View.VISIBLE

           }else {
               println("showAll")
               txtLoading.visibility = View.GONE
               progressBar.visibility = View.GONE
               showAll()
           }
        })
        mainViewModel.satellites.observe(viewLifecycleOwner, Observer {
            var nList = it as ArrayList<Satellite>
            satellites = nList
            satelliteAdapter.setData(nList)
            this.mainViewModel.setIsLoading(false)
        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel = activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }!!
        mainViewModel.setContext(context)
    }
    fun hideAll(){
        edtTxtSearch.visibility = View.GONE
        satelliteList.visibility = View.GONE
    }
    fun showAll(){
        edtTxtSearch.visibility = View.VISIBLE
        satelliteList.visibility = View.VISIBLE
    }

    override fun onSatelliteItemClick(id: Int) {
        var action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(id)
        findNavController().navigate(action)
    }

}