package com.example.pokedex.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager
import com.example.pokedex.R
import com.example.pokedex.adapter.ViewPageAdapter
import com.example.pokedex.fragments.FragmentMoves
import com.example.pokedex.fragments.FragmentRecl
import com.example.pokedex.fragments.FragmentStatus
import com.example.pokedex.modal.Pokemon
import com.example.pokedex.services.RetrofitInitializer
import com.example.pokedex.util.Type
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonDetailsActivity : AppCompatActivity(), FragmentStatus.OnFragmentInteractionListener, FragmentMoves.OnFragmentInteractionListener, FragmentRecl.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
    }

    private var pokemonId = 0
    private val link = "https://www.pokemon.com/br/pokedex/"
    private lateinit var pokemonImagem: ImageView
    private lateinit var pokemonNome: TextView
    private lateinit var pokemonExp: TextView
    private lateinit var pokemonPeso: TextView
    private lateinit var pokemonAltura: TextView
    private lateinit var pokeId: TextView
    private lateinit var tabs: TabLayout
    private lateinit var viewPage: ViewPager
    private lateinit var viewpageadapter: ViewPageAdapter
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var shareButton: ImageView
    private val shareIntent = Intent(android.content.Intent.ACTION_SEND)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        pokemonId = intent.getIntExtra("pokemon_position", 0)

        pokemonNome      = findViewById(R.id.pokemon_nome)
        pokemonImagem    = findViewById(R.id.pokemon_imagem)
        constraintLayout = findViewById(R.id.constraintLayout_details)
        shareButton      = findViewById(R.id.share_button)
        tabs             = findViewById(R.id.details_poketab)
        viewPage         = findViewById(R.id.viewpage_details)
        pokeId           = findViewById(R.id.poke_id)
        pokemonExp       = findViewById(R.id.base_exp)
        pokemonPeso      = findViewById(R.id.pokemon_peso)
        pokemonAltura    = findViewById(R.id.pokemon_altura)

        pokeId.text = pokemonNumber(pokemonId)

        carregarPokemon(pokemonId)

        viewpageadapter = ViewPageAdapter(supportFragmentManager, pokemonId)
        viewPage.adapter = viewpageadapter

        tabs.setupWithViewPager(viewPage)

        shareButton.setOnClickListener {
            shareIntent.setType("text/*")
            startActivity(Intent.createChooser(shareIntent, "Compartilhar"))
        }
    }

    fun carregarPokemon(pokemonNumber: Int) {
        val call = RetrofitInitializer.pokeApi.pokeService().pokemon(pokemonNumber.toString())

        call.enqueue(object: Callback<Pokemon> {
            @SuppressLint("DefaultLocale")
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                response.body()?.let {
                    pokemonNome.text   = it.name
                    pokemonExp.text    = "BaseXp: ${it.base_experience}xp"
                    pokemonPeso.text   = "Peso: ${it.weight.toDouble()/10}kg"
                    pokemonAltura.text = "Altura: ${it.height.toDouble()/10}m"

                    Picasso.get().load(it.sprites.front_default).into(pokemonImagem)
                    val msg = "Gostei muito do pokemon ${it.name} ${link}${it.name}"

                    if(it.types.size != 1) {
                        constraintLayout.setBackgroundResource(Type.valueOf(it.types[1].type.name.toUpperCase()).getColor())
                    } else {
                        constraintLayout.setBackgroundResource(Type.valueOf(it.types[0].type.name.toUpperCase()).getColor())
                    }

                    it.types.forEach { type ->
                        findViewById<LinearLayout>(Type.valueOf(type.type.name.toUpperCase()).getLinearLayout()).visibility = View.VISIBLE
                    }

                    shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, msg)
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg)
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
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
}
