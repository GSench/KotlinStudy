fun hello(m: String) = "Hello, $m!"
println(hello("World"))
fun defargs(a: Int = 1, b: String = hello("World")) {
	println(b+a)
}
defargs()