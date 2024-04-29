# Generics

- ```kotlin
  class Box<T>(t: T) {
      var value = t
  }
  ```

- Generic type inference:
  
  ```kotlin
  val box: Box<Int> = Box<Int>(1)
  val box = Box(1)
  ```

- generic `fun getVal(id: Int): T {}`

- Inheriting with type definition (it's OK in Java too):
  
  ```kotlin
  
  ```
  
  
  
  
