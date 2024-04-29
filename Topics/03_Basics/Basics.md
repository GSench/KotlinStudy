# Kotlin Basics

## Variables

- `val` immutable / `var` mutable
- 
- Type inference (auto type detection)
- Type or initializer is required

## Types

https://kotlinlang.org/docs/basic-types.html

### Numbers

https://kotlinlang.org/docs/numbers.html

- `Byte`, `Short`, `Int`, `Long`
- All types are object types
- Number literals `L` `F` `0x` `0xb`
- No implicit conversions! (in Java no bigger to smaller, in Kotlin no smaller to bigger neither)
- Bitwise operations: `shl`, `shr`, `ushr`, `and`, `or`, `xor`, `inv()`
- In arithmetical ops: result type is biggest type
- Unsigned `UByte`, `UShort`, `UInt`, `ULong`, U UL literals https://kotlinlang.org/docs/unsigned-integer-types.html

### Boolean

https://kotlinlang.org/docs/booleans.html
`||` `&&` work lazily

### Char

https://kotlinlang.org/docs/characters.html
`\t` `\b` `\n` `\r` `\'` `\"` `\\` `\$`

### Strings

https://kotlinlang.org/docs/strings.html

- `str[i]` for chars, but chars are still immutable!
- ﻿`""" Multiline string """`
- String templates﻿ `"$s.length is ${s.length}"`

## Loops

https://kotlinlang.org/docs/control-flow.html

- `for(i: Int in 1..10){}` `for(i in 1..10){}` `for(a in array){}` 
- `while(){}` `do{} while()`
- Ranges are distinct types; Progressions: {`..`/`to`,`..<`/`until`,`downTo`}; Range = asc Progression with `step` = 1 https://kotlinlang.org/docs/ranges.html
- Returns and jumps: `label@` code blocks `label@while(){...break@label...}`﻿; `run {}`https://kotlinlang.org/docs/returns.html
- `repeat(n){}` stdlib's loop https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/repeat.html

## Conditionals

https://kotlinlang.org/docs/control-flow.html

- `if(){} else if(){} else {}`

- when
  
  ```kotlin
  when(res){
      1 -> println(1) // single check, single action
      2, 3 -> println(2) // multiple checks with ,
      is String -> { // multiple actions in {}
          println(3)
          println(res)
      }
      else -> println(4)
  }
  ```
  
  ```kotlin
  when { // no value, just conditions; <=> if
      x==1 -> print(1)
      y < 5 -> {print(2)}
  }
  ```

- `if`,`when` are expressions:
  
  - `val n = if(true){1}else{2}`
  
  - it return `Any` value (`Unit` if the value is not some definite expression):
    
    ```kotlin
    val phi = Math.PI // prints Int
    //val phi = -Math.PI/2 // prints String
    //val phi = -Math.PI/2 // prints Any
    val a: Any = // Type can be ommited
        when(Math.sin(phi).toInt()) {
            0 -> 0
            1 -> "1"
            else -> Any()
    }
    // val b: Int = 1 + a // ERROR, despilte a is Int (phi=PI) 
    when(a) {
        is Int -> println("Int")
        is String -> println("String")
        else -> println("Any")
    }
    ```
  
  - `else` is mandatory in this case (or all cases must be implemented (e.g. all enum vals))!
  
  - return value is the last expression of if,when {} branch:
    
    ```kotlin
    val n = if(true){
        println(1)
        1
    } else {
        println(2)
        2
    }
    ```

## Packages

  https://kotlinlang.org/docs/packages.html

- can be implicidly imported (we use `println` without importing `package kotlin.io`)

- `import org.smth.*`

- `import org.smth as smthelse`

## Compile-time constants

https://kotlinlang.org/docs/properties.html#compile-time-constants

`const` modifier. Such a property must:

- be a top-level property, or a member of an `object` or a `companion object`

- be initialized with a value of type `String` or a primitive type

- It cannot be a custom getter

The compiler will replace `const` with its value. However, the field will not be removed and can be interacted with using reflection.
