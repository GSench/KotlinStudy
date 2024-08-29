# MutableList

## buildList

instead of:
```kotlin
val list = mutableList<String>()
	.apply{ if(cond1) add("Alfa") }
	.apply{ 
		add("Bravo")
		add("Charlie")
	}
	.apply{
		if(cond2) add("Delta")
		else add("Echo")
	}
	.apply{ add("Bravo") }

```

use [`buildList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/build-list.html):
```kotlin
val list = buildList {
	if(cond1) add("Alfa")
	add("Bravo")
	add("Charlie")
	if(cond2) add("Delta")
	else add("Echo")
	add("Bravo")
}
```

