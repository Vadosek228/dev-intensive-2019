package ru.skillbranch.devintensive.extensions

val htmlTagRegex = """<.*?>""".toRegex()
val moreTwoSpacesRegex = """\s{2,}""".toRegex()

fun String.truncate(count: Int = 16): String {
    if (this.trim().length <= count) return this.trim()
    return this.substring(0, count).trim() + "..."
}

fun String.stripHtml(): String {
    val result = htmlTagRegex.replace(this, "")
    return moreTwoSpacesRegex.replace(result, " ")
}