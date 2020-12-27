package me.saks.advent.helpers

// https://www.redblobgames.com/grids/hexagons/#coordinates-cube
data class HexCell(val x: Int, val y: Int, val z: Int) {
    // Directions are for the 'pointy' type of hex grid
    fun east(): HexCell {
        return copy(x = x + 1, y = y - 1)
    }
    fun west(): HexCell {
        return copy(x = x - 1, y = y + 1)
    }
    fun southWest(): HexCell {
        return copy(x = x - 1, z = z + 1)
    }
    fun southEast(): HexCell {
        return copy(y = y - 1, z = z + 1)
    }
    fun northWest(): HexCell {
        return copy(y = y + 1, z = z - 1)
    }
    fun northEast(): HexCell {
        return copy(x = x + 1, z = z - 1)
    }
    fun neighbours(): List<HexCell> {
        return listOf(east(), west(), southWest(), southEast(), northWest(), northEast())
    }
}
