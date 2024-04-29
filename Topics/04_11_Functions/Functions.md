## Functions

https://kotlinlang.org/docs/functions.html

```kotlin
fun double(x: Int): Int {
    return 2 * x
}
val result = double(2)
```

- `fun hello(){}` <=> `fun hello():Unit{}` <=~> java`void hello(){}`, but Unit is an object
- `fun err():Nothing{throw Exception()}` `fun inf():Nothing{while(true){}}` returns value that never exists (always throws an Exception or enter an infinite loop) https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing.html
- `fun getInt(x:Int,y:Int){}` parameters declaration. Type must be specified
- `fun getInt(x:Int,y:Int):Int{return x+y}` <=> `fun getInt(x:Int,y:Int):Int=x+y` Single expression functions
- `fun getInt() = 42` <=> `fun getInt():Int{return 42}` return type inference. But! Functions with {} must specify return types explicitly!

### Named arguments

`fun namedargs(a:Int,b:String,c:Int){}` `namedargs(1,c=3,b="2")` Named arguments﻿ can go in any orger, but after 1st named arg all subsequent ones must be named.

### Default arguments

- `fun defargs(a:Int=0,b:Int=1){}` Type must be specified
- `fun defargs(a:Int=0,b:Int){}` params after default must be named if using default (b must by named `defargs(b=1)`)
- defarg's values may be complex (e.g. results or other functions)

### Variable number of arguments

```kotlin
fun va(vararg a: Int){}
va(1, 2, 3)
```

`vararg a: Int`=`a: Array<out Int>`
Spread \* operator:

```kotlin
val a = arrayOf(1, 2, 3)
va(*a)
```
