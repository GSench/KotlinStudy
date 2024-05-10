# Serialization

## Marshalling & Serialization ✅

### [Marshalling](https://en.wikipedia.org/wiki/Marshalling_(computer_science))

**<u>Marshalling</u>** is the process of transforming the memory representation of an object into a data format suitable for storage or transmission, especially between different runtimes. It is typically used when data must be moved between different parts of a computer program or from one program to another. 

- Marshalling is describing the overall intent or process to transfer some live object from a client to a server. The point with marshalling an object is to have that object that is present in one running program be present in another running program; that is, an object on the client should be transferred to the server, which is a form of reification allowing the object’s structure, data and state to transit from a runtime to another, leveraging an intermediate, serialized, "dry" representation (which is of second importance) circulating onto the communication socket.
- Serialization does not necessarily have this same intent, since it is only concerned about transforming data to generate that intermediate, "dry" representation of the object (for example, into a stream of bytes) which could then be either reified in a different runtime, or simply stored in a database, a file or in memory.

Marshalling and serialization might thus be done differently, although some form of serialization is usually used to do marshalling.

### [Serialization](https://en.wikipedia.org/wiki/Serialization)

In computing, **<u>serialization</u>** is the process of translating a data structure or object state into a format that can be stored (e.g. files in secondary storage devices, data buffers in primary storage devices) or transmitted (e.g. data streams over computer networks) and reconstructed later (possibly in a different computer environment). The opposite operation, extracting a data structure from a series of bytes, is **<u>deserialization</u>**.



## Kotlin Serialization ✅

- https://kotlinlang.org/docs/serialization.html
- https://github.com/Kotlin/kotlinx.serialization
- https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/serialization-guide.md

*Serialization* is the process of converting data used by an application to a format that can be transferred over a network or stored in a database or a file. In turn, *deserialization* is the opposite process of reading data from an external source and converting it into a runtime object. Together, they are essential to most applications that exchange data with third parties.

