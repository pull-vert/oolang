# A few code and design ideas that may or may not be implemented

# Nice Java features Oolang will or could use

## Valhalla

[Complete Valhalla explained article](https://www.jvm-weekly.com/p/project-valhalla-explained-how-a).

### [JEP 401: Value Classes and Objects](https://openjdk.org/jeps/401) (preview)
Introduce value objects, which are immutable and lack object identity. Value objects are distinguished solely by the
values of their fields, and can be represented by Java Virtual Machines in ways that improve performance. this is a
preview language and VM feature.

Goals
* Enable developers to opt in to a programming model for immutable data in which the == operator, and all other
operations, distinguish objects by the values of their fields rather than their identities.
* Support the compatible migration of existing classes that represent immutable data to this model. Migrate suitable
existing classes in the Java Platform API, such as Integer and LocalDate, to have value object instances.
* Do not ask developers to learn new semantics for memory management or variable storage. The Java language should
continue to operate on just two kinds of data: primitives and object references.
* Maximize the freedom of JVM implementors to represent immutable data in ways that improve memory footprint, locality,
and garbage collection efficiency.

### [JEP 539: Strict Field Initialization in the JVM](https://openjdk.org/jeps/539) (preview)
Introduce strictly initialized fields in the Java Virtual Machine. Such fields must be initialized before they are read,
thus default values such as `0` or `null` are never observed. For strictly initialized fields that are final, the same
value is always observed. This is a preview VM feature, available for use by compilers that emit class files.

Goals
* Offer designers of JVM-based programming languages a model for field initialization which has stronger integrity
guarantees than the present model.
* Give these designers the flexibility to choose, for each static and instance field in a class, whether to opt in to
the new model or continue with the present model.
