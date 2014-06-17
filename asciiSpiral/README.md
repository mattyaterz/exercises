Ascii Spiral
============

Problem
-------

Take an integer and print the integers from 0 to that input integer in a spiral format.
For example, if supplied with 24, the output would be:

```
20 21 22 23 24 
19  6  7  8  9
18  5  0  1 10
17  4  3  2 11
16 15 14 13 12
```

Build and Run
-------------
Assuming you have Java 7 installed, all you should have to do is

`./gradlew clean build`

And then simply

`asciiSpiral$ java -jar ./build/libs/asciiSpiral-0.1-SNAPSHOT.jar ${spiralLength}`

