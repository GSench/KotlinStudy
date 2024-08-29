# Switch delegate

```kotlin
class Test (
	val lazy1: Lazy<String>,
    val lazy2: Lazy<String>,
) {
    var condition = false
    val lazyValue by if(condition) lazy1 else lazy2
    val lazyCombinedValue by lazy{ (if(condition) lazy1 else lazy2).value }
    val combinedValue get() = (if(condition) lazy1 else lazy2).value
    
    override fun toString() =
    	"condition = $condition\n" +
    	"lazy1 is${if(lazy1.isInitialized()) "" else " NOT"} initialized\n" +
    	"lazy2 is${if(lazy2.isInitialized()) "" else " NOT"} initialized\n" +
    	"lazyValue = $lazyValue\n" + 
    	"lazyCombinedValue = $lazyCombinedValue\n" +
    	"combinedValue = $combinedValue\n" +
        "lazy1 is${if(lazy1.isInitialized()) "" else " NOT"} initialized\n" +
    	"lazy2 is${if(lazy2.isInitialized()) "" else " NOT"} initialized\n"
}

fun main() {
    val test = Test(
    	lazy1 = lazy{ "Lazy 1" },
        lazy2 = lazy{ "Lazy 2" },
    )
    println(test)
    test.condition = true
    println(test)
    test.condition = false
    println(test)
}
```
```output
condition = false
lazy1 is NOT initialized
lazy2 is NOT initialized
lazyValue = Lazy 2
lazyCombinedValue = Lazy 2
combinedValue = Lazy 2
lazy1 is NOT initialized
lazy2 is initialized

condition = true
lazy1 is NOT initialized
lazy2 is initialized
lazyValue = Lazy 2
lazyCombinedValue = Lazy 2
combinedValue = Lazy 1
lazy1 is initialized
lazy2 is initialized

condition = false
lazy1 is initialized
lazy2 is initialized
lazyValue = Lazy 2
lazyCombinedValue = Lazy 2
combinedValue = Lazy 2
lazy1 is initialized
lazy2 is initialized
```
