# Array

```kotlin
// Initializers
Array<Book>(size)
IntArray(size){0}
IntArray(11){i -> 2.pow(i)}
intArrayOf(vararg elements: Int): IntArray
arrayOf<T>(vararg elements: T): Array<T>

// Properties
.size
.isEmpty()
.indices
.lastIndex
.lastElement

// Functions
.any { it == 0 } // true if contains any of
.none { it != 0 } // true if contains none of
IntArray.sum()
.fold(1){acc, next -> acc * next} : Int // Mul all elements
.take(n) .takeLast(n) // first/last n elements
.sorted()
.reverse()
.zip(otherArray): List<Pair<T,V>>
.count{it==0}

// Idioms
for(v in array) {}
for(i in array.indices) {}
for(i in 0..array.lastIndex) {}
for ((i, v) in array.withIndex()) {}
array.forEach { v -> }
array.forEachIndexed { index, element -> }
arrayA contentEquals arrayB // compare by content WTIH ORDER
arrayA == arrayB // compare by REFERENCE!
```

# String

```kotlin
// Initializers
"ABCD"
array.joinToString(",")
list.joinToString{it.toString()+"\n"}

// Properties
.length

// Functions
str[index]
.toCharArray()
.isLetter()
.isDigit()
.toInt()
.toInt(radix: Int)

// Idioms
strA == strB // compare contents of Strings
for(chr in str){}
```

## Char

```kotlin
// Initializers
'A'
array.toCharArray()[i]

// Properties

// Functions
.isLetter()
.isDigit()
.uppercase(): String // can return multiple characters for locale specific Char 
.uppercaseChar(): Char
.lowercase(): String // can return multiple characters for locale specific Char 
.lowercaseChar(): Char
.toInt() // ASCII code

// Idioms
chrA == chrB
'a'+1   //= 'b': Char
'z'-'a' //=  25: Int
```

## StringBuilder

```kotlin
// Initializers
StringBuilder()
StringBuilder("ABC")

// Properties

// Functions
.append(smth)
.toString()

// Idioms
```

# List

```kotlin
// Initializers
array.toMutableList()
linkedList.toList()

// Properties
.size

// Functions
.sorted()
.sortedDescending()
.sortedBy{f(it)}
.sortedByDescending{f(it)}
.take(n)

// Idioms
listA == listB // compare contents of Lists
```

## LinkedList

```kotlin
// Initializers
LinkedList<String>()

// Properties
.size

// Functions
.add(value)
.addFirst(value)
.addLast(value)
.getFirst()
.getLast()
.remove(value)
.remove() .removeFirst()
.removeLast()
.clear()

// Idioms
LinkedList(list) // copy a list (or a LinkedList) as a LinkedList
```

# Hash

## HashMap

```kotlin
// Initializers
HashMap<Char, Int>(size)

// Properties
.size
.keys
.values

// Functions
hashMap[key] = value
.toList(): Pair<K,V>

// Idioms
hashMapA == hashMapB // compare contents of HashMaps
hashMap[key] = (hashMap[key] ?: 0) + 1 //increment
hashMap.forEach {(key, value) -> }
for((key, value) in hashMap) {}
```

## HashSet

```kotlin
// Initializers
HashSet<Char>()
HashSet<Int>(size)
HashSet<String>(size,loadFactor)
array.toHashSet()

// Properties
.size

// Functions
.add(key)
.remove(key)
.clear()
.iterator(): Iterator

// Idioms
if(num in hashSet){}
if(num !in hashSet){}
```

# Stack

```kotlin
// Initializers
Stack<Int>()

// Properties

// Functions
.push() // add to top
.pop() // get from top and remove
.peek() // get from top
.isEmpty()

// Idioms
```
