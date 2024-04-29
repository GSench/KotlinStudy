# Kotlin command-line compiler

https://kotlinlang.org/docs/command-line.html

## REPL

https://kotlin-quick-reference.com/025-R-kotlin-repl.html

The Kotlin REPL (“Read-Evaluate-Print-Loop”) is a command-line interpreter that you use as a “playground” area to test your Kotlin code. To start a REPL session, just type kotlinc at your operating system command line.

cmd
```command-line
>kotlin
Welcome to Kotlin version 1.9.21 (JRE 15.0.2+7-27)
Type :help for help, :quit for quit
>>> println("hello REPL!")
println("hello REPL!")hello REPL!
>>> :quit

>
```

## Compiler

### no package

HelloWorld.kt
```Kotlin
fun main() {
	println("Hello World!")
}
```
cmd
```command-line
>kotlinc HelloWorld.kt

>dir
10.12.2023  22:12    <DIR>          .
10.12.2023  22:12    <DIR>          ..
10.12.2023  22:11                80 HelloWorld.kt
10.12.2023  22:12               650 HelloWorldKt.class
10.12.2023  22:12    <DIR>          META-INF

>java HelloWorldKt
Hello World!

>kotlin HelloWorldKt
Hello World!

>
```

### package

https://discuss.kotlinlang.org/t/kotlin-1-4-21-where-is-kotlin-runtime-jar/20655/4 :
> ... - I was following Hadi Hariri’s “Introduction To Kotlin Programming” on O’Reilly’s website. ... somewhat out of date since since ... 2016. **His IntelliJ examples cannot be followed verbatim** ...

https://www.jetbrains.com/help/idea/kotlin-repl.html
HelloWorld.kt
```Kotlin
package com.gsench.kotlincourse

fun main() {
	println("Hello World!")
}
```
cmd
```command-line
>kotlinc HelloWorld.kt -include-runtime -d HelloWorld.jar

>dir
10.12.2023  22:18    <DIR>          .
10.12.2023  22:18    <DIR>          ..
10.12.2023  22:18         4 908 071 HelloWorld.jar
10.12.2023  22:17                78 HelloWorld.kt

>java -jar HelloWorld.jar
Hello World!

>kotlin HelloWorld.jar
Hello World!

>
```
> The -include-runtime option makes the resulting .jar file self-contained and runnable by including the Kotlin runtime library in it.

> Since binaries compiled this way depend on the Kotlin runtime, you should make sure the latter is present in the classpath whenever your compiled library is used.

Seems like kotlin runtime is not necessary to include for simple applications that don't use Kotlin libraries. Worked for me:
cmd
```command-line
>kotlinc HelloWorld.kt -d HelloWorld.jar

>dir
10.12.2023  22:56    <DIR>          .
10.12.2023  22:56    <DIR>          ..
10.12.2023  22:58             1 097 HelloWorld.jar
10.12.2023  22:17                78 HelloWorld.kt

>java -jar HelloWorld.jar
Hello World!

>kotlin HelloWorld.jar
Hello World!

>
``` 
https://stackoverflow.com/a/43935811
> Therefore, it seems that:
> 1. the “Kotlin runtime” is actually just a “Kotlin class library” (and not strictly a separate “runtime” like JRE);
> 2. when Kotlin code is compiled into Java byte-code, the Kotlin is gone, but the byte-code that replaces it needs access to the Kotlin class library; and so
> 3. the Kotlin runtime must be available to any Java byte-code which was originally Kotlin code, which can be done by bundling the Kotlin runtime with such code.

## script
Kotlin can also be used as a scripting language. A script is a Kotlin source file (.kts) with top-level executable code.

list_folders.kts
```Kotlin
import java.io.File

// Get the passed in path, i.e. "-d some/path" or use the current path.
val path = if (args.contains("-d")) args[1 + args.indexOf("-d")]
           else "."

val folders = File(path).listFiles { file -> file.isDirectory() }
folders?.forEach { folder -> println(folder) }
```
cmd
```command-line
>kotlinc -script list_folders.kts -- -d \src
\src\nopackage
\src\package
\src\script
kotlin.Unit

>
```