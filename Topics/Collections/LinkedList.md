# LinkedList


There was a problem of getting pre-last element of a `LinkedList`. We can use  `Iterator`s for this purpose:

```kotlin
val first  : Element = linkedList.first
val last   : Element = linkedList.last

val second : Element = list.iterator().apply { next() }.next()
val preLast: Element = list.descendingIterator().apply { next() }.next()
```

[StackOverflow](https://stackoverflow.com/questions/25146945/how-to-get-previous-element-in-a-linkedlist)

