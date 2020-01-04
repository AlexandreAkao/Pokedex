package com.example.pokedex.util

import com.example.pokedex.R

enum class Type {
    WATER {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_water
        }

        override fun getColor(): Int {
            return R.color.water
        }

        override fun getImagem(): Int {
            return R.drawable.water
        }
    },

    FIRE {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_fire
        }

        override fun getColor(): Int {
            return R.color.fire
        }

        override fun getImagem(): Int {
            return R.drawable.fire
        }
    },

    GRASS {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_grass
        }

        override fun getColor(): Int {
            return R.color.grass
        }

        override fun getImagem(): Int {
            return R.drawable.grass
        }
    },

    GROUND {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_ground
        }

        override fun getColor(): Int {
            return R.color.ground
        }

        override fun getImagem(): Int {
            return R.drawable.ground
        }
    },

    ROCK {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_rock
        }

        override fun getColor(): Int {
            return R.color.rock
        }

        override fun getImagem(): Int {
            return R.drawable.rock
        }
    },

    STEEL {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_steel
        }

        override fun getColor(): Int {
            return R.color.steel
        }

        override fun getImagem(): Int {
            return R.drawable.steel
        }
    },

    ICE {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_ice
        }

        override fun getColor(): Int {
            return R.color.ice
        }

        override fun getImagem(): Int {
            return R.drawable.ice
        }
    },

    ELECTRIC {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_electric
        }

        override fun getColor(): Int {
            return R.color.electric
        }

        override fun getImagem(): Int {
            return R.drawable.electric
        }
    },

    DRAGON {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_dragon
        }

        override fun getColor(): Int {
            return R.color.dragon
        }

        override fun getImagem(): Int {
            return R.drawable.dragon
        }
    },

    GHOST {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_ghost
        }

        override fun getColor(): Int {
            return R.color.ghost
        }

        override fun getImagem(): Int {
            return R.drawable.ghost
        }
    },

    PSYCHIC {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_psychic
        }

        override fun getColor(): Int {
            return R.color.psychic
        }

        override fun getImagem(): Int {
            return R.drawable.psychic
        }
    },

    NORMAL {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_normal
        }

        override fun getColor(): Int {
            return R.color.normal
        }

        override fun getImagem(): Int {
            return R.drawable.normal
        }
    },

    FIGHTING {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_figthing
        }

        override fun getColor(): Int {
            return R.color.fighting
        }

        override fun getImagem(): Int {
            return R.drawable.fighting
        }
    },

    POISON {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_poison
        }

        override fun getColor(): Int {
            return R.color.poison
        }

        override fun getImagem(): Int {
            return R.drawable.poison
        }
    },

    BUG {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_bug
        }

        override fun getColor(): Int {
            return R.color.bug
        }

        override fun getImagem(): Int {
            return R.drawable.bug
        }
    },

    FLYING {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_flying
        }

        override fun getColor(): Int {
            return R.color.flying
        }

        override fun getImagem(): Int {
            return R.drawable.flying
        }
    },

    DARK {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_dark
        }

        override fun getColor(): Int {
            return R.color.dark
        }

        override fun getImagem(): Int {
            return R.drawable.dark
        }
    },

    FAIRY {
        override fun getLinearLayout(): Int {
            return R.id.linear_layout_fairy
        }

        override fun getColor(): Int {
            return R.color.fairy
        }

        override fun getImagem(): Int {
            return R.drawable.fairy
        }

    };

    abstract fun getImagem(): Int

    abstract fun getColor(): Int

    abstract fun getLinearLayout(): Int
}