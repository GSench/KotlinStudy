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

## Intra-thread Semantics

https://stackoverflow.com/questions/25711048/understanding-intra-thread-semantics

The *memory model* determines what values can be read at every point in the program.
The program obeys **<u>intra-thread semantics</u>**: The actions of each thread individually must behave according to the semantics of that thread, with the exception that the values seen by each read are determined by the *memory model*.

**NOTICE**: in [documentation](https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html#jls-17.4)
> The actions of each thread **in isolation** must behave as ...

It essentially means that
> If we consider the actions of each thread individually, these actions must behave as ...

**We do not mean the isolation of a thread!**

*Intra-thread semantics* are the semantics for single-threaded programs, and allow the complete prediction of the behavior of a thread based on the values seen by read actions within the thread.

**IMPORTANT NOTICE**: *Intra-thread semantics* allows to predict THE BEHAVIOUR of a thread, NOT THE RESULT! *Intra-thread semantics* constraints the action execution order within a thread, and thats it. The result of a thread CAN STILL BE AFFECTED by other threads!

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

*Intra-thread semantics* are violated if some JVM implementation execute the actions in the order:

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

In fact *intra-thread semantics* prescribe read-write order **within a thread**:

```kotlin
// ... some actions
a = 1
// ... some actions, NOT read/write a
// ... some actions, NOT read/write a
b = a + 1 // (a = 1) MAY BE executed ANYWHEN in the interval [a=1 action; read a action)
```

To determine if the actions of each thread in an execution are legal (according *intra-thread semantics*), we simply evaluate the implementation of each thread as it would be performed in a single-threaded context, as defined in the rest of the memory model specification.

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
/*threadB*/ d = c + 2 // (c = 2) must be executed before this action
/*threadA*/ a = 1 // actions a = 1 and b = 2 of threadA are executed in reverse order, as they dont contradict to each other
/*threadA*/ b = a + b // (a = 1) and (b = 2) must be executed before this action
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

An important notice on the result of a thread in multithread programs.

```kotlin
var a = 0
var c = 0
val threadA = thread {
	c = 1
	a = c
}
val threadB = thread {
	c = -1
}
```

These actions of both threads may appear in total execution order as follows:

```kotlin
/*threadA*/ c = 1
/*threadB*/ c = -1
/*threadA*/ a = c
```

And the result of this program is `a=-1`, but not `a=1`, like it would be if `threadA` was executed in isolation. It may seems like such multithread program violates *intra-thread semantics*, but it's not.

*Intra-thread semantics* only guarrants **the execution order** of both threads **within these threads**. `write c` action in `threadA` is executed **before** `read c` action in `threadA`. `threadB` interfered into program logic and executed `write c` action. The order of `write c` actions is undefined inter-thread, because there are no memory model constraints. Thus `threadA` actions became inconsistent with the results of this thread. But *intra-thread semantics* **was not violated**! It is impossible to violate *intra-thread semantics*, because these rules are something that compiler and JVM implementation must obey.

## Program Order

The **<u>program order</u>** is a total order in which *inter-thread actions* would be performed according to the *intra-thread semantics*.

A set of actions is **<u>sequentially consistent</u>** if all actions occur in a total order (the execution order) that is consistent with *program order*, and furthermore, each read of a variable sees the written value such that:
- write comes before read in the execution order
- there is no other write that comes between write and read in the execution order.

Each time the evaluation of a thread generates an *inter-thread action*, it must match the *inter-thread action* that comes next in *program order*. If the action is a read, then further evaluation of thread uses the value seen by that read action as determined by the *memory model*.

Considering the example:

```kotlin
var a = 0
var b = 0
var c = 0
val threadA = thread {
	c = 1
	a = c
}
val threadB = thread {
	c = -1
	
	b = c
}
```

There are $4!=24$ possible *program orders*, depending on the order of 4 actions in both threads. *Intra-thread semantics* restricts possible *program orders* because `write c` actions in both threads must be executed before `read c` actions in both threads. So all the possible *program orders* are:

```kotlin
| c = 1    | c = 1    | c = 1    | c = -1   | c = -1   | c = -1
| a = c    | c = -1   | c = -1   | b = c    | c = 1    | c = 1 
| c = -1   | a = c    | b = c    | c = 1    | a = c    | b = c 
| b = c    | b = c    | a = c    | a = c    | b = c    | a = c 
```

According to *intra-thread semantics* any other total execution order is impossible (there are $4!-6=18$ left).

Although this program is *sequentially inconsistent*, because in case `c = -1` is executed after `c = 1`, there is an other write that comes between write and read in the execution order (the same for the opposite case `c=-1` `c=1`). To fix this we can add some constraint, that prescribe *memory model* to execute these action in exact order:

```kotlin
var a = 0
var b = 0
var c = 0
val monitor = Any()
var wasNotified = false
val threadA = thread {
	c = 1
	synchronized(monitor){
		wasNotified = true
		(monitor as java.lang.Object).notify()
	}
	a = c
}
val threadB = thread {
	synchronized(monitor){
		while(!wasNotified)
			(monitor as java.lang.Object).wait()
	}
	c = -1
	b = c
}
```

Now all the possible *program orders* are:

```kotlin
| c = 1    | c = 1    | c = 1
| a = c    | c = -1   | c = -1
| c = -1   | a = c    | b = c
| b = c    | b = c    | a = c
```

because `(monitor as java.lang.Object).wait()` in thread `threadB` applied a constraint, that is taken into account by *memory model*.

But this program is still *sequentially inconsistent*: the order of actions `c=1`, `c=-1` is still undefined relatively to `a=c`. We need to add stronger constraint:

```kotlin
var a = 0
var b = 0
var c = 0
val threadA = thread {
	c = 1
	a = c
}
val threadB = thread {
	threadA.join()
	c = -1
	b = c
}
```

Now the only possible *program order* is:

```kotlin
| c = 1
| a = c
| c = -1
| b = c
```

And this program is *sequentially consistent*.

*Sequential consistency* is a very strong guarantee that is made about visibility and ordering in an execution of a program. Within a sequentially consistent execution, there is a *total order* over all individual actions (such as reads and writes) which is consistent with the order of the program, and each individual action is atomic and is immediately visible to every thread.

If a program has no *data races*, then all executions of the program will appear to be *sequentially consistent*.

From the lecture [Java Memory Model ...and the pragmatics of it: by Aleksey Shipilev](https://shipilev.net/blog/2014/jmm-pragmatics/) ([presentation slides](https://shipilev.net/talks/narnia-2555-jmm-pragmatics-en.pdf)):

> The program order does not provide the ordering guarantees. The only reason it exists is to provide the link between possible executions and the original program. Given the simple schematics of actions and executions, you can construct an infinite number of executions. These executions are detached from any reality; they are just the "primordial soup", containing everything possible by construction. Somewhere in this soup float the executions which can explain a particular outcome of the given program, and the set of all such plausible executions cover all plausible outcomes of the program. Here is where Program Order (PO) jumps in. To filter out the executions we can take to reason about the particular program, we have **intra-thread consistency** rules, which eliminate all unrelated executions. Intra-thread consistency is the very first execution filter, which most people do implicitly in their heads when dealing with JMM.


## Synchronization Order

Every execution has a _synchronization order_. A synchronization order is a total order over all of the synchronization actions of an execution.

## Happens-before Relationship

- Two actions can be ordered by a _happens-before_ relationship. If one action _happens-before_ another, then the first is visible to and ordered before the second.
	- An unlock on a monitor _happens-before_ every subsequent lock on that monitor.
	- A write to a `volatile` field _happens-before_ every subsequent read of that field.
	- A call to `start()` on a thread _happens-before_ any actions in the started thread.
	- All actions in a thread _happen-before_ any other thread successfully returns from a `join()` on that thread.
	- The default initialization of any object _happens-before_ any other actions (other than default-writes) of a program.

## Well-Formed Executions

## `final` Field Semantics


## Most common multithread errors

- https://jenkov.com/tutorials/java-concurrency/thread-safety.html

Threads communicate primarily by sharing access to fields and the objects reference fields refer to. This form of communication is extremely efficient, but makes two kinds of errors possible: **thread interference** and **memory consistency errors**.
### [Thread Interference](https://docs.oracle.com/javase/tutorial/essential/concurrency/interfere.html)

Interference happens when two operations, running in different threads, but acting on the same data, _interleave_.

One of the most common **Example 1** is:

```kotlin
class Counter {
    var c = 0
	    private set
    fun increment() = c++
    fun decrement() = c--
}
fun main() {
	val counter = Counter()
	val threadA = thread { counter.increment() }
	val threadB = thread { counter.decrement() }
	println(counter.c) // -1
}
```

`c++`/`c--` can be decomposed into three steps:

1. Retrieve the current value of `c`.
2. Increment/Decrement the retrieved value by `1`.
3. Store the result value back in `c`.

So the result actions consequence might be:

1. `threadA`: Retrieve `c`.
2. `threadB`: Retrieve `c`.
3. `threadA`: Increment retrieved value; result is `1`.
4. `threadB`: Decrement retrieved value; result is `-1`.
5. `threadA`: Store result in `c`; `c` is now `1`.
6. `threadB`: Store result in `c`; `c` is now `-1`.

Let's consider **Example 2**:

```kotlin
var a = 0  
var b = 0  
val thread1 = thread {  
    a = 1  
    println("b = $b")  
}  
val thread2 = thread {  
    b = 1  
    println("a = $a")  
}
```

According to Java Memory Model all actions in both threads are independent and have no *happends-before* constraints. So there can be several possible outputs depending on different actions order:

- **Case 1**: both treads are executed consequtively
```kotlin
// a = 0, b = 0
/*thread1*/ a = 1 // a = 1, b = 0 
/*thread1*/ println("b = $b") // b = 0
/*thread2*/ b = 1 // a = 1, b = 1
/*thread2*/ println("a = $a") // a = 1
```

- **Case 2**: the same, but `thread2` executed before `thread1`:
```kotlin
// a = 0, b = 0
/*thread2*/ b = 1 // a = 0, b = 1
/*thread2*/ println("a = $a") // a = 0
/*thread1*/ a = 1 // a = 1, b = 1 
/*thread1*/ println("b = $b") // b = 1
```

- **Case 3**: Actions of thread1 and thread2 are executed asynchronously and merged:
```kotlin
// a = 0, b = 0
/*thread1*/ a = 1 // a = 1, b = 0 
/*thread2*/ b = 1 // a = 1, b = 1
/*thread1*/ println("b = $b") // b = 1
/*thread2*/ println("a = $a") // a = 1
```

- **Case 4**: The same, but `thread2` executed 'inside' actions of `thread1`:
```kotlin
// a = 0, b = 0
/*thread1*/ a = 1 // a = 1, b = 0 
/*thread2*/ b = 1 // a = 1, b = 1
/*thread2*/ println("a = $a") // a = 1
/*thread1*/ println("b = $b") // b = 1
```

### Reordering issues

The same **Example 2**:

```kotlin
var a = 0  
var b = 0  
val thread1 = thread {  
    a = 1  
    println("b = $b")  
}  
val thread2 = thread {  
    b = 1  
    println("a = $a")  
}
```

Since actions in both threads are independent and have no *happends-before* constraints, they could be *reordered* for the sake of optimization. It wont affect the outut in **Case 1** or **Case 2**, but gives another result for *Case 3* and **Case 4**:

- **Case 5**: Actions (assign and println) of `thread2` are reordered. Threads' actions are executed asynchronously.
```kotlin
// a = 0, b = 0
/*thread1*/ a = 1 // a = 1, b = 0
/*thread2*/ println("a = $a") // a = 1
/*thread1*/ println("b = $b") // b = 0
/*thread2*/ b = 1 // a = 1, b = 1
```

- **Case 6**: Actions (assign and println) of `thread1` are reordered. Threads' actions are executed asynchronously.
```kotlin
// a = 0, b = 0
/*thread2*/ b = 1 // a = 1, b = 1
/*thread1*/ println("b = $b") // b = 1
/*thread2*/ println("a = $a") // a = 0
/*thread1*/ a = 1 // a = 1, b = 0
```

- **Case 7**: Both actions (assign and println) in both threads are reordered. Threads' actions are executed asynchronously.
```kotlin
// a = 0, b = 0
/*thread1*/ println("b = $b") // b = 0
/*thread2*/ println("a = $a") // a = 0
// or the same:
/*thread2*/ println("a = $a") // a = 0
/*thread1*/ println("b = $b") // b = 0

/*thread1*/ a = 1 // a = 1, b = 0 
/*thread2*/ b = 1 // a = 1, b = 1
// or the same if last 2 actions:
/*thread2*/ b = 1 // a = 0, b = 1
/*thread1*/ a = 1 // a = 1, b = 1 
```

### Memory Consistency Errors

- https://docs.oracle.com/javase/tutorial/essential/concurrency/memconsist.html
- https://jenkov.com/tutorials/java-concurrency/java-memory-model.html
- https://jenkov.com/tutorials/java-concurrency/false-sharing.html

The same **Example 2**:

```kotlin
var a = 0  
var b = 0  
val thread1 = thread {  
    a = 1  
    println("b = $b")  
}  
val thread2 = thread {  
    b = 1  
    println("a = $a")  
}
```

Memory Consistency Errors occur when different threads have inconsistent views of what should be the same data.

There may be other cases, because memory between 2 threads is not syncronized. So writing variable in one thread may not affect reading this variable in the other.

If variable have no *happends-before* constraints, like `synchronized(){}`, JVM may create local copies of variables for both threads, and use them during these `Thread`s execution. This may happen if both threads are executed on different CPUs. Then each CPU have local copy of variables in registers. ([This](https://jenkov.com/tutorials/java-concurrency/cache-coherence-in-java-concurrency.html) article describes what is really happening)
Variables in their common memory space (in RAM, or in their original CPU's local memory space) are syncronized with their copies after `Thread` finish. Such mechanism allows to optimize memory usage for both `Thread`s and reduse expensive RAM fetches.

Considering **Case 3**:
**Case 8**: Both threads are executed on different CPUs, which have local copies of variables in registers.
```kotlin
// RAM: a = 0, b = 0
/*thread1*/ // a = 0, b = 0
/*thread2*/ // a = 0, b = 0
/*thread1*/ a = 1 // a = 1, b = 0 
/*thread2*/ b = 1 // a = 0, b = 1
/*thread1*/ println("b = $b") // b = 0
/*thread2*/ println("a = $a") // a = 0
// RAM: a = 1, b = 1
```


