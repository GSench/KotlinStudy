# Kotlin Tips & Tricks

## if(null) return

```kotlin
// Instead:
if(smth == null) return
// Do:
smth ?: return
```

## Getter + Initialiser

```kotlin
private var someObject: SomeObject? = null
// Instead
fun getSomeObject(incomingParam: Param): SomeObject {  
    if (someObject == null) {  
        someObject = SomeObjectImpl(incomingParam)  
    }  
    return requireNotNull(someObject)  
}
// Do:
fun getSomeObject(incomingParam: Param) =  
    someObject ?: SomeObjectImpl(incomingParam)  
        .also { someObject = it }
```

## SmartCast

```kotlin
open class SomeClass  
class DerivedClass: SomeClass() {  
    val prop = "Hello!"  
}  
fun doSmth(someClass: SomeClass) {  
    someClass as DerivedClass // smartcast someClass
    // now we can use someClass as DerivedClass  
    println(someClass.prop)  
}  
fun main() {  
    //doSmth(SomeClass()) // ClassCastException  
    doSmth(DerivedClass()) // Hello!  
}
```