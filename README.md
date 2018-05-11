# ExampleDSL
This project demonstrates different ways to create DSL in Kotlin.

Most examples using Link object for [HAL document](https://tools.ietf.org/html/draft-kelly-json-hal-08).
The couple exceptions uses other targets to demonstrate more complicated cases.

Each package with the name starts with 'dsl' contains single example. The name of the package
should help to recognize the tecnics it demonstrates. The same package under 'test' folder
contains the unit test demonstrating the tecnics and resulting DSL.

## Naive implementation
Here the DSL code is simply a call to constructor with values provided as arguments.
Optional and labeled parameters makes this approach simple and yet flexible.

## Function-based implementation
Top-level functions allow to hide the name of the class inside and provide any logic to
calculate values right in-place. However it requires class to be mutable.

## Temporary Data class implementation
Collecting values in the intermediate mutable data class allows to keep final class immutable.

## Infix function implementation
Infix functions help to make DSL read more like English (to some extend).

## Implementation with local functions
–