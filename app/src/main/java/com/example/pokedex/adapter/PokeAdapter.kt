package com.example.pokedex.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.solver.widgets.ConstraintAnchor
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.activity.MainActivity
import com.example.pokedex.modal.ListPokemon
import com.example.pokedex.modal.Name_Url
import com.example.pokedex.modal.Pokemon
import com.example.pokedex.services.RetrofitInitializer
import com.example.pokedex.util.Type
import com.google.android.material.chip.ChipGroup
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokeAdapter(private val context: Context, val listener: MainActivity): RecyclerView.Adapter<PokeAdapter.PokeViewHolder>() {

    private val pokemons = mutableListOf<Pokemon>()
    private val sharedPref: SharedPreferences = context.getSharedPreferences("poke-favoritos", 0)

    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val view = layoutInflater.inflate(R.layout.poke_cardview, parent, false)

        return PokeViewHolder(view, context, listener)
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        val pokemon = pokemons[position]
        var ids = if (sharedPref.all["favIds"].toString() == "null") "".split(", ").toMutableList() else sharedPref.all["favIds"].toString().split(", ").toMutableList()
        var isFav = false
    pokemon.id
        Picasso.get().load(pokemon.sprites.front_default).into(holder.imagem)
        holder.nome.text = pokemonNumber(pokemon.id) + " - " + pokemon.name

        if(pokemon.types.size != 1) {
            holder.cardview.setBackgroundResource(Type.valueOf(pokemon.types[1].type.name.toUpperCase()).getColor())
        } else {
            holder.cardview.setBackgroundResource(Type.valueOf(pokemon.types[0].type.name.toUpperCase()).getColor())
        }

        holder.fav.setColorFilter(ContextCompat.getColor(context, R.color.WHITE), android.graphics.PorterDuff.Mode.SRC_IN)

        ids.forEach{
            if (it == pokemon.id.toString()) {
                holder.fav.setColorFilter(ContextCompat.getColor(context, R.color.RED), android.graphics.PorterDuff.Mode.SRC_IN)
                isFav = true
                return@forEach
            }
        }

        holder.fav.setOnClickListener {
            var update = ""

            if (isFav) {

                isFav = false

                ids = if (sharedPref.all["favIds"].toString() == "null") "".split(", ").toMutableList() else sharedPref.all["favIds"].toString().split(", ").toMutableList()
                ids = (ids.filter {

                    it != pokemon.id.toString()
                }).toMutableList()

                holder.fav.setColorFilter(ContextCompat.getColor(context, R.color.WHITE), android.graphics.PorterDuff.Mode.SRC_IN)

                update = ids.joinToString()

            } else {

                isFav = true

                ids = if (sharedPref.all["favIds"].toString() == "null") "".split(", ").toMutableList() else sharedPref.all["favIds"].toString().split(", ").toMutableList()
                ids.add(pokemon.id.toString())

                holder.fav.setColorFilter(ContextCompat.getColor(context, R.color.RED), android.graphics.PorterDuff.Mode.SRC_IN)

                update = ids.joinToString()
            }

            sharedPref.edit().putString("favIds", update).apply()
        }

        holder.itemView.setOnClickListener{
            listener.onClick(it, pokemon.id)
        }

    }

    class PokeViewHolder(view: View, context: Context, listener: PokemonClickListener): RecyclerView.ViewHolder(view) {
        val imagem: ImageView = view.findViewById(R.id.pokeImagem)
        val nome: TextView = view.findViewById(R.id.poke_name)
        val cardview: CardView = view.findViewById(R.id.poke_cardview)
        val fav: ImageView = view.findViewById(R.id.fav_pokemon)
    }

    fun updateList(pokemons: List<Pokemon>) {
        val count = pokemons.count()
        this.pokemons.addAll(pokemons.subList(count - 20, count))
        this.pokemons.sortBy { it.id }
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

    fun updateFav(pokemons: List<Pokemon>) {
        this.pokemons.clear()
        this.pokemons.addAll(pokemons)
        notifyDataSetChanged()
    }

    fun clear() {
        this.pokemons.clear()
        notifyDataSetChanged()
    }
}