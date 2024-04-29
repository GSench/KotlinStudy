# Type checks and casts﻿
https://kotlinlang.org/docs/typecasts.html
- `is`, `!is` <=> Java`typeof()` `!typeof()`
- Unsafe cast operator﻿ `val x: String = y as String`
- Safe cast operator﻿ `val x: String? = y as? String` = null on failure
- Smart casts(SC): work only when the compiler can guarantee that the variable won't change between the check and its usage.
	- **SC do not actually change type! It just allows to use variables in others type context**:
		```
		open class Ancestor
		class Child: Ancestor() {
			fun doSmth() = println("doing")
		}
		val c: Child = Child()
		val a: Ancestor = c
		if(a is Child) {
			// Here smart cast works:
			// Using (a as Child) insted of a after check
			a.doSmth() //prints doing
		}
		```
	- `if(x is String) print(x.length)` automatically cast after if
	- `if (x !is String) return print(x.length)` automatically cast after return in fail case
	- `if (x !is String || x.length == 0) return` 
	- `when (x) { is Int -> print(x + 1) is String -> print(x.length + 1)`
	- `var a: String? = null; a = "abc"` since `a` explicitly assigned to non-null value, we can use it as not nullable.
	- `val a: Any = "hello"; if(a is String) println(a.length)` - also smart-cast
	- `var` properties never smart cast
	- `val` properties smart cast if (`private`, `internal` or it's checked in the same module), not `open`, no custom getters 
	- `val` local variables always smart cast except local delegated properties.
	- `var` local variables smart cast if the variable is not modified between the check and its usage, is not captured in a lambda that modifies it, and is not a local delegated property.

