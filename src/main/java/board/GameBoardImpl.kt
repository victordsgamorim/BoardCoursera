package board

class GameBoardImpl<T>(width: Int) : SquareBoardImpl(width), GameBoard<T> {

    private val cellElements = mutableMapOf<Cell, T?>()

    init {

        for (r in 1..width) {

            for (c in 1..width) {
                val cell = getCell(r, c)
                cellElements[cell] = null
            }
        }
    }

    override fun get(cell: Cell): T? = cellElements.getValue(cell)

    override fun set(cell: Cell, value: T?) {
        cellElements[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean) =
            cellElements.filterValues { element -> predicate.invoke(element) }.keys

    override fun find(predicate: (T?) -> Boolean) =
            cellElements.filterValues { element -> predicate.invoke(element) }.keys.firstOrNull()

    override fun any(predicate: (T?) -> Boolean) = cellElements.values.any(predicate)

    override fun all(predicate: (T?) -> Boolean) = cellElements.values.all(predicate)


}