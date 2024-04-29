# Null safety

https://kotlinlang.org/docs/null-safety.html

- All types cannot have `null` value by default: `val s: String = null` ERROR
- Nullable versions of types:
    `val s: String? = null`. Unable to run fun of `s` 'as is': `val s: String? = "abc"; s.length` ERROR, as `s` is nullable
  
- Using nullable variables:
  - **Smart-cast** (explicid !=null check)
  - **Safe calls** `val b: String? = null; println(b?.length)`, `?.length` returns `Int?` instead of `Int`, so `println` prints "null";<br>
    `bob?.department?.head?.name` here any of `?.` result is `SomeType?`, so such a chain returns null if any of the properties in it is null.<br>
    `str?.let{println(it)}` do nothing if `str == null`
  - **Nullable receiver**﻿ of Extension functions `val p: person? = null; println(p.toString())` - prints "null", as toString() is ext.fun. defined for null values
  - **Elvis operator** `val l = b?.length ?: -1` <=>`val l: Int = if (b != null) b.length else -1`; other example: `val p = node.getParent() ?: return null`
- **Not-null assertion operator** `!!`: `val l = b!!.length`
