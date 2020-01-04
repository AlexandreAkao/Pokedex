package com.example.pokedex.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.pokedex.R
import com.example.pokedex.adapter.AdapterEvolution
import com.example.pokedex.modal.Pokemon
import com.example.pokedex.modal.PokemonEvolution
import com.example.pokedex.modal.evolution.Evolution
import com.example.pokedex.modal.evolution.EvolvesTo
import com.example.pokedex.modal.specie.Specie
import com.example.pokedex.services.RetrofitInitializer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_fragment_evolution.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentRecl : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterEvolution

    private var listener: OnFragmentInteractionListener? = null

    override fun onResume() {
        super.onResume()
        arguments?.let {
            pokemonSpecies(it.getInt("id"))

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_fragment_recl, container, false)

        recyclerView = root.findViewById(R.id.recycler_evolution)
        recyclerView.layoutManager = LinearLayoutManager(root.context)

        adapter = AdapterEvolution(root.context)

        recyclerView.adapter = adapter

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
            FragmentRecl().apply {
                arguments = Bundle().apply {
                    putInt("id", pokemon)
                }
            }
    }

    fun pokemonSpecies(pokemonNumber: Int) {
        val call = RetrofitInitializer.pokeApi.pokeService().pokemonSpecie(pokemonNumber.toString())

        call.enqueue(object: Callback<Specie> {
            override fun onResponse(call: Call<Specie>, response: Response<Specie>) {
                response.body()?.let {
                    val chain = it.evolution_chain.url.substring(42)

                    evolutionChain(chain)
                }
            }

            override fun onFailure(call: Call<Specie>, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }

    fun evolutionChain(endPoint: String) {
        val call = RetrofitInitializer.pokeApi.pokeService().evolution(endPoint)

        call.enqueue(object: Callback<Evolution> {
            override fun onResponse(call: Call<Evolution>, response: Response<Evolution>) {
                response.body()?.let { evolution ->
                    val chain = evolution.chain
                    val pokeList = mutableListOf<PokemonEvolution>()

                    chainRecursivo(chain, pokeList)

                    adapter.updateList(pokeList)
                }
            }

            override fun onFailure(call: Call<Evolution>, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }

    fun chainRecursivo(chain: EvolvesTo, list: MutableList<PokemonEvolution>) {
        val namePoke = chain.species.name

        chain.evolves_to.forEach {
            if (it.evolves_to.size != 0) {
                chainRecursivo(it, list)
            }
            list.add(PokemonEvolution(namePoke, it.species.name))
        }
    }
}
