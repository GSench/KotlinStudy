println("Start")
run block1@{
    println("Running")
    return@block1 //finishes block1
    println("Not Running")
}
println("Finish")

println("Start")
while_loop@while(true) {
    for (i in 1..5) {
        println("Running $i")
        if (i == 3) {
            break@while_loop
        }
    }
}
println("Finish")
