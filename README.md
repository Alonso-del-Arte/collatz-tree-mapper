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
at all. [FINISH WRITING] 