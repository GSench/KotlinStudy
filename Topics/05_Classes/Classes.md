# Classes

https://kotlinlang.org/docs/classes.html

## Constructors & Properies

https://kotlinlang.org/docs/classes.html

https://kotlinlang.org/docs/properties.html

- `class A` constructor `()` & body `{}` are optional.

- primary constructor+ (PC+):
  
  - `class A constructor()` - PC; if no modifiers, `constructor` can be ommited; `class DontCreateMe private constructor() { /*...*/ }`
  - `val`,`var` definitions ~in primary Java constructor
  - `init{}` ~in primary Java constructor

- properties:
  
  - `class A (x: Int)` - not a property but can be used in PC+
  - `class A (var x: Int, var y: Int = 0)` - properties + are being set in PC+; type is mandatory; can have default value; cannot have custom getters/setters
  - `class A (){var x: Int = 0 var y = 0}` - properties, must be initialized in PC+ and before being used; type can be inferenced; can have custom getter/setter
  - `var stringRepresentation: String get(){...} set(value){...}` getter, setter
  - `var innerClassVal: String private set` - default setter modifiers
  - `var counter = 0 set(value) { if (value >= 0) field = value` Backing fields﻿

- `class A{constructor(){}}` Secondary constructors﻿ (SC)
  
  - `class A(...){constructor(...):this(...){}}` must call PC (if it has one), or other SC first
  
  - `init()`, property initializations are executed before SC (as they are int PC)
  
  - SCs'﻿ bodies are equal to `init{}`
  
  - this is also SC:
    
    ```kotlin
    class PrimConstructor {
        init { println(1) }
        // Note: vals are not have to be initialized!
        // They must be initialized in sec.constr. or in init{}
        // In case of PC they must be initialized during declaration or in init{}
        // (PC parameters can be used)
        val a: String
        val b: Int
        init { println(2) }
        // IDEA alert: Secondary constructor should be converted to a primary one 
        constructor(x: String, y: Int) {
            a = x + y
            b = y
            println(3)
        }
        constructor(z: Int) {
            a = z.toString()
            b = z
        }
        init { println(4) }
    }
    fun main() {
        // we still cannot create class with empty PC (though it has not one):
        // val e = PrimConstructor() // ERROR
        val p = PrimConstructor("a", 2) // 1 2 4 3
    }
    ```

- Creating instance of a class A: `val a = A()`

- `class A{fun doSmth(){...}}` - function can be class property

- Classes can contain:
  
  - Constructors and initializer blocks
  - Functions
  - Properties
  - Nested and inner classes
  - Object declarations

```kotlin
class Person (
    var firstName: String,
    val lastName: String,
    birthdayYear: Int,
    val id: Int = 0
) {
    val age = 2024 - birthdayYear
    //var sex: Char // ERROR! Property must be initialized or be abstract
    init {
        println(birthdayYear)
        println(age)
    }
    //fun getBirthday() = birthdayYear // ERROR! Unresolved reference: birthdayYear
}
```

```java
public final class Person {
   private final int age;
   private String firstName;
   private final String lastName;
   private final int id;

   public final int getAge() {
      return this.age;
   }

   public final String getFirstName() {
      return this.firstName;
   }

   public final void setFirstName(String var1) {
      this.firstName = var1;
   }

   public final String getLastName() {
      return this.lastName;
   }

   public final int getId() {
      return this.id;
   }

   public Person(String firstName, String lastName, int birthdayYear, int id) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.id = id;
      this.age = 2024 - birthdayYear;
      System.out.println(birthdayYear);
      int var5 = this.age;
      System.out.println(var5);
   }

   // Default value implementation
   // $FF: synthetic method
   public Person(String var1, String var2, int var3, int var4, int var5, DefaultConstructorMarker var6) {
      if ((var5 & 8) != 0) {
         var4 = -1;
      }

      this(var1, var2, var3, var4);
   }

}
```

## Visibility modifiers

https://kotlinlang.org/docs/visibility-modifiers.html

- for files
  - `public` The default visibility. Visible everywhere.
  - `private` - visible inside the file that contains the declaration.
  - `internal` - visible everywhere in the same module. https://kotlinlang.org/docs/visibility-modifiers.html#modules
    - An IntelliJ IDEA module.
    - A Maven project.
    - A Gradle source set (with the exception that the test source set can access the internal declarations of main).
    - A set of files compiled with one invocation of the <kotlinc> Ant task.
- for Classes:
  - `private` - visible inside this class only (including all its members).
  - `protected` = `private`, but that it is also visible in children classes.
  - `internal` - any client inside this module who sees the declaring class sees its internal members.
  - `public` - any client who sees the declaring class sees its public members.
  - Getters always have the same visibility as their properties.

## Compile-time constants

`const` modifier. Such a property must:

- be a top-level property, or a member of an `object` declaration or a `companion object`

- be initialized with a value of type `String` or a primitive type

- It cannot be a custom getter

The compiler will replace `const` with its value. However, the field will not be removed and can be interacted with using reflection.

## Data classes

https://kotlinlang.org/docs/data-classes.html

```
data class Customer(val id: Int, var name: String, var email: String)
```

- Only `val`/`var` properties of PC are sufficent for data class (are used in `toString()`, taken into account for `equals()`, etc.)
- provides default implementation for:
  - Constructor
  - `.equals()`/`.hashCode()` pair
  - `.toString()` of the form "User(name=John, age=42)"
  - `.componentN()` - properties in their order of declaration. Makes possible to deconstruct data class. Cannot be overriden.
  - `.copy()` can be used with `=`. Allows to alter some of its properties. Cannot be overriden.
- requirements:
  - The primary constructor must have at least one parameter.
  - All primary constructor parameters must be marked as val or var.
  - Data classes can't be abstract, open, sealed, or inner.
- can also have body; it is possible to override default implementations

## Enum classes

https://kotlinlang.org/docs/enum-classes.html

```
enum class Priority {
// enum constants:
    MINOR,
    NORMAL,
    MAJOR,
    CRITICAL
}
val priority = Priority.NORMAL
```

- `toString()` returns property name, not Int; = `.name` property
- `(val value: Int){MINOR(-1),...}` - any property can be defined in constructor and assigned to enum constants
- enum class can have properties in body (not only in PC)
- `.ordinal`: Int = index of enum constants (order number)
- `.values()` = list of all possible enum constants
- `.valueOf("MAJOR")` = enum constants by its name
- `.entries`: `EnumEntries<EnumClass>`
- `enum class Priority { MINOR{ override fun toString()...}, ... }` - default functions can be overriden
- `enum class Priority { MINOR{ override fun text()...},...; abstract fun text()... }` we can add abstract function but we must implement it for all enum constants
- You can access the enum constants using the `enumValues<T>(` and `enumValueOf<T>()` functions.

## Object

https://kotlinlang.org/docs/object-declarations.html

```kotlin
object Singleton {
    var title = "global object"
}
println(Singleton.title)

val helloWorld = object {
    val hello = "Hello"
    val world = "World"
    // object expressions extend Any, so `override` is required on `toString()`
    override fun toString() = "$hello $world"
}
print(helloWorld)
```

Using anonymous objects as return and value types:

```kotlin
class C {
    private fun getObject() = object {
        val x: String = "x"
    }
    fun printX() {
        println(getObject().x)
    }
}
```

There is one important semantic difference between object expressions and object declarations:

- Object expressions are executed (and initialized) immediately, where they are used.
- Object declarations are initialized lazily, when accessed for the first time.
- A companion object is initialized when the corresponding class is loaded (resolved) that matches the semantics of a Java static initializer.
