# Inheritance

## Open classes

https://kotlinlang.org/docs/inheritance.html

- All classes inherit `Any` that has `equals()`, `hashCode()`, `toString()`

- Classes are `final` by default. `open` classes inheritable.

- ```kotlin
  open class Base(p: Int)
  class Derived(p: Int) : Base(p)
  ```

### Constructors

- If the derived class has a PC, the base class must be initialized in that PC

- If the derived class has no PC, then each SC must initialize the base type (or another SC):
  
  ```kotlin
  class Derived: Base {
      constructor(p: Int) : super(p) 
      constructor(x: Int, y: Int) : this(x + y)
  }
  ```

- different secondary constructors can call different constructors of the base type

- If derived class has no PC but SC reffering to base class constructor, we cannot refer to base class constructor in derived class's header. 
  
  ```kotlin
  open class Base(val x: Int)
  
  class Derived1: Base(1) // OK
  // rule: if no PC, but SC calls super, no super constructor in header!
  class Derived2: Base//(1) //ERROR! no super init without PC
  { constructor() : super(2) }
  // rule: if class has PC, all SC must refer to PC, not super
  class Derived3(): Base(1) {
      //constructor(a: Int): super(2) //ERROR! PC call expected
      constructor(a: Int): this() // param 'a' is just to differ from PC
  }
  ```

### Override

- functions and properies are `final` by default

- ```kotlin
  open class Shape {
      open val vertexCount: Int = 0 // can be overriden
      open fun draw() { /*...*/ } // can be overriden
      fun fill() { /*...*/ } // cannot be overriden
  }
  class Circle() : Shape() {
      override fun draw() { /*...*/ } // must have override keyword, still open
      // fun fill() { /*...*/ } ERROR with or without override keyword
  }
  open class Rectangle() : Shape() {
      override val vertexCount = 4 // also can be used in PC
      final override fun draw() { /*...*/ } // cannot be overriden
  }
  class Polygon : Shape {
      override var vertexCount: Int = 3  // val can be overriden by var
      // BUT var can NOT be overriden by val
  }
  ```

- **??** The base class initialization is done as the first step. When the base class constructor is executed, the properties declared or overridden in the derived class have not yet been initialized. When designing a base class, avoid using `open` members in the constructors, property initializers, or `init` blocks.

### Superclass calls

- `super` to call superclass implementations of functions and properties

- Inside an inner class `super@Outer` call outer's class superclass implementations of functions and properties:
  
  ```kotlin
  open class SuperClass {
      open fun doSmth() { println("Super class function") }
  }
  class OuterClass : SuperClass() {
      override fun doSmth() { println("Outer class function") }
      inner class InnerClass {
          fun doSmthInner() {
              super@OuterClass.doSmth()
              // super.doSmth() ERROR
              doSmth()
              println("Inner class function")
          }
      }
  }
  fun main() {
      val o = OuterClass()
      o.InnerClass().doSmthInner()
      // Super class function
      // Outer class function
      // Inner class function
  }
  ```

- Multiple override:
  
  ```kotlin
  open class Rectangle {
      open fun draw() { /* ... */ }
  }
  interface Polygon {
      fun draw() { /* ... */ } // interface members are 'open' by default
  }
  class Square() : Rectangle(), Polygon {
      // The compiler requires draw() to be overridden:
      override fun draw() { // override both Rectangle's and Polygon's draw!
          super<Rectangle>.draw() // call to Rectangle.draw()
          super<Polygon>.draw() // call to Polygon.draw() (default impl)
      }
  }
  ```

## Abstract classes

https://kotlinlang.org/docs/classes.html#abstract-classes

```kotlin
abstract class Polygon {
    abstract fun draw()
    open fun fill(){}
    fun rotate(){}
}

class Rectangle : Polygon() {
    override fun draw() {}
    override fun fill() {}
    //override fun rotate(){} // ERROR!
}
```

- You can override a non-abstract `open` member with an abstract one (while abstract class inherits open class)

## Interfaces

https://kotlinlang.org/docs/interfaces.html

- `interface` cannot store state (unlike `abstract` classes)

- ```kotlin
  interface MyInterface1 {
      fun bar() // abstract
      fun foo() {} // open
      fun foobar(){}
  }
  interface MyInterface2 {
      val prop: Int // abstract
      val propertyWithImplementation: String
          get() = "foo" // accessors implementations can be provided
          // can't have backing fields
      fun printProp() {
          print(prop)
      }
      fun foobar(){}
  }
  // can implement several interfaces
  class Child1 : MyInterface1, MyInterface2 {
      override var prop: Int = 29 // var can override val but not vice versa
      override val propertyWithImplementation = "foo of Child"
      override fun bar() {} // mandatory override
      override fun foo() { // optional override
          super.foo() // calling default implementation: no ambiguity here
      }
      override fun foobar() {
          super<MyInterface1>.foobar()
          super<MyInterface2>.foobar()
      }
  }
  // An interface can derive from other interfaces
  interface MyInterface3: MyInterface1 {
      // can provide impl. for their members and declare new ones
      override fun bar(){}
      fun newbar()
  }
  class Child2: MyInterface3 {
      // implementing foo, bar is not required, but can be provided
      override fun newbar(){}
      override fun foobar(){} // override MyInterface3 superclass open fun
  }
  ```

- 
