# Tuples
- Pair: `val pair: Pair<Int, Char> = Pair(1,'A')` `val pair: Pair<Int, Char> = 1 to 'A'`
- Triple: `val triple: Triple<Int, Int, String> = Triple(1, 2, "Hello")`
- are data classes:
	```
	public data class Pair<out A, out B> (
		public val first: A,
		public val second: B
	) : Serializable {...}
	```
- attrs interface: `.componentN()` or `.first`,`.second`...
- Destructuring declarations﻿:
	- deconstructing is possible for **any** objects with `.componentN()` operator functions (incl. `Pair`, `Triple`, `data class`)
	- `val (name, age) = Pair("Rick", 32)` is compiled down to: 
		```
		val name = Pair("Rick", 32).component1()
		val age = Pair("Rick", 32).component2()
		```
	- Specifying type explicidly: `val (name: String, age: Int) = Pair("Rick", 32)`
	- data class example:
		```
		data class Person(val id: Number, var name: String, val gender: Char, var age: Int)
		val p = Person(42, "Dick", 'M', 24)
		val (id,name,gender,age) = p
		```
	- `for`: `for ((key, value) in map){}`
		- Provide `iterator()` fun for the `map` obj
		- Each element of `map` object must have `componentN()` operator fun
	- Underscore for unused variables: `val(_,status)=getResult()`
	- Destructuring in lambdas: `map.mapValues{(k, v)->"$v!"}`
		- Specifying type:<br>
			`map.mapValues{(_,v):Map.Entry<Int,String>->"$v!"}`<br>
			or<br>
			`map.mapValues{(_,v:String)->"$v!"}`
- `.toList()` for tuples of single type
