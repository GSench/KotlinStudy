# Memory Model

- https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html#jls-17.4
- https://jenkov.com/tutorials/java-concurrency/java-memory-model.html
- https://jenkov.com/tutorials/java-concurrency/java-happens-before-guarantee.html

A **<u>memory model</u>** describes whether the execution trace is a legal execution of the program by examining each read in an execution trace and checking that the write observed by that read is valid according to certain rules.

The memory model describes **possible behaviors** of a program. An implementation is free to produce any code it likes, as long as *all resulting executions of a program produce a result that can be predicted by the memory model*. This provides a great deal of freedom for the implementor to perform a myriad of **code transformations**, including the **reordering of actions** and **removal of unnecessary synchronization**.

## Shared Memory

Memory that can be shared between threads is called _shared memory_ or _heap memory_. Local variables, formal method parameters, and exception handler parameters are never shared between threads and are unaffected by the memory model.

## Actions

An **<u>inter-thread action</u>** is an action performed by one thread that can be detected or directly influenced by another thread:
- *Read* (non-volatile). Reading a variable.
- *Write* (non-volatile). Writing a variable.
- *Synchronization actions*, which are:
	- *Volatile read*. A volatile read of a variable.
	- *Volatile write*. A volatile write of a variable.
	- *Lock*. Locking a monitor
	- *Unlock*. Unlocking a monitor.
	- The (synthetic) first and last action of a thread.
	- Actions that start a thread or detect that a thread has terminated
- *External Actions* - actions that may be observable outside of an execution, and has a result based on an environment external to the execution.
- *Thread divergence actions*: performed by a thread that is in an infinite loop in which no memory, synchronization, or external actions are performed. They allow to model how a thread may cause all other threads to stall and fail to make progress.

## Intra-thread Semantics and Program Order

https://stackoverflow.com/questions/25711048/understanding-intra-thread-semantics

The *memory model* determines what values can be read at every point in the program.
The program obeys **<u>intra-thread semantics</u>**: The actions of each thread in isolation must behave as governed by the semantics of that thread, with the exception that the values seen by each read are determined by the *memory model*.

*Intra-thread semantics* are the semantics for single-threaded programs, and allow the complete prediction of the behavior of a thread based on the values seen by read actions within the thread. 

```kotlin
var a = 0
var b = 0
var c = 0
a = 1
b = 2
c = 2
c = b + 1
b = a + c
```

The result of this program is values of `a=1`, `b=4`, `c=3`. But actions execution order may differ from code order:

```kotlin
// actions rearragnged for some reason, as they dont contradict to each other:
var c = 0
var b = 0
var a = 0
b = 2
// c = 2 // this action is ignored, as c is overridden further 
c = b + 1 // (b = 2) must be executed before this action
a = 1
b = a + c // (a = 1) and (c = b + 1) must be executed before this action
```

*Intra-thread semantics* are violated if some JVM implementation execute actions in order:

```kotlin
var a = 0
var b = 0
var c = 0
a = 1
c = 2
c = b + 1 // VIOLATION! b is read before write!
b = 2 // VIOLATION! b is read before write!
b = a + c
```

In fact *intra-thread semantics* prescribe read-write order:

```kotlin
// ... some actions, that dont affect a
a = 1
// ... some actions, that dont affect a
// ... some actions, that dont affect a
b = a + 1 // (a = 1) MAY BE executed ANYWHEN in the interval [a=1 action; read a action)
```

The result of this program is always `a==1`, `b==2` regardless of the true `a = 1` execution order.

The **<u>program order</u>** is a total order in which *inter-thread actions* would be performed according to the *intra-thread semantics*.

A set of actions is **<u>sequentially consistent</u>** if all actions occur in a total order (the execution order) that is consistent with *program order*, and furthermore, each read of a variable sees the value written by that write such that:
- write comes before read in the execution order
- there is no other write that comes between write and read in the execution order.

To determine if the actions of thread `t` in an execution are legal, we simply evaluate the implementation of thread `t` as it would be performed in a single-threaded context, as defined in the rest of this specification.

