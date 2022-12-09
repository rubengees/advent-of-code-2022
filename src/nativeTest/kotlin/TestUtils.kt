import okio.FileSystem
import okio.Path.Companion.toPath

const val RESOURCES_DIR = "./src/nativeTest/resources"

fun readInputFile(day: Int): String {
    return FileSystem.SYSTEM.read("$RESOURCES_DIR/day$day.txt".toPath()) { readUtf8() }.trim()
}
