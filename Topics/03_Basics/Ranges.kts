println("range to")
val range: IntRange = 1..5
println(range) // 1..5
for (v in range) print(v) // 12345
println("\nrange downTo")
val rangeDown = 7 downTo 1 step 2
for (v in rangeDown) print(v) // 7531