package converter

fun main() {

    val convertor = Convertor()
    var fromUnit: String
    var toUnit: String
    while (true) {
        print("Enter what you want to convert (or exit): ")
        val input = readln()
        if (input.lowercase() == "exit") {
            println(); break
        }
        else convertor.convert(input.lowercase())
        println()
    }
}