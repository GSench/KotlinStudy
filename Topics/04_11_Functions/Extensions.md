# Extensions

https://kotlinlang.org/docs/extensions.html

```kotlin
fun String.allWordsCaps(): String {
    return this // receiver object (String)
        .split(" ")
        .joinToString(" ") { word ->
            word.replaceFirstChar { it.titlecase() }
        }
}
fun main() {
    println("some general title".allWordsCaps())
}
```

```java
@NotNull
public static final String allWordsCaps(@NotNull String $this$allWordsCap) {
    Intrinsics.checkNotNullParameter($this$allWordsCaps, "$this$allWordsCaps");
    return CollectionsKt.joinToString$default(/*...*/;
}
public static final void main() {
    String var0 = allWordsCaps("some general title");
    System.out.println(var0);
}
```

- Exts dont modify the classes they extend. They also cannot access its private props (if ext is outside its receiver type).

- Exts are dispatched **statically**:
  
  - They belong to class itself:
    
    ```kotlin
    open class Shape
    class Rect: Shape()
    fun Shape.getName() = "Shape"
    fun Rect.getName() = "Rectangle"
    fun printClassName(s: Shape) = println(s.getName())
    printClassName(Rect()) // Shape
    ```
  
  - member always wins against ext
  
  - ext can overload member that have the same name but a different signature (it's another func)

- Nullable receiver﻿
  
  ```kotlin
  fun Any?.toString() = when (this) {
      null -> "null" // smartcast to notnull type in else
      else -> toString() // toString of reciever (Any!!)
  }
  ```

- Extension properties﻿
  
  ```kotlin
  val <T> List<T>.lastIndex: Int get() = size - 1
  val House.number = 1 // error: no initializers for ext props
  ```

- Companion object extensions﻿
  
  ```kotlin
  class MyClass { companion object { } } // will be called "Companion"
  fun MyClass.Companion.printCompanion() { println("companion") }
  MyClass.printCompanion()
  ```

- extensions as members
  
  ```kotlin
  class Host
  class Connection(val host: Host) {
      fun Host.connectionString() =
          this.toString() + // extension receiver (Host); no this@Host
          this@Connection.toString() // dispatch receiver
      fun connect() = print(host.connectionString())
  }
  Connection(Host()).connect()
  //Host().connectionString() // ERROR, no ext outside Connection
  ```
  
  - Ext as members can be `open` and overridden in subclasses


