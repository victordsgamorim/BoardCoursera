package games.game2048

import java.util.*

/*
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
*/

fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T?> {
    val list = this.filterNotNull().toMutableList()

    var count = 1

    while (count < list.size) {
        if (list[count] == list[count - 1]) {
            list[count - 1] = merge(list[count])
            list.removeAt(count)
        }

        count++
    }

    return list
}

