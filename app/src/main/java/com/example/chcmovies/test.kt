import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun testflow() = flow<Int> {
    for (i in 1..3)
        emit(i)
}

fun main() = runBlocking {

    testflow().collect { i ->
        println(i)

    }
}