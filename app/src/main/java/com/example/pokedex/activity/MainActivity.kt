package com.example.pokedex.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.adapter.PokeAdapter
import com.example.pokedex.adapter.PokemonClickListener
import com.example.pokedex.modal.ListPokemon
import com.example.pokedex.modal.Name_Url
import com.example.pokedex.modal.Pokemon
import com.example.pokedex.services.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity: AppCompatActivity(), PokemonClickListener {

    private lateinit var pokeList: RecyclerView
    private lateinit var adapter: PokeAdapter
    private lateinit var fav: ImageView
    private val pokemons = mutableListOf<Name_Url>()
    private val pokeLive = MutableLiveData<List<Pokemon>>()
    private var lastClickTime = 0L
    private var loading = true
    private var incrementPokemon = 0
    private var isFav = false
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = getSharedPreferences("poke-favoritos", 0)

        pokeList = findViewById(R.id.main_recyclerview)
        pokeList.layoutManager = LinearLayoutManager(this)

        fav = findViewById(R.id.fav_button)
        fav.setColorFilter(ContextCompat.getColor(this, R.color.BLACK), android.graphics.PorterDuff.Mode.SRC_IN)



        adapter = PokeAdapter(this, this)

        pokeList.adapter = adapter

        progress_bar_main.visibility = View.VISIBLE

        var pastVisiblesItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int

        pokeList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!isFav) {
                    if (dy > 0) {

                        visibleItemCount = pokeList.childCount
                        totalItemCount = pokeList.layoutManager!!.itemCount
                        pastVisiblesItems = (pokeList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                        if (loading) {
                            if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                                loading = false
                                carregarApi()
                            }
                        }
                    }
                }
            }
        })

        fav.setOnClickListener {
            val ids = (sharedPref.all["favIds"] as CharSequence).split(", ").toMutableList().filterNot { it == "" }
            val pokemonFav = mutableListOf<Pokemon>()
            isFav = !isFav

            if (isFav) {
                fav.setColorFilter(ContextCompat.getColor(this, R.color.RED), android.graphics.PorterDuff.Mode.SRC_IN)
                loadPokemonFav(ids, pokemonFav)

            } else {
                fav.setColorFilter(ContextCompat.getColor(this, R.color.BLACK), android.graphics.PorterDuff.Mode.SRC_IN)
                incrementPokemon = 0
                adapter.clear()
                carregarApi()
            }
        }

        carregarApi()

        pokeLive.observe(this, Observer {
            adapter.updateList(it)
            progress_bar_main.visibility = View.GONE
        })
    }

    override fun onClick(view: View, id: Int) {
        if (SystemClock.elapsedRealtimeNanos() - lastClickTime < 800) {
            return
        }
        lastClickTime = SystemClock.elapsedRealtime()

        val it = Intent(this, PokemonDetailsActivity::class.java)

        it.putExtra("pokemon_position", id)
        startActivity(it)
    }

    fun carregarApi() {
        val call = RetrofitInitializer.pokeApi.pokeService().ListPokemon(incrementPokemon * 20)

        call.enqueue(object: Callback<ListPokemon> {
            override fun onResponse(call: Call<ListPokemon>, response: Response<ListPokemon>) {
                response.body()?.let {
                    pokemons.addAll(it.results)

                    urlToPokemon(it.results)
                    incrementPokemon++
                    loading = true
                }
            }

            override fun onFailure(call: Call<ListPokemon>, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }

    fun urlToPokemon(lista: List<Name_Url>) {
        progress_bar_main.visibility = View.VISIBLE

        val poke = mutableListOf<Pokemon>()
        var count = 0

        lista.forEach {
            val path = it.url.substring(34)
            val call = RetrofitInitializer.pokeApi.pokeService().pokemon(path)

            call.enqueue(object: Callback<Pokemon> {
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    response.body()?.let { currentPokemon ->
                        poke.add(currentPokemon)
                        count++

                        if(count === 20) {
                            pokeLive.postValue(poke)
                        }
                    }
                }

                override fun onFailure(call: Call<Pokemon>, t: Throwable?) {
                    Log.e("onFailure error", t?.message)

                    count++

                    if(count === 20) {
                        pokeLive.postValue(poke)
                    }
                }
            })
        }
    }

    fun loadPokemonFav(pokemonNumber: List<String>, pokemonFav: MutableList<Pokemon>) {

        var count = 0

        pokemonNumber.forEach {
            val call = RetrofitInitializer.pokeApi.pokeService().pokemon(it)

            call.enqueue(object: Callback<Pokemon> {
                @SuppressLint("DefaultLocale")
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    response.body()?.let {
                        pokemonFav.add(it)
                        count++

                        if (count == pokemonNumber.size) {
                            pokemonFav.sortBy { it.id }

                            adapter.updateFav(pokemonFav)
                        }
                    }
                }

                override fun onFailure(call: Call<Pokemon>, t: Throwable?) {
                    Log.e("onFailure error", t?.message)
                    count++

                    if (count == pokemonNumber.size) {
                        pokemonFav.sortBy { it.id }

                        adapter.updateFav(pokemonFav)
                    }
                }
            })
        }

    }
}
