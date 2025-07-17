# Collatz Tree Mapper

WORK IN PROGRESS

The famous Collatz function is *f*(*n*) = 3*n* + 1 if *n* is odd, *n*/2 if *n* 
is even. Iterating this function from any positive *n* will always eventually 
lead to 1. At least that's the conjecture, which has been verified for millions 
of values of *n*, but has so far eluded mathematical proof. 

I don't think I can prove or disprove the conjecture. But I think I can build a 
tool in Java to visualize the conjecture in the form of number trees. Better 
yet, I should use Scala for this purpose. In Scala, it's very easy to pass 
functions around as parameters, so it makes perfect sense to use Scala for this 
program.

Following iterations of the Collatz function is easy. Where it gets tricky is 
that any given number can have either one or two precursors, or no precursors 
at all. 

To make this manageable, I've decided to limit the calculations to 32-bit 
integers. Even so, trying to scan everything in advance could be quite 
time-consuming. [FINISH WRITING] 

I started this project in Scala 2 with Java 8. I forgot about this project for 
almost four years. In the interim, I upgraded to Java 21, and found that the 
Scala 2 REPL could no longer function. So I had to upgrade to Scala 3.

But as I intend to resume work in IntelliJ IDEA, which by default retains the 
original JDK and SDK choices even if the general system is upgraded, I'll 
probably continue with Scala 2 and Java 8 when working directly on the source 
and tests.

In addition to the mathematical interest, this project also offers a few 
opportunities to illustrate higher order functions in Scala.
