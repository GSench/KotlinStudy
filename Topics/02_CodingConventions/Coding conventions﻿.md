
# Coding conventions﻿

https://kotlinlang.org/docs/coding-conventions.html

## Some Kotlin specific insights:

- `package`s does not have to match folder names (like in Java)
- Placing multiple declarations in the same Kotlin source file is allowed
- **In Kotlin, semicolons are optional**, and therefore line breaks are significant.
- Trailing commas﻿

## Source code organization﻿

`package`s does not have to match folder names (like in Java)

**But it should for coding style purpose.**

> On JVM: In projects where Kotlin is used together with Java, Kotlin source files should reside in the same source root as the Java source files, and follow the same directory structure: each file should be stored in the directory corresponding to each package statement.

## Naming

- file name should be the same as the name of the class
- name describing what the file contains for files with multiple classes
- UpperCamelCase for:
	- file names
	- classes and objects
	- factory functions used to create instances of classes can have the same name as the abstract return type (class in UpperCamelCase)
	- properties holding references to singleton objects can use the same naming style as object declarations (class in UpperCamelCase)
- lowerCamelCase for:
	- functions, properties and local variables
	- top-level or object properties which hold objects with behavior or mutable data
- SCREAMING_SNAKE_CASE for: 
	- names of constants
- Names of packages are always lowercase and do not use underscores
- method names with spaces enclosed in backticks are allowed but recommeded only for test methods and not supported by the Android runtime
- If a class has two properties which are conceptually the same but one is part of a public API and another is an implementation detail, use an underscore as the prefix for the name of the private property
- name of a class is usually a noun or a noun phrase explaining what the class is
- name of a method is usually a verb or a verb phrase saying what the method does
- When using an acronym as part of a declaration name, capitalize it if it consists of two letters (IOStream); capitalize only the first letter if it is longer (XmlFormatter, HttpInputStream).
- avoid using meaningless words

### Multiplatform projects﻿
- **jvm**Main/kotlin/Platform.**jvm**.kt
- **android**Main/kotlin/Platform.**android**.kt
- **iosMain**/kotlin/Platform.**ios**.kt

## Source file organization

Placing multiple declarations (classes, top-level functions or properties) in the same Kotlin source file is encouraged as long as these declarations are closely related to each other semantically, and the file size remains reasonable (not exceeding a few hundred lines).

Avoid creating files just to hold all extensions of some class. (place them in same file)

### Class layout﻿
The contents of a class should go in the following order:
1. Property declarations and initializer blocks
2. Secondary constructors
3. Method declarations
4. Companion object

Put related stuff together, so that someone reading the class from top to bottom can follow the logic of what's happening. Choose an order (either higher-level stuff first, or vice versa) and stick to it.

## Formatting﻿

Use four spaces for indentation. Do not use tabs.

> **In Kotlin, semicolons are optional**, and therefore line breaks are significant. The language design assumes Java-style braces, and you may encounter surprising behavior if you try to use a different formatting style.

### Indentation﻿

Use four spaces for indentation. Do not use tabs.

- Put spaces around binary operators (a + b). Exception: don't put spaces around the "range to" operator (0..i).
- Do not put spaces around unary operators (a++).
- Put spaces between control flow keywords (if, when, for, and while) and the corresponding opening parenthesis.
- Do not put a space before an opening parenthesis in a primary constructor declaration, method declaration or method call.
- Never put a space after (, [, or before ], )
- Never put a space around . or ?.: foo.bar().filter { it > 2 }.joinToString(), foo?.bar()
- Put a space after //: // This is a comment
- Do not put spaces around angle brackets used to specify type parameters: class Map<K, V> { ... }
- Do not put spaces around ::: Foo::class, String::length
- Do not put a space before ? used to mark a nullable type: String?

### Modifiers order﻿

- Place all annotations before modifiers:
- public / protected / private / internal
- expect / actual
- final / open / abstract / sealed / const
- external
- override
- lateinit
- tailrec
- vararg
- suspend
- inner
- enum / annotation / fun // as a modifier in `fun interface`
- companion
- inline / value
- infix
- operator
- data

Unless you're working on a library, omit redundant modifiers (for example, public).


### Multiline calls for:

- Classes with longer headers
- multiple interfaces
- classes with a long supertype list
- function signature that doesn't fit on a single line
- If the function has an expression body whose first line doesn't fit on the same line as the declaration
- In long argument lists, put a line break after the opening parenthesis
- When wrapping chained calls, put the . character or the ?. operator on the next line

### Trailing commas﻿
A trailing comma is a comma symbol after the last item in a series of elements:

```Kotlin
class Person(
    val firstName: String,
    val lastName: String,
    val age: Int, // trailing comma
)
```

## Avoid redundant constructs﻿

- If a function returns Unit, the return type should be omitted:
- Omit semicolons whenever possible.
- Don't use curly braces when inserting a simple variable into a string template. Use curly braces only for longer expressions.

## Idiomatic use of language features

- Prefer using immutable data to mutable.
- Prefer declaring functions with default parameter values to declaring overloaded functions.
- If you have a functional type or a type with type parameters which is used multiple times in a codebase, prefer defining a type alias for it
- In lambdas which are short and not nested, it's recommended to use the it convention instead of declaring the parameter explicitly. In nested lambdas with parameters, always declare parameters explicitly.
- Avoid using multiple labeled returns in a lambda.
- Use the named argument syntax when a method takes multiple parameters of the same primitive type
- Prefer using the expression form of try, if, and when
- Prefer using if for binary conditions instead of when.
- If you need to use a nullable Boolean in a conditional statement, use if (value == true) or if (value == false) checks.
- Prefer using higher-order functions (filter, map etc.) to loops.
- Use the ..< operator to loop over an open-ended range:
- Prefer string templates to string concatenation.
- Use extension functions liberally. Every time you have a function that works primarily on an object, consider making it an extension function accepting that object as a receiver.
- Declare a function as infix only when it works on two objects which play a similar role.
- If you declare a factory function for a class, avoid giving it the same name as the class itself.

- A public function/method returning an expression of a platform type must declare its Kotlin type explicitly (e.g. Java calls)
- Any property (package-level or class-level) initialized with an expression of a platform type must declare its Kotlin type explicitly (e.g. Java calls)

