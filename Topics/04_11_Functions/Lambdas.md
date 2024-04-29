# Higher-order functions and lambdas

https://kotlinlang.org/docs/lambdas.html

Kotlin functions are **first-class***: can be stored in variables.

**Func in Kotlin are equal to interfaces with invoke() method.**

Higher-order function takes functions as parameters or returns a function.

```kotlin
fun highOrderFun(action: (x: Int, y: Int) -> String): 
    (Int) -> Int {
    //...
}
```

## Function type

### Delcaration

- `(Int, Int) -> Int`

- `() -> Unit` The Unit return type cannot be omitted

- `(x: Int) -> String` can include names for the function params

- `((Int, Int) -> Int)?` nullable function type

- `suspend () -> Unit` suspend modifier

- `(Int) -> ((Int) -> Unit)` func that takes Int and returns func that takes Int and returns nothing
  `(Int) -> (Int) -> Unit` the same, arrow notation is right-associative

- `Int.(String) -> Long` **receiver type**: func that can be called on a receiver Int object with a String parameter and return a Long value. <=> `(Int, String) -> Long`

### Invoke

- `f.invoke(x)` <=> `f(x)` invokes value of a function type

- func type can be inherited:
  
  ```kotlin
  class IntTransformer: (Int) -> Int {
      override operator fun invoke(x: Int): Int = TODO()
  }
  val intFunction: (Int) -> Int = IntTransformer()
  ```

- Callable reference to funct: `::Funct`, `ObjClass::Funct` or constructor: `::ObjClass`:
  
  ```kotlin
  fun isOdd(n: Int): Boolean {
      return n % 2 != 0
  }
  println(list(1,2,3).filter(::isOdd)) // [1, 3]
  ```

## Lambda expressions λ

- `{λ}` : `val sumLambda: (Int, Int) -> Int = { x: Int, y: Int -> x + y }`

- `val actionLambda = () -> Unit = { println("action") }`

- λ return type cannot be specifyed explicitly. About `return` and multiline λ:
  
  ```kotlin
  val lambda = { x: Int ->
      val result = x + x
      println("result = $result")
      // The last expression is the return value:
      result
  }
  // in case of high order function:
  ints.filter {
      val shouldFilter = it > 0
      shouldFilter
  }
  // equal to
  ints.filter {
      val shouldFilter = it > 0
      return@filter shouldFilter
  }
  ```

- ```kotlin
  fun loopFunc() {
      repeat(3){
          println(it)
          if (it>1) {
              return // returns from loopFunc
              return@loopFunc // also returns from loopFunc
              return@repeat // returns from repeat
          }
      }
  }
  ```

- optional type annotations
  
  ```kotlin
  val sumLambda = {x: Int, y: Int -> x + y} // explicit types
  // so sumLambda's type is inferred as (Int, Int) -> Int
  val subLambda: (Int, Int) -> Int = {x, y -> x - y} // types ommited
  // as they can be inferred from λ val type
  val errLambda = {x, y -> x - y} // ERROR
  ```
  
  **note**: in case of high order func λ's params type can always be inferred

- **trailing λ** if the last param of a func is a func, then a λ can be placed after the ()
  
  ```kotlin
  val sum = listOf(1,2,3).fold(0, { s: Int, e: Int -> s + e })
  // the same as
  val sum = listOf(1,2,3).fold(0) { s: Int, e: Int -> s + e }
  // these are equal:
  run({ println("...") })
  run() { println("...") }
  run { println("...") } // If λ is the only param, () can be omitted
  ```

- `it` is implicitly declared as param name in case of single param λ
  
  `ints.filter { it > 0 } // this literal is of type '(it: Int) -> Boolean'`

- If λ param is unused: `map.forEach { (_, value) -> println("$value!") }`

- 

## Anonymous functions

- ```kotlin
  fun(x: Int, y: Int): Int {
      return x + y
  }
  fun(x: Int, y: Int): Int = x + y
  ```

- `ints.filter(fun(item) = item > 0)`  param`s and anon func's types can be omitted if they can be inferred

- anon func with {} must have return type `listOf(1,2,3).filter(fun(item):Boolean {return item>0})`

- ```kotlin
  fun loopFunc() {
      repeat(3, fun(i) {
          println(i)
          if (i>1) {
              return // returns from repeat
              return@loopFunc // returns from loopFunc
              return@repeat // returns from repeat
          }
      }
  }
  ```

- 

## Closures

```kotlin
var x = 0 // the variables declared in the outer scope
val stAnotherX = { y: Int ->
    println("changing x from $x to $y")
    x = y // The variables captured in the closure can be modified in λ
}
println("x = $x") // x = 0
x = 1
println("x = $x") // x = 1
setAnotherX(2) // changing x from 1 to 2
println("x = $x") // x = 2
```

## Receiver type

- `A.(B) -> C` func can be called on a receiver object `A` with a parameter `B` and return a value `C`

- `(A, B) -> C` can be passed or assigned as `A.(B) -> C`, and vice versa

- inside the body of receiver type `A` func `A` = `this`. Also `A`'s public props can be accessed inside:
  
  ```kotlin
  val sum: Int.(Int)->Int = {other->plus(other)} // plus is Int's func
  val sum = fun Int.(other:Int):Int = this+other // this = reciever Int
  ```

- **??** *Lambda expressions can be used as function literals with receiver when the receiver type can be inferred from the context*

- Type-safe builders﻿ https://kotlinlang.org/docs/type-safe-builders.html

- 
