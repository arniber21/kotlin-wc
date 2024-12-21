package org.example

import java.io.FileInputStream

class Entry {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
//            parseArgs("kwc -c test.txt".split(" ").toTypedArray())
//            parseArgs("kwc -l test.txt".split(" ").toTypedArray())
//            parseArgs("kwc -w test.txt".split(" ").toTypedArray())
//            parseArgs("kwc -m test.txt".split(" ").toTypedArray())
            parseArgs(args)
        }
    }
}

fun parseArgs(args: Array<String>) {
    assert(args.isNotEmpty())
    assert(args.size > 2)
    val fileName = args[2]
    val fileReader = FileInputStream(fileName)
    when (args[1]) {
        "-c" -> {
            val characters = countBytes(fileReader)
            println("$characters $fileName")
        }
        "-l" -> {
            val lines = countLines(fileReader)
            println("$lines $fileName")
        }
        "-w" -> {
            val words = countWords(fileReader)
            println("$words $fileName")
        }
        "-m" -> {
            val characters = countCharacters(fileReader)
            println("$characters $fileName")
        }
        else -> {
            println("Invalid argument")
        }
    }
    fileReader.close()
}

fun countBytes(content: FileInputStream): Int {
    return content.use {
        var accum = 0
        while (it.read() != -1) accum++
        return accum
    }
}

fun countLines(content: FileInputStream): Int {
    return content.use {
        var accum = 0
        var char: Int
        do {
            char = content.read()
            if (char.toChar() == '\n') {
                accum++
            }
        } while(char != -1)
        return accum
    }
}

fun countWords(content: FileInputStream): Int {
    return content.use {
        var accum = 0
        var char: Int
        var line = StringBuilder()
        do {
            char = content.read()
            if (char.toChar() != '\n' && char != -1) {
                line.append(char.toChar())
            }

            if (char.toChar() == '\n' || char == -1) {
                // Convert the line into a string
                val lineString = line.toString().trim()
                // Split the line into words using regex to handle multiple spaces
                if (lineString.isNotEmpty()) {
                    val words = lineString.split("\\s+".toRegex())
                    // Count the number of words
                    accum += words.size
                }
                // Clear the line
                line.clear()
            }
        } while (char != -1)
        return accum
    }
}

fun countCharacters(content: FileInputStream): Int {
    return content.use {
        var accum = 0
        var char: Int
        do {
            char = content.read()
            if (char.toChar() != '\n' && char != -1) {
                accum++
            }
        } while(char != -1)
        return accum
    }
}