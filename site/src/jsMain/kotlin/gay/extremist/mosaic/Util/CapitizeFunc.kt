package gay.extremist.mosaic.Util

fun String.capitalize(): String {
    return this.split(" ")
    .joinToString(" "){word: String ->
        word.replaceFirstChar {
            it.uppercase()
        }
    }.split("-")
    .joinToString("-") { word: String ->
        word.replaceFirstChar {
            it.uppercase()
        }
    }
}