In Kotlin, data serialization tools are available in a separate component, [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization). It consists of several parts: the `org.jetbrains.kotlin.plugin.serialization` Gradle plugin, [runtime libraries](https://kotlinlang.org/docs/serialization.html#libraries), and compiler plugins.

`kotlinx.serialization` includes libraries for various serialization formats:

- [JSON](https://www.json.org/): [`kotlinx-serialization-json`](https://github.com/Kotlin/kotlinx.serialization/blob/master/formats/README.md#json)
- [Protocol buffers](https://developers.google.com/protocol-buffers): [`kotlinx-serialization-protobuf`](https://github.com/Kotlin/kotlinx.serialization/blob/master/formats/README.md#protobuf)
- [CBOR](https://cbor.io/): [`kotlinx-serialization-cbor`](https://github.com/Kotlin/kotlinx.serialization/blob/master/formats/README.md#cbor)
- [Properties](https://en.wikipedia.org/wiki/.properties): [`kotlinx-serialization-properties`](https://github.com/Kotlin/kotlinx.serialization/blob/master/formats/README.md#properties)
- [HOCON](https://github.com/lightbend/config/blob/master/HOCON.md): [`kotlinx-serialization-hocon`](https://github.com/Kotlin/kotlinx.serialization/blob/master/formats/README.md#hocon) (only on JVM)

## Testing Example ✅

```kotlin
data class SomeObjId (val id: Int)  
data class SomeObj (  
    val id: SomeObjId,  
    val title: String  
)  
fun main() {  
    val someObjects = arrayOf(
	    SomeObj(SomeObjId(1), "title1"),
	    SomeObj(SomeObjId(2), "title2")
	)  
    for(someObj in someObjects) println(someObj)  
}
```
```output
SomeObj(id=SomeObjId(id=1), title=title1)
SomeObj(id=SomeObjId(id=2), title=title2)
```

## JSON Serialization ✅

### [Google Gson library](https://github.com/google/gson)

Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object.

Gson can work with arbitrary Java objects including pre-existing objects that you do not have source code of.

[Guide](https://github.com/google/gson/blob/main/UserGuide.md)

#### Setup

`build.gradle.kts`
```kotlin
dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
}
```

#### Init

```kotlin
import com.google.gson.Gson
val gson = Gson()
```

#### Serialize

```kotlin
val encObjects: String = gson.toJson(someObjects)  
println(encObjects)
```
```output
[{"id":{"id":1},"title":"title1"},{"id":{"id":2},"title":"title2"}]
```

#### Deserialize

```kotlin
val decObjects = gson.fromJson(encObjects, Array<SomeObj>::class.java)  
for(someObj in decObjects) println(someObj)
```
```output
SomeObj(id=SomeObjId(id=1), title=title1)
SomeObj(id=SomeObjId(id=2), title=title2)
```

### [Kotlin Json Serialization](https://kotlinlang.org/docs/serialization.html)

Kotlin JSON Serialization documentation: https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/json.md

#### Setup

`build.gradle.kts`
```kotlin
plugins {
    kotlin("plugin.serialization") version "1.9.24"
}
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}
```

#### Init

```kotlin
import kotlinx.serialization.Serializable

@Serializable  
data class SomeObjId (val id: Int)  
@Serializable  
data class SomeObj (  
    val id: SomeObjId,  
    val title: String  
)
```

#### Serialize

```kotlin
val encObjects: String = Json.encodeToString(someObjects)  
println(encObjects)
```
```output
[{"id":{"id":1},"title":"title1"},{"id":{"id":2},"title":"title2"}]
```

#### Deserialize

```kotlin
val decObjects = Json.decodeFromString<Array<SomeObj>>(encObjects)  
for(someObj in decObjects) println(someObj)
```
```output
SomeObj(id=SomeObjId(id=1), title=title1)
SomeObj(id=SomeObjId(id=2), title=title2)
```

## Java Serialization ✅

- https://www.baeldung.com/java-serialization
- https://habr.com/ru/articles/431524/

#### Init

```kotlin
import java.io.Serializable
data class SomeObjId (val id: Int): Serializable {
	companion object {
		@JvmStatic private val serialVersionUID: Long = 1
	}
}
data class SomeObj (
	val id: SomeObjId,
	val title: String
): Serializable {
	companion object {
		@JvmStatic private val serialVersionUID: Long = 1
	}
}
```

The JVM associates a version (long) number with each serializable class. We use it to verify that the saved and loaded objects have the same attributes, and thus are compatible on serialization. If a serializable class doesn’t declare a `serialVersionUID`, the JVM will generate one automatically at run-time. However, it’s highly recommended that each class declares its `serialVersionUID`, as the generated one is compiler dependent and thus may result in unexpected `InvalidClassExceptions`.

#### Serialize

```kotlin
import java.io.ByteArrayOutputStream  
import java.io.ObjectOutputStream

val encObjects: ByteArray = ByteArrayOutputStream().apply {  
    ObjectOutputStream(this).writeObject(someObjects)  
}.toByteArray()  
println(encObjects
	.map { "%02X".format(it) }
	.chunked(16)
	.joinToString("\n") { it.joinToString (" ") }
)
```
```output
AC ED 00 05 75 72 00 0A 5B 4C 53 6F 6D 65 4F 62
6A 3B 77 DB 9D 38 29 45 3B B6 02 00 00 78 70 00
00 00 02 73 72 00 07 53 6F 6D 65 4F 62 6A 00 00
00 00 00 00 00 01 02 00 02 4C 00 02 69 64 74 00
0B 4C 53 6F 6D 65 4F 62 6A 49 64 3B 4C 00 05 74
69 74 6C 65 74 00 12 4C 6A 61 76 61 2F 6C 61 6E
67 2F 53 74 72 69 6E 67 3B 78 70 73 72 00 09 53
6F 6D 65 4F 62 6A 49 64 00 00 00 00 00 00 00 01
02 00 01 49 00 02 69 64 78 70 00 00 00 01 74 00
06 74 69 74 6C 65 31 73 71 00 7E 00 02 73 71 00
7E 00 06 00 00 00 02 74 00 06 74 69 74 6C 65 32
```

#### Deserialize

```kotlin
import java.io.ByteArrayInputStream  
import java.io.ObjectInputStream  

val decObjects = ObjectInputStream(ByteArrayInputStream(encObjects)).readObject() as Array<SomeObj>  
for(someObj in decObjects) println(someObj)
```
```output
SomeObj(id=SomeObjId(id=1), title=title1)
SomeObj(id=SomeObjId(id=2), title=title2)
```



## Protobuf

### Kotlin ProtoBuf Serialization ✅

- https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/formats.md
- https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-protobuf/kotlinx.serialization.protobuf/-proto-buf/

[Protocol Buffers](https://protobuf.dev/) is a language-neutral binary format that normally relies on a separate `.proto` file that defines the protocol schema. It is more compact than `CBOR`, because it assigns integer numbers to fields instead of names.

Kotlin Serialization is using `proto2` semantics, where all fields are explicitly required or optional. For a basic example we change our example to use the [ProtoBuf](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-protobuf/kotlinx.serialization.protobuf/-proto-buf/index.html) class with [ProtoBuf.encodeToByteArray](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-protobuf/kotlinx.serialization.protobuf/-proto-buf/encode-to-byte-array.html) and [ProtoBuf.decodeFromByteArray](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-protobuf/kotlinx.serialization.protobuf/-proto-buf/decode-from-byte-array.html) functions.

#### Setup

`build.gradle.kts`
```kotlin
plugins {
    kotlin("plugin.serialization") version "1.9.24"
}
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.6.0")
}
```

#### Init

Minimal:

```kotlin
import kotlinx.serialization.Serializable

@Serializable  
data class SomeObjId (val id: Int)  
@Serializable  
data class SomeObj (  
    val id: SomeObjId,  
    val title: String  
)
```

By default, field numbers in the Kotlin Serialization ProtoBuf implementation are automatically assigned, which does not provide the ability to define a stable data schema that evolves over time. That is normally achieved by writing a separate ".proto" file. However, with Kotlin Serialization we can get this ability without a separate schema file, instead using the [ProtoNumber](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-protobuf/kotlinx.serialization.protobuf/-proto-number/index.html) annotation.

Protocol buffers support various integer encodings optimized for different ranges of integers. They are specified using the [ProtoType](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-protobuf/kotlinx.serialization.protobuf/-proto-type/index.html) annotation and the [ProtoIntegerType](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-protobuf/kotlinx.serialization.protobuf/-proto-integer-type/index.html) enum.

```kotlin
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoBuf  
import kotlinx.serialization.protobuf.ProtoIntegerType
import kotlinx.serialization.protobuf.ProtoNumber  
import kotlinx.serialization.protobuf.ProtoType

@Serializable  
data class SomeObjId @OptIn(ExperimentalSerializationApi::class) constructor(  
    @ProtoType(ProtoIntegerType.DEFAULT)  
    @ProtoNumber(1)  
    val id: Int  
)  
@Serializable  
data class SomeObj @OptIn(ExperimentalSerializationApi::class) constructor(  
    @ProtoNumber(1)  
    val id: SomeObjId,  
    @ProtoNumber(2)  
    val title: String  
)
```

#### Serialize

```kotlin
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToByteArray  
import kotlinx.serialization.protobuf.ProtoBuf

@OptIn(ExperimentalSerializationApi::class)

val encObjects: ByteArray = ProtoBuf.encodeToByteArray(someObjects)  
println(encObjects
	.map { "%02X".format(it) }
	.chunked(16)
	.joinToString("\n") { it.joinToString (" ") }
)
```
```output
02 0C 0A 02 08 01 12 06 74 69 74 6C 65 31 0C 0A
02 08 02 12 06 74 69 74 6C 65 32
```

#### Deserialize

```kotlin
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf

@OptIn(ExperimentalSerializationApi::class)

val decObjects = ProtoBuf.decodeFromByteArray<Array<SomeObj>>(encObjects)  
for(someObj in decObjects) println(someObj)
```
```output
SomeObj(id=SomeObjId(id=1), title=title1)
SomeObj(id=SomeObjId(id=2), title=title2)
```

#### ProtoBuf schema generator

```kotlin
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.schema.ProtoBufSchemaGenerator

@OptIn(ExperimentalSerializationApi::class)

val descriptors = listOf(SomeObj.serializer().descriptor)  
val schemas: String = ProtoBufSchemaGenerator.generateSchemaText(descriptors)  
println(schemas)
```
```output
syntax = "proto2";


message SomeObj {
  required SomeObjId id = 1;
  required string title = 2;
}

message SomeObjId {
  required int32 id = 1;
}
```

### ProtoBuf ❌ COMPILATION ERRORS ❌
#### Setup

<u>protobuf</u> lite for generating Java classes from proto:
- https://github.com/google/protobuf-gradle-plugin
- https://stackoverflow.com/questions/72210395/how-to-setup-protobuf-in-kotlin-android-studio
- https://protobuf.dev/getting-started/kotlintutorial/
- https://github.com/protocolbuffers/protobuf/releases

`build.gradle.kts`
```kotlin
import com.google.protobuf.gradle.id
plugins {
    id("com.google.protobuf") version "0.9.4"
}
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.19.4"
    }
    generateProtoTasks {
        all().configureEach {
            builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}
dependencies {
    implementation("com.google.protobuf:protobuf-javalite:3.21.7")
}
```

❌ COMPILATION ERRORS ❌

another:

```kotlin
import com.google.protobuf.gradle.id
plugins {
    id("com.google.protobuf") version "0.9.4"
}
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.19.4"
    }
    generateProtoTasks {
        all().configureEach {
            builtins {
                id("kotlin") {
                    option("lite")
                }
            }
        }
    }
}
dependencies {
    implementation("com.google.protobuf:protobuf-javalite:3.21.7")
}
```

#### Define a schema

Protobuf requires a predefined schema in a proto file in the `app/src/main/proto/` directory. This schema defines the type for the objects that you persist in your Proto DataStore. To learn more about defining a proto schema, see the [protobuf language guide](https://developers.google.com/protocol-buffers/docs/proto3).

`src/main/proto/SomeObj.proto`
```proto3
syntax = "proto3";  
  
message SomeObj {  
  SomeObjId id = 1;  
  string title = 2;  
}  
  
message SomeObjId {  
  int32 id = 1;  
}
```

❌ COMPILATION ERRORS ❌

#### Generating Java and Kotlin classes manually

https://protobuf.dev/getting-started/kotlintutorial/

**Install Protocol Buffers Compiler (protoc)**:

1. Download the Protocol Buffers compiler appropriate for your operating system from the official GitHub repository: [https://github.com/protocolbuffers/protobuf/releases](https://github.com/protocolbuffers/protobuf/releases)
2. Extract the downloaded file to a location on your system.
3. Add the `bin` directory of the extracted folder to your system's PATH environment variable.

**Generate Kotlin code from `.proto` files**:

1. Open the `.proto` file you created.
2. IntelliJ IDEA should recognize the Protobuf syntax and prompt you to compile it.
3. If it doesn't, you can manually compile the `.proto` file using `protoc` in the terminal:

```cmd
cd D:\Programing\Kotlin\workspace\Sandbox
protoc --java_out=src\main\java --kotlin_out=src\main\kotlin src\main\proto\SomeObj.proto
```

This will generate `SomeObjOuterClass.java` in `java` directory and `SomeObjIdKt.kt`, `SomeObjKt.kt`, `SomeObjOuterClassKt.kt` in `kotlin` directory. ❌ COMPILATION ERRORS ❌

#### Protobuf plugin for IntelliJ IDEA

**Install the Protobuf plugin for IntelliJ IDEA**:

1. Open IntelliJ IDEA.
2. Go to `File` > `Settings` (or `IntelliJ IDEA` > `Preferences` on macOS).
3. In the Settings window, navigate to `Plugins`.
4. Click on `Marketplace` tab and search for "Protocol Buffers".
5. Install the "Protocol Buffers" plugin by JetBrains.

![IDEA_protobuf_plugin](img/2024-05-10_03-45-44.png)

#### Create a Proto Serializer

Define a class that implements `Serializer<T>`, where `T` is the type defined in the proto file. This serializer class tells how to read and write your data type.

```kotlin
object SettingsSerializer : Serializer<SomeObj> {
	override val defaultValue: SomeObj = SomeObj.getDefaultInstance()
	override suspend fun readFrom(input: InputStream): SomeObj {
	    try {
		    return SomeObj.parseFrom(input)
	    } catch (exception: InvalidProtocolBufferException) {
		    throw CorruptionException("Cannot read proto.", exception)
	    }
	}
	override suspend fun writeTo (t: SomeObj, output: OutputStream) = t.writeTo(output)
}
```

#### Init

```kotlin

```

#### Read

```kotlin

```

#### Write

```kotlin

```



