data class File(val name: String, val size: Int)

data class Dir(
    val name: String,
    val parent: Dir? = null
) {
    private val dirs = mutableListOf<Dir>()
    private val files = mutableListOf<File>()

    val size: Int
        get() {
            return this.dirs.sumOf { it.size } + files.sumOf { it.size }
        }

    fun addFile(file: File) {
        this.files.add(file)
    }

    fun addDir(name: String) {
        this.dirs.add(Dir(name, this))
    }

    fun subDir(name: String): Dir {
        return this.dirs.find { it.name == name } ?: error("Unknown dir $name")
    }

    fun sequence(): Sequence<Dir> = sequence {
        yield(this@Dir)

        for (dir in dirs) {
            yieldAll(dir.sequence())
        }
    }
}

val FILE_REGEX = Regex("^(\\d+) (.*)$")

fun parse(input: String): Dir {
    val root = Dir("/")
    var current = root

    for (line in input.lines()) {
        if (line == "$ cd /") {
            current = root
        } else if (line == "$ ls") {
            continue
        } else if (line == "$ cd ..") {
            current = current.parent ?: error("Dir ${current.name} has no parent")
        } else if (line.startsWith("$ cd ")) {
            current = current.subDir(line.substring(5))
        } else if (line.startsWith("dir")) {
            current.addDir(line.substring(4))
        } else if (line.matches(FILE_REGEX)) {
            val match = FILE_REGEX.matchEntire(line) ?: error("Line did not match regex")
            val size = match.groupValues[1].toInt()
            val name = match.groupValues[2]

            current.addFile(File(name, size))
        } else {
            error("Unknown command $line")
        }
    }

    return root
}

fun day7Part1(input: String): String {
    return parse(input).sequence().filter { it.size <= 100_000 }.sumOf { it.size }.toString()
}

fun day7Part2(input: String): String {
    val fileSystem = parse(input)

    val usedSpace = fileSystem.size
    val unusedSpace = 70_000_000 - usedSpace
    val requiredSpace = 30_000_000 - unusedSpace

    return fileSystem.sequence().filter { it.size >= requiredSpace }.minOf { it.size }.toString()
}
