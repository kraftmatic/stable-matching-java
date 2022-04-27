# stable-matching-java
Small application that generates prefrence matricies for men/women and assigns partners in a demo of the stable matching problem/algorithm

# usage instructions
The only variables that need to be set are the int at the top of the class, numberOfOneSex, and the boolean passed into the stableMarriage method which will 
enable/disable the printing of the solution.  The solution can be disabled if only the benchmark of how long the process takes is wanted.

For numberOfOneSex, keep in mind that this number will be doubled for the size of the matrix.  If numberOfOneSex is 10, then there will be 20 rows generated to
store the preferences of both 10 men and 10 women.
