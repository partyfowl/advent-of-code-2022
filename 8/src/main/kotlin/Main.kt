import java.io.File

class Forest(trees: Array<IntArray>){
    val rows = trees
    val columns: Array<MutableList<Int>> = Array(rows[0].size) { mutableListOf() }

    init {
        for (y in trees.indices) for (x in trees[y].indices) {
            columns[x].add(trees[y][x])
        }
    }
}

class Tree(forest: Forest, x: Int, y: Int) {
    private val height = forest.columns[x][y]
    var unobstructedView = false
    var scenicScore = 1

    private fun checkSlice(slice: List<Int>) {
        var obstructed = false
        var visibleTrees = 0
        for ((index, treeHeight) in slice.withIndex()) {
            visibleTrees = index + 1
            if (treeHeight >= this.height) {
                obstructed = true
                break
            }
        }

        if (!obstructed) this.unobstructedView = true
        this.scenicScore *= visibleTrees
    }

    init {
        // NORTH
        checkSlice(forest.columns[x].slice((y-1 downTo 0)))

        // SOUTH
        checkSlice(forest.columns[x].slice((y+1 until forest.columns[x].size)))

        // WEST
        checkSlice(forest.rows[y].slice((x-1 downTo 0)))

        // EAST
        checkSlice(forest.rows[y].slice((x+1 until forest.rows[x].size)))
    }
}

fun main() {
    val forest = Forest(File("input.txt").readLines().map { line -> line.map { it.digitToInt() }.toIntArray()}.toTypedArray())

    var treesWithAView = 0
    var highestScenicScore = 0

    for (x in forest.columns.indices) for (y in forest.rows.indices) {
        var tree = Tree(forest, x, y)
        if (tree.unobstructedView) treesWithAView++
        if (tree.scenicScore > highestScenicScore) highestScenicScore = tree.scenicScore
    }

    println("Part 1: $treesWithAView")
    println("Part 2: $highestScenicScore")
}