```kotlin
var a = 0
var b = 0
var c = 0
var d = 0
val threadA = thread {
    a = 1
    b = 2
    b = a + b
}
val threadB = thread {
    c = 1
    c = 2
    d = c + 2
}
```

After compilation the result actions execution consequence may be:

```kotlin
// actions rearragnged for some reason, as they dont contradict to each other:
/*mainThread*/ var d = 0
/*mainThread*/ var c = 0
/*mainThread*/ var b = 0
/*mainThread*/ var a = 0

// actions of threadA and threadB may go in any order while they dont violate JMM rules
/*threadB*/ // c = 1 // this action is ignored, as c is overridden further 
/*threadB*/ c = 2
/*threadA*/ b = 2 // actions a = 1 and b = 2 of threadA are executed in reverse order, as they dont contradict to each other
/*threadB*/ d = c + 2 // Intra-thread semantics prescribe to read c as 2 here
/*threadA*/ a = 1 // actions a = 1 and b = 2 of threadA are executed in reverse order, as they dont contradict to each other
/*threadA*/ b = a + b // Intra-thread semantics prescribe to read a as 1 and b as 2 here
```

If we decompile it back, the program will look like:

```kotlin
var d = 0
var c = 0
var b = 0
var a = 0
val threadA = thread {
    b = 2
    a = 1
    b = a + b
}
val threadB = thread {
    //c = 1
    c = 2
    d = c + 2
}
```

And the program's result (values of `a==1`, `b==3`, `c==2`, `d==4`) remains the same, because the program obeys to *intra-thread semantics* of the final program order.

The *intra-thread semantics* of `threadA`:

```kotlin
//c = 2
b = 2
//d = c + 2
a = 1
b = a + b
```

The *intra-thread semantics* of `threadB`:

```kotlin
c = 2
//b = 2
d = c + 2
//a = 1
//b = a + b
```

Each time the evaluation of thread generates an *inter-thread action*, it must match the *inter-thread action* that comes next in *program order*. If the action is a read, then further evaluation of thread uses the value seen by that read action as determined by the *memory model*.

It may seems like multithread programs can violate *intra-thread semantics*:

```kotlin
var c = 0
val tA = thread { c =  1 }
val tB = thread { c = -1 }
ta.join()
tb.join()
println(c)
```

There are 2 possible *program orders*, because the position of the actions `(c=1)`,`(c=-1)` is not defined relative to each other: no constraints by *memory model*.

```kotlin
var c = 0
// some actions, that dont affect c
c = 1 // by thread tA
// some actions, that dont affect c
c = -1 // by thread tB
// some actions, that dont affect c
println(c) // reading c=-1 here

var c = 0
// some actions, that dont affect c
c = -1 // by thread tB
// some actions, that dont affect c
c = 1 // by thread tA
// some actions, that dont affect c
println(c) // reading c=1 here
```

But *intra-thread semantics* **are not violated**, as any `write c` action is executed **before** `read c` action. The order of write actions is undefined, because there are no constraints. The behavior of the program is still predictable: `c = ` either `1` or `-1`.  It is impossible to violate *intra-thread semantics*, because these rules are something that compiler and JVM implementation must obey.

```kotlin
var c = 0
val tA = thread {
	c =  1
}
val tB = thread {
	tA.join()
	c = -1
}
ta.join()
tb.join()
println(c)
```

Now program order is:

```kotlin
var c = 0
c =  1
c = -1
println(c)
```

because `tA.join()` in thread `tB` applied a constraint, that is taken into account by *memory model*.

## Synchronization Order

Every execution has a _synchronization order_. A synchronization order is a total order over all of the synchronization actions of an execution.

## Happens-before Relationship

- Two actions can be ordered by a _happens-before_ relationship. If one action _happens-before_ another, then the first is visible to and ordered before the second.
	- An unlock on a monitor _happens-before_ every subsequent lock on that monitor.
	- A write to a `volatile` field _happens-before_ every subsequent read of that field.
	- A call to `start()` on a thread _happens-before_ any actions in the started thread.
	- All actions in a thread _happen-before_ any other thread successfully returns from a `join()` on that thread.
	- The default initialization of any object _happens-before_ any other actions (other than default-writes) of a program.

