package com.example.pokedex.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView

import com.example.pokedex.R
import com.example.pokedex.modal.Pokemon
import com.example.pokedex.services.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentStatus : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var hpValue: TextView
    private lateinit var attValue: TextView
    private lateinit var dfsValue: TextView
    private lateinit var speedValue: TextView
    private lateinit var attsValue: TextView
    private lateinit var dfssValue: TextView

    private lateinit var hpBar: ProgressBar
    private lateinit var attBar: ProgressBar
    private lateinit var dfsBar: ProgressBar
    private lateinit var speedBar: ProgressBar
    private lateinit var attsBar: ProgressBar
    private lateinit var dfssBar: ProgressBar

    override fun onResume() {
        super.onResume()
        arguments?.let {
            carregarPokemon(it.getInt("id"))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_fragment_status, container, false)

        hpValue = root.findViewById(R.id.hp_value)
        attValue = root.findViewById(R.id.att_value)
        dfsValue = root.findViewById(R.id.dfs_value)
        speedValue = root.findViewById(R.id.speed_value)
        attsValue = root.findViewById(R.id.atts_value)
        dfssValue = root.findViewById(R.id.dfss_value)

        hpBar = root.findViewById(R.id.hp_bar)
        attBar = root.findViewById(R.id.att_bar)
        dfsBar = root.findViewById(R.id.dfs_bar)
        speedBar = root.findViewById(R.id.speed_bar)
        attsBar = root.findViewById(R.id.atts_bar)
        dfssBar = root.findViewById(R.id.dfss_bar)

        hpBar.max = 300
        attBar.max = 300
        dfsBar.max = 300
        speedBar.max = 300
        attsBar.max = 300
        dfssBar.max = 300


        return root
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(pokemon: Int) =
            FragmentStatus().apply {
                arguments = Bundle().apply {
                    putInt("id", pokemon)
                }
//                carregarPokemon(pokemon)

            }
    }

    fun carregarPokemon(pokemonNumber: Int) {
        val call = RetrofitInitializer.pokeApi.pokeService().pokemon(pokemonNumber.toString())

        call.enqueue(object: Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                response.body()?.let {
                    val stats = it.stats

                    hpValue.text = stats[5].base_stat.toString()
                    attValue.text = stats[4].base_stat.toString()
                    dfsValue.text = stats[3].base_stat.toString()
                    speedValue.text = stats[0].base_stat.toString()
                    attsValue.text = stats[2].base_stat.toString()
                    dfssValue.text = stats[1].base_stat.toString()

                    hpBar.progress = stats[5].base_stat
                    attBar.progress = stats[4].base_stat
                    dfsBar.progress = stats[3].base_stat
                    speedBar.progress = stats[0].base_stat
                    attsBar.progress = stats[2].base_stat
                    dfssBar.progress = stats[1].base_stat

                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }

}
