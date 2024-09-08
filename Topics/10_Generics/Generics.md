# Generics

```kotlin
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
  
  
  
  - https://kotlinlang.org/docs/generics.html
  - https://habr.com/ru/articles/719256/
  - https://www.baeldung.com/kotlin/generics
  - https://kotlinlang.ru/docs/generics.html
  - https://habr.com/ru/companies/redmadrobot/articles/301174/
  - https://medium.com/swlh/generics-in-kotlin-5152142e281c
  - 
