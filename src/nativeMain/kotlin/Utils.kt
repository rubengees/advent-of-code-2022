import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.newFixedThreadPoolContext

fun readInputFile(day: Int): String {
    val name = "day$day.txt"

    return inlineResources[name] ?: error("$name not found")
}

@OptIn(ExperimentalStdlibApi::class)
fun workerDispatcher(): CloseableCoroutineDispatcher {
    return newFixedThreadPoolContext(Platform.getAvailableProcessors(), "parallel")
}
