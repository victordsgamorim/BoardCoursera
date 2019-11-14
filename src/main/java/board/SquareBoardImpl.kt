package board

open class SquareBoardImpl(override val width: Int) : SquareBoard {

    private val cells = mutableSetOf<Cell>()

    init {

        for (r in 1..width) {
            for (c in 1..width) cells.add(Cell(r, c))
        }

    }

    override fun getCellOrNull(i: Int, j: Int): Cell? =
            cells.firstOrNull { (row, column) -> row == i && column == j }


    override fun getCell(i: Int, j: Int): Cell =
            cells.first { (row, column) -> row == i && column == j }


    override fun getAllCells(): Collection<Cell> = cells


    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val row = cells.filter { cell -> cell.i == i && cell.j in jRange }
        if (jRange.first > jRange.last) return row.reversed()
        return row
    }


    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val column = cells.filter { cell -> cell.j == j && cell.i in iRange }
        if (iRange.first > iRange.last) return column.reversed()
        return column
    }


    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            Direction.UP -> getCellOrNull(this.i - 1, this.j)
            Direction.DOWN -> getCellOrNull(this.i + 1, this.j)
            Direction.RIGHT -> getCellOrNull(this.i, this.j + 1)
            Direction.LEFT -> getCellOrNull(this.i, this.j - 1)
        }
    }
}