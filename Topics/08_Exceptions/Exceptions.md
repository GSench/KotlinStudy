# Exceptions
https://kotlinlang.org/docs/exceptions.html
- All exception classes in Kotlin inherit the Throwable class
- to throw: `throw Exception("Hi There!")`
- to catch: `try {} catch (e: SomeException) {} finally {}`
- try is an expression (can return value): `val a: Int? = try {input.toInt() } catch (e: NumberFormatException) { null }`
- Kotlin does not have checked exceptions: no Java`void doSmthDangerous() throws Exception {}` (+ exceptions do not have to be checked)
- throw is an expression of type `Nothing`:
	`val s = person.name ?: throw IllegalArgumentException("Name required")`
	```
	fun fail(message: String): Nothing {
		/*return*/ throw IllegalArgumentException(message)
	}
	```