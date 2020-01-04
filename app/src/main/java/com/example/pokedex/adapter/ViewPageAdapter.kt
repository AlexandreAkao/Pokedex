package com.example.pokedex.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.pokedex.fragments.FragmentMoves
import com.example.pokedex.fragments.FragmentRecl
import com.example.pokedex.fragments.FragmentStatus
import com.example.pokedex.modal.Pokemon

class ViewPageAdapter(fragmentManager: FragmentManager, val pokemonId: Int) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment = when (position) {
            0 -> FragmentMoves.newInstance(pokemonId)
            1 -> FragmentRecl.newInstance(pokemonId)
            2 -> FragmentStatus.newInstance(pokemonId)
            else -> FragmentMoves.newInstance(pokemonId)
        }

        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val title: String = when (position) {
            0 -> "HABILIDADES"
            1 -> "EVOLUÇÃO"
            2 -> "STATUS"
            else -> "noun"
        }

        return title
    }
}