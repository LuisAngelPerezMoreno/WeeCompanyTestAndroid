package com.example.weecompanytest.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weecompanytest.MainActivity
import com.example.weecompanytest.R
import com.example.weecompanytest.adapters.CountryBinder
import com.example.weecompanytest.adapters.ICountryBinder
import com.example.weecompanytest.models.Country
import com.example.weecompanytest.services.country.CountryViewModel
import com.example.weecompanytest.services.country.CountryViewModelType
import com.example.weecompanytest.services.country.ICountryViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.main_fragment.view.*
import mva2.adapter.ListSection
import mva2.adapter.MultiViewAdapter
import java.util.*


class ListCountriesFragment : Fragment(), ICountryViewModel, ICountryBinder {

    companion object {
        fun newInstance() = ListCountriesFragment()
    }

    private lateinit var adapter: MultiViewAdapter
    private lateinit var countryBinder: CountryBinder
    private lateinit var rootView: View
    private lateinit var viewModel: CountryViewModel
    private var countriesListSection = ListSection<Country>()
    private lateinit var countrySelect: Country
    private var positionCountrySelect: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = CountryViewModel(requireContext(), this)
        viewModel.getCountrys()

        countryBinder = CountryBinder(this)
        countriesListSection = ListSection()
        adapter = MultiViewAdapter()
        adapter.registerItemBinders(countryBinder)
        adapter.addSection(countriesListSection)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.main_fragment, container, false)
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rootView.rv_country.layoutManager = layoutManager
        rootView.rv_country.adapter = adapter
        return rootView
    }

    override fun seleccion(item: Country, position: Int) {
        val countryUpdate = countriesListSection.get(position)
        countryUpdate.isSelect = true
        countriesListSection.set(position, countryUpdate)

        if (positionCountrySelect != null && positionCountrySelect != position){
            val countryDeselect = countriesListSection.get(positionCountrySelect!!)
            countryDeselect.isSelect = false
            countriesListSection.set(positionCountrySelect!!, countryDeselect)
        }

        countrySelect = item
        positionCountrySelect = position
        showDialogCouponAlert()
    }


    fun showDialogCouponAlert(){
        val dialog = MaterialAlertDialogBuilder(requireContext()).setView(R.layout.dialog_country)
            .show()
        val title = dialog.findViewById<TextView>(R.id.tv_title)
        val descriptionTwo = dialog.findViewById<TextView>(R.id.tv_description_two)

        title!!.text = "Click, ${countrySelect.country}"
        descriptionTwo!!.text = "pais de ${countrySelect.country}"

        dialog.setOnDismissListener {
            val uri: String =
                java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", MainActivity.lat, MainActivity.lng)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            requireContext().startActivity(intent)
        }
    }

    override fun response(type: CountryViewModelType, success: Boolean, message: String?) {
        if (type == CountryViewModelType.Listado){
            countriesListSection.addAll(viewModel.listado)
            rootView.loanding.visibility = View.GONE
            rootView.cl_container.visibility = View.VISIBLE
        }
    }

}