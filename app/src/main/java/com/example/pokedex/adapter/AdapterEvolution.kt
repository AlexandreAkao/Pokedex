package com.example.pokedex.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.modal.Pokemon
import com.example.pokedex.modal.PokemonEvolution
import com.example.pokedex.services.RetrofitInitializer
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterEvolution(private val context: Context): RecyclerView.Adapter<AdapterEvolution.PokeViewHolder>() {

    private val pokemons = mutableListOf<PokemonEvolution>()
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val view = layoutInflater.inflate(R.layout.card_evolution, parent, false)

        return PokeViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        val pokemon = pokemons[position]

        holder.name1.text = pokemon.pokemon1
        holder.name2.text = pokemon.pokemon2
        carregarPokemon(pokemon.pokemon1, holder.imagem1, holder.id1)
        carregarPokemon(pokemon.pokemon2, holder.imagem2, holder.id2)
    }

    class PokeViewHolder(view: View, context: Context): RecyclerView.ViewHolder(view) {
        val imagem1: ImageView = view.findViewById(R.id.poke_1)
        val imagem2: ImageView = view.findViewById(R.id.poke_2)
        val name1: TextView = view.findViewById(R.id.poke_name_evolution1)
        val name2: TextView = view.findViewById(R.id.poke_name_evolution2)
        val id1: TextView = view.findViewById(R.id.poke_id_evolution1)
        val id2: TextView = view.findViewById(R.id.poke_id_evolution2)
    }

    fun updateList(pokemonEvolution: List<PokemonEvolution>) {
        pokemons.clear()
        pokemons.addAll(pokemonEvolution.reversed())
        notifyDataSetChanged()
    }

    fun pokemonNumber(number: Int): String {
        if (number < 10) {
            return "#00$number"
        } else if (number < 100) {
            return "#0$number"
        } else {
            return "#$number"
        }
    }

    fun carregarPokemon(pokemonName: String, imageView: ImageView, textView: TextView) {
        val call = RetrofitInitializer.pokeApi.pokeService().pokemon(pokemonName)

        call.enqueue(object: Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                response.body()?.let {
                    Picasso.get().load(it.sprites.front_default).into(imageView)
                    textView.text = pokemonNumber(it.id)
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }

}