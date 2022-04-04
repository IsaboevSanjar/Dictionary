package uz.sanjar.dictionarysql.core.model

data class Words(
    val id: Int,
    var eng: String,
    var uz: String,
    var description: String,
    var fav: Int,
    var history: Int,
    var added: Int,
    var enuz: Int,
    var isOpen: Boolean = false,
    var isExpended: Boolean = false
)