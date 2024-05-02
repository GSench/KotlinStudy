# Coroutines

- https://kotlinlang.org/docs/coroutines-guide.html

A **<u>coroutine</u>** is an instance of a suspendable computation. It is conceptually similar to a thread, in the sense that it takes a block of code to run that works concurrently with the rest of the code. However, a coroutine is not bound to any particular thread. It may suspend its execution in one thread and resume in another one.

A coroutine does not have a class representation. In Java each thread is associated with an instance of the class `Thread`. There is no object instance of the coroutine itself.

```kotlin
fun main() {
	println("Main started")
	GlobalScope.launch { // this: CoroutineScope
		println("Main coroutine started")
	    launch { // launch a new coroutine and continue
	        println("Coroutine started")
	        delay(1000) // non-blocking delay for 1 second
	        println("Coroutine finished")
	    }
	    // main coroutine continues while a previous one is delayed
	    println("Main coroutine finished, but waits until its child finishes")
	}
	println("Main finished")
}
```
```output
Main started
Main coroutine started
Main coroutine finished, but waits until its child finishes
Coroutine started
Coroutine finished
Main finished
```

## [suspend functions](https://kotlinlang.org/docs/composing-suspending-functions.html)

`suspend fun` can *suspend* execution of a coroutine. Suspending functions can be used only inside *coroutines* or other `suspend fun`.

The `suspend` keyword does nothing itself. It just mark this function for Kotlin compiler, and if this function is invoked, the further continuation may be suspended.

```kotlin
suspend fun doSmth(): Int {
    delay(1000) // executing suspend fun delay
    return 42
}
fun main() = runBlocking {
	val result = doSmth()
    println(result)
}
```

Suspending functions are sequential by default﻿.

```kotlin
suspend fun doSomethingUsefulOne(): Int {
    delay(1000) // pretend we are doing something useful here
    return 13
}
suspend fun doSomethingUsefulTwo(): Int {
    delay(1000) // pretend we are doing something useful here, too
    return 29
}

fun main() = runBlocking {
    val time = measureTimeMillis {
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println("The answer is ${one + two}")
    }
    println("Completed in $time ms")
}
```
```output
The answer is 42
Completed in 2021 ms
```

## CoroutineScope
### CoroutineContext

- https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html
- https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/

The `CoroutineContext` is a set of various elements. The main elements are the `Job` of the coroutine and its `Dispatcher`.

[source](https://github.com/JetBrains/kotlin/blob/master/libraries/stdlib/src/kotlin/coroutines/CoroutineContext.kt)
```kotlin
public interface CoroutineContext {
    public operator fun <E : Element> get(key: Key<E>): E?
    /* Accumulates entries of this context starting with [initial] value and applying [operation] from left to right to current accumulator value and each element of this context. */
    public fun <R> fold(initial: R, operation: (R, Element) -> R): R

    /* Returns a context containing elements from this context and elements from the other [context]. The elements from this context with the same key as in the other one are dropped. */
    public operator fun plus(context: CoroutineContext): CoroutineContext {...}

// Returns a context containing elements from this context, but without an element with the specified [key].
    public fun minusKey(key: Key<*>): CoroutineContext
	
	// Key for the elements of [CoroutineContext].
    public interface Key<E : Element>

    /* An element of the [CoroutineContext]. An element of the coroutine context is a singleton context by itself. */
    public interface Element : CoroutineContext {
        public val key: Key<*>
        public override operator fun <E : Element> get(key: Key<E>): E? =
            @Suppress("UNCHECKED_CAST")
            if (this.key == key) this as E else null
        public override fun <R> fold(initial: R, operation: (R, Element) -> R): R =
            operation(initial, this)
        public override fun minusKey(key: Key<*>): CoroutineContext =
            if (this.key == key) EmptyCoroutineContext else this
    }
}
```

