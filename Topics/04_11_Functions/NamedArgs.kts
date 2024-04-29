fun namedargs(a: Int, b: String, c: Int){
	println("a = $a; b = $b; c = $c")
}
//namedargs(b="2", a=1, 3) //error: mixing named and positioned arguments is not allowed
namedargs(1, c=3, b="2")