package converter

class Convertor {
    private val allUnits = Units.getAllAliases()
    private var nrToConvert: Double = -1.0
    private var inputFrom: String = ""
    private var inputTo: String = ""
    private var fromUnit: Units = Units.NULL
    private var toUnit: Units = Units.NULL
    private var singularFrom: String = "???"
    private var singularTo: String = "???"
    private var pluralFrom: String = "???"
    private var pluralTo: String = "???"

    fun convert(input: String) {
        if (processInput(input)) {
            setUnits()
            when (val converted = Units.convert(nrToConvert, fromUnit, toUnit)) {
                -1.0 -> displayFailMessage()
                -2.0 -> println("Length shouldn't be negative")
                -3.0 -> println("Weight shouldn't be negative")
                else -> displayConversion(nrToConvert, converted)
            }
        }
    }

    private fun processInput(input: String): Boolean {
        try {
            var parts = input.split(" ")
            nrToConvert = parts.first().toDouble()
            parts = parts.drop(1)
            if (parts.first() in listOf("degree", "degrees")) {
                inputFrom = (parts[0] + " " + parts[1])
                // drop from unit and connection word
                parts = parts.drop(3)
            } else {
                inputFrom = parts[0]
                // drop from unit and connection word
                parts = parts.drop(2)
            }
            inputTo = if (parts.first() in listOf("degree", "degrees")) {
                (parts[0] + " " + parts[1])
            } else {
                parts[0]
            }
            return true
        } catch (e: Exception) {
            println("Parse error")
            return false
        }
    }

        private fun setUnits() {
            if (inputFrom in allUnits) {
                fromUnit = Units.getUnit(inputFrom)
                singularFrom = fromUnit.singular
                pluralFrom = fromUnit.plural
            } else {
                fromUnit = Units.NULL
                singularFrom = "???"
                pluralFrom = "???"
            }
            if (inputTo in allUnits) {
                toUnit = Units.getUnit(inputTo)
                singularTo = toUnit.singular
                pluralTo = toUnit.plural
            } else {
                toUnit = Units.NULL
                singularTo = "???"
                pluralTo = "???"
            }
        }

        private fun displayConversion(nrToConvert: Double, converted: Double) {
            val message = when (nrToConvert) {
                0.0 -> "0.0 $pluralFrom is 0.0 $pluralTo"
                1.0 -> {
                    if (converted.toInt().toDouble() == 1.0) "1.0 $singularFrom is $converted $singularTo"
                    else "1.0 $singularFrom is $converted $pluralTo"
                }

                else -> {
                    if (converted.toInt().toDouble() == 1.0) "$nrToConvert $pluralFrom is 1.0 $singularTo"
                    else "$nrToConvert $pluralFrom is $converted $pluralTo"
                }
            }
            println(message)
            println()
        }

        private fun displayFailMessage() {
            println("Conversion from $pluralFrom to $pluralTo is impossible")
            println()
        }
    }
