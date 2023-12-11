package converter
enum class Units(val singular: String, val plural: String, val short: String, val ratio: Double, val type: Int) {
    NULL("???", "???", "???", 0.0, 0),
    GRAM("gram", "grams", "g", 1.0, 1),
    KILOGRAM("kilogram", "kilograms", "kg", 1000.0, 1),
    MILLIGRAM("milligram", "milligrams", "mg", 0.001, 1),
    OUNCE("ounce", "ounces", "oz", 28.3495, 1),
    POUND("pound", "pounds", "lb", 453.592, 1),
    METER("meter", "meters", "m", 1.0, 2),
    KILOMETER("kilometer", "kilometers", "km", 1000.0, 2),
    MILLIMETER("millimeter", "millimeters", "mm", 0.001, 2),
    CENTIMETER("centimeter", "centimeters", "cm", 0.01, 2),
    FOOT("foot", "feet", "ft", 0.3048, 2),
    YARD("yard", "yards", "yd", 0.9144, 2),
    INCH("inch", "inches", "in", 0.0254, 2),
    MILE("mile", "miles", "mi", 1609.35, 2),
    CELSIUS("degree celsius", "degrees celsius", "c", 1.0, 3),
    FAHRENHEIT("degree fahrenheit", "degrees fahrenheit", "f", 1.0, 3),
    KELVIN("kelvin", "kelvins", "k", 1.0, 3);
}
fun convert(u1: Units, u2: Units, num: Double) {
    val final1 = if (num != 1.0) u1.plural else u1.singular
    val res: Double = if (u1.type != 3) {
        num * u1.ratio / u2.ratio
    } else {
        when {
            u1.short == "f" && u2.short == "c" -> (num - 32) * (5.0/9)
            u1.short == "c" && u2.short == "f" -> num * (9.0 / 5) + 32
            u1.short == "c" && u2.short == "k" -> num + 273.15
            u1.short == "k" && u2.short == "c" -> num - 273.15
            u1.short == "k" && u2.short == "f" -> num * (9.0 / 5) - 459.67
            u1.short == "k" && u2.short == "k" -> num
            u1.short == "c" && u2.short == "c" -> num
            u1.short == "f" && u2.short == "f" -> num
            else -> (num + 459.67) * (5.0 / 9)
        }
    }
    val final2 = if (res != 1.0) u2.plural else u2.singular
    println("$num $final1 is $res $final2")
}
fun main() {
    while (true) {
        print("Enter what you want to convert (or exit): ")
        val i = readln().lowercase().replace("degrees ", "").replace("degree ", "").split(' ').toMutableList()
        if (i[0] == "exit") break
        if (i.lastIndex != 3 || i[0].toDoubleOrNull() == null) {
            println("Parse error")
            continue
        }
        var u1 = Units.NULL
        var u2 = Units.NULL
        when (i[1]) {
            "celsius", "dc" -> i[1] = "c"
            "fahrenheit", "df" -> i[1] = "f"
        }
        when (i[3]) {
            "celsius", "dc" -> i[3] = "c"
            "fahrenheit", "df" -> i[3] = "f"
        }
        for (j in Units.values()) {
            if (i[1] in listOf(j.singular, j.plural, j.short)) u1 = j
            if (i[3] in listOf(j.singular, j.plural, j.short)) u2 = j
        }
        when {
            u1 == Units.NULL || u2 == Units.NULL || u1.type != u2.type -> println("Conversion from ${u1.plural} to ${u2.plural} is impossible")
            i[0].toDouble() < 0 && u1.type == 1 -> println("Weight shouldn't be negative")
            i[0].toDouble() < 0 && u1.type == 2 -> println("Length shouldn't be negative")
            else -> convert(u1, u2, i[0].toDouble())
        }
    }
}
