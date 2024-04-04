package converter

enum class Units(val type: String, val aliases: List<String>, val singular: String, val plural: String) {

    METER("distance", aliases = listOf("m", "meter", "meters"), "meter", "meters"),
    KILOMETER("distance", aliases = listOf("km", "kilometer", "kilometers"), "kilometer", "kilometers"),
    CENTIMETER("distance", aliases = listOf("cm", "centimeter", "centimeters"), "centimeter", "centimeters"),
    MILLIMETER("distance", aliases = listOf("mm", "millimeter", "millimeters"), "millimeter", "millimeters"),
    MILE("distance", aliases = listOf("mi", "mile", "miles"), "mile", "miles"),
    YARD("distance", aliases = listOf("yd", "yard", "yards"), "yard", "yards"),
    FOOT("distance", aliases = listOf("ft", "foot", "feet"), "foot", "feet"),
    INCH("distance", aliases = listOf("in", "inch", "inches"), "inch", "inches"),

    GRAM("weight", aliases = listOf("g", "gram", "grams"), "gram", "grams"),
    KILOGRAM("weight", aliases = listOf("kg", "kilogram", "kilograms"), "kilogram", "kilograms"),
    MILLIGRAM("weight", aliases = listOf("mg", "milligram", "milligrams"), "milligram", "milligrams"),
    POUND("weight", aliases = listOf("lb", "pound", "pounds"), "pound", "pounds"),
    OUNCE("weight", aliases = listOf("oz", "ounce", "ounces"), "ounce", "ounces"),

    CELSIUS(
        "degrees",
        aliases = listOf("degree celsius", "degrees celsius", "celsius", "dc", "c"),
        "degree Celsius",
        "degrees Celsius"
    ),
    FAHRENHEIT(
        "degrees",
        aliases = listOf("degree fahrenheit", "degrees fahrenheit", "fahrenheit", "df", "f"),
        "degree Fahrenheit",
        "degrees Fahrenheit"
    ),
    KELVIN("degrees", listOf("kelvin", "kelvins", "k"), "kelvin", "kelvins"),
    NULL("", listOf(), "???", "???");


    private fun convertToMeters(amount: Double): Double {
        return when (this) {
            METER -> amount
            KILOMETER -> amount * 1000.0
            CENTIMETER -> amount * 0.01
            MILLIMETER -> amount * 0.001
            MILE -> amount * 1609.35
            YARD -> amount * 0.9144
            FOOT -> amount * 0.3048
            INCH -> amount * 0.0254
            else -> -1.0
        }
    }

    private fun convertFromMeters(amount: Double): Double {
        return when (this) {
            METER -> amount
            KILOMETER -> amount / 1000.0
            CENTIMETER -> amount / 0.01
            MILLIMETER -> amount / 0.001
            MILE -> amount / 1609.35
            YARD -> amount / 0.9144
            FOOT -> amount / 0.3048
            INCH -> amount / 0.0254
            else -> -1.0
        }
    }

    private fun convertToGrams(amount: Double): Double {
        return when (this) {
            GRAM -> amount
            KILOGRAM -> amount * 1000.0
            MILLIGRAM -> amount * 0.001
            POUND -> amount * 453.592
            OUNCE -> amount * 28.3495
            else -> -1.0
        }
    }

    private fun convertFromGrams(amount: Double): Double {
        return when (this) {
            GRAM -> amount
            KILOGRAM -> amount / 1000.0
            MILLIGRAM -> amount / 0.001
            POUND -> amount / 453.592
            OUNCE -> amount / 28.3495
            else -> -1.0
        }
    }

    private fun convertToFahrenheit(amount: Double): Double {
        return when (this) {
            FAHRENHEIT -> amount
            CELSIUS -> amount * 9 / 5 + 32
            KELVIN -> amount * 9 / 5 - 459.67
            else -> -1.0
        }
    }

    private fun convertFromFahrenheit(amount: Double): Double {
        return when (this) {
            FAHRENHEIT -> amount
            CELSIUS -> (amount - 32) * 5 / 9
            KELVIN -> (amount + 459.67) * 5 / 9
            else -> -1.0
        }
    }

    companion object {
        fun getAllAliases(): List<String> {
            return values().map { it.aliases }.flatten()
        }

        fun getUnit(s: String): Units {
            values().map { if (s in it.aliases) return it }
            return NULL
        }

        fun convert(amount: Double, from: Units, to: Units): Double {
            return if (from.type == to.type) {
                when (from.type) {
                    "distance" -> if (amount < 0) {
                        -2.0
                    } else {
                        to.convertFromMeters(from.convertToMeters(amount))
                    }

                    "weight" -> if (amount < 0) {
                        -3.0
                    } else {
                        to.convertFromGrams(from.convertToGrams(amount))
                    }

                    "degrees" -> to.convertFromFahrenheit(from.convertToFahrenheit(amount))
                    else -> -1.0
                }
            } else -1.0
        }
    }
}