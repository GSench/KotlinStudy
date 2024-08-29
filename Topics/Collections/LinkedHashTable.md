# LinkedHashTable

#TODO Debug

```kotlin
private class LinkedHashTable<T> {  
    private val hashSet = HashMap<T, Element<T>>()  
    class Element<T>(  
        val value: T,  
        var prev: Element<T>?,  
        var next: Element<T>?  
    )  
    var head: Element<T>? = null  
    var tail: Element<T>? = null  
    fun addFirst(value: T) {  
        if(value in hashSet) return  
        val element = Element(value,null, head)  
        if(head==null) tail = element  
        head = element  
        hashSet[value] = element  
    }  
    fun addLast(value: T) {  
        if(value in hashSet) return  
        val element = Element(value, tail, null)  
        if(tail==null) head = element  
        tail = element  
        hashSet[value] = element  
    }  
    fun remove(value: T) {  
        if(value !in hashSet) return  
        val element = hashSet.remove(value)  
        if(element === head) head = element?.next  
        if(element === tail) tail = element?.prev  
        element?.prev?.next = element?.next  
    }  
    fun addAfter(value: T, after: T){  
        if(after !in hashSet) return  
        if(value in hashSet) remove(value)  
        val elementPrev = hashSet[after]  
        val element = Element(value, elementPrev, elementPrev?.next)  
        elementPrev?.next?.prev = element  
        elementPrev?.next = element  
        if(elementPrev === tail) tail = element  
        hashSet[value] = element  
    }  
    fun addBefore(value: T, before: T){  
        if(before !in hashSet) return  
        if(value in hashSet) remove(value)  
        val elementNext = hashSet[before]  
        val element = Element(value, elementNext?.prev, elementNext)  
        elementNext?.prev?.next = element  
        elementNext?.prev = element  
        if(elementNext === head) head = element  
        hashSet[value] = element  
    }  
}
```