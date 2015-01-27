~ number: 2
~ title: Unit Testing with JUnit and IntLists

Navigation
----------------

- [Pre-lab](#pre-lab)
- [Introduction](#introduction)
- [An Arithmetic Bug](#arithmetic)
- [Testing Triangles](#triangles)
- [Application: IntLists](#intlists)
- [Recap](#recap)

<a name="pre-lab"></a> Pre-lab
-------------------------------

None this week, but make sure that you finish Lab 1: Intro to git!

<a name="introduction"></a> Introduction
--------------------------------
In this lab, you will learn about Unit Testing, JUnit, Destructive vs NonDestructive methods, and IntLists.

Your job for this assignment is to fix the bug in `Arithmetic.java`, create unit tests for `Triangle.java`, and to create and test methods for `IntList.java`.

####What is JUnit?
Unit Testing is the best way to rigorously test each method of your code and ultimately ensure that you have a working project.
The “Unit” part of Unit Testing comes from the idea that you can break your program down into units, or the smallest testable part of an application.
Therefore, Unit Testing enforces good code structure (each method should only do “One Thing”), and allows you to consider all of the edge cases for each method and test for them individually.
And when JUnit tests fail, you have an excellent starting point for debugging.

####JUnit Syntax
JUnit provides some special functionality on top of what you can normally do in java.

Ultimately, JUnit provides a testing framework, so you can test your code without stressing about the implementation details that actually go into testing your code.

So what is different about a JUnit java file?  Go ahead and navigate to the Arithmetic directory and open `ArithmeticTest.java`. The first thing you'll notice are the imports at the top.  These imports are what give you access to the JUnit methods and functionality that you'll need to run JUnit tests.

Next, you'll see that there are two methods in `ArithmeticTest.java`: testProduct() and testSum()
These methods follow this format:

	  @Test
	  public void testMethod() {
	    assertEquals(<expected>, <actual>);
	  }

assertEquals is by far the most common method.  It tests whether an actual value is equal to the expected value or not (failure).

Basically, each method that is a test will be preceded by a @Test annotation, and can have one or more assertEquals or assertTrue method (provided by the JUnit)
The main method in a JUnit testing file will actually run all of the tests.  You don't need to memorize this syntax, just know that what it does is run all of the methods which are preceded by @Test.
System.exit(ucb.junit.textui.runClasses(SomeTest.class));

In the case of `ArithmeticTest.java`, the name of the .class file is ArithmeticTest.

  /* Run the unit tests in this file. */
  public static void main(String... args) {
    System.exit(ucb.junit.textui.runClasses(ArithmeticTest.class));
  }

In order to execute the tests, in the command line run:
`java ArithmeticTest`
Since I have provided a Makefile, you can also instead run:
`make check`
This will run all of the tests and tell you if they passed/failed.

<a name="arithmetic"></a>  Arithmetic
--------------------------------
After running your tests, you should get a JUnit report.
Notice it includes a failure!  This tells you which test failed (testSum in ArithmeticTest), what the expected and actual values were,
and on what line the failure occured.

  java ArithmeticTest
  Time: 0.005
  There were 1 failures:

  1) testSum(ArithmeticTest)
      expected:<11> but was:<30>
      at ArithmeticTest.testSum:25 (ArithmeticTest.java)

  Ran 2 tests. 1 failed.
  make: *** [check] Error 1


Notice that the other test ran without any failures.  Now, you should go investigate why testSum failed.  Look at testSum to understand what its testing for,
and then make the appropriate change in Arithmetic.
Execute the following:

`make`

`make check`

This recompiles your code, and re-runs the tests.
If you've fixed the bug, it should look like this:

  java ArithmeticTest
  Time: 0.005
  Ran 2 tests. All passed.

One thing to notice here is that even though testSum included many asserts, you only saw the first failure (even though you know that all of the asserts would have failed!)
This is because JUnit tests are short-circuiting- as soon as one of the asserts in a method fails, it will output the failure and move on to the next test.

<a name="triangles"></a> Testing Triangles
--------------------------------

Now move on to the Triangles directory.
Now we have a class, `Triangle.java`, which has a constructor that takes in 3 ints, and constructs a Triangle with the inputs as sides.
We have a method, triangleType, which will return one of the following:

-"At least one length is less than 0!" if any of the sides is negative

-"The lengths of the triangles do not form a valid triangle!" if for any side a, b, and c: (a + b) <= c

-"Equilateral" if all the sides are the same length

-"Isosceles" if any two sides are the same length (and wasn't already determined to be an "Equilateral" type)

-"Scalene" otherwise.


We've already handled the edge cases for you (Negative sides and invalid triangles)
It is your job to determine whether the triangle is equilateral, isosceles, or scalene.

HINT:  Look into Java's if/ else if/ else

HINT:  or is `||` in java, and is `&&` in java

Now that you are fairly certain you are correctly handling all of the different cases for triangleType, let's move on to testing it!
Open TriangleTest.java, and observe the testScalene method.  Now, fill in testEquilateral, and additionally create tests for Isosceles, Negative Sides, and Invalid sides.
Make sure to fill in the main method so that it runs all of the TriangleTest classes' tests!

Now that you've filled out the the tests, try running them:

First, run `make` in the Triangles directory.  Pay attention to any errors, and fix them before running your tests.

Make sure `make check` passes before moving on.

<a name="intlists"></a> Application: IntLists
--------------------------------

###Introduction to IntLists

Now a real-CS61B application of JUnit tests: IntLists.

If you remember Rlists or Recursive Lists in CS61A, or the Linked List Data Structure in general: that's what an IntList is!
An IntList is a recursive list of ints.
If you look at IntList.java in the IntList directory, you can briefly look over the methods for a deep understanding of our implementation.
But this all you really need to know:
An IntList has a .head and .tail property.  The .head is the first element of the list (an int).  The .tail is the remaining elements of the list (another IntList!)
There is also a convenient constructor defined for you:

`IntList myList = IntList.list(0, 1, 2, 3);`

Which will create the IntList 0 -> 1 -> 2 -> 3 -> null

`myList.head`  returns 0

`myList.tail` returns 1 -> 2 -> 3 -> null

`myList.tail.tail.tail` returns 3 -> null

`myList.tail.tail.tail.tail` returns null


###Destructive vs. Nondestructive

A common way to iterate over an IntList

  public void dSquareList(IntList L) {

    while (L != null) {
      L.head = L.head * L.head;
      L = L.tail;
    }
  }

At first glance, it seems that this method doesn't do anything!  It returns void- nothing.  It does iterate and square each element in L,
but it doesn't return L at the end.  BUT this is actually a classic example of a destructive method- it modifies L.  In fact, after calling this method
once on L, every element in L will be squared

NOTE:  its still considered best practice to return L, and use the returned value rather than relying on this behavior and returning void.

The best way to fully understand this is to have a healthy grasp of how pointers work in java.

We want to look at the inside of the dSquareList method, but we also need to understand what is going on one level higher- when we create the IntList that we pass into dSquareList!

  IntList origL = Intlist.list(1, 2, 3)
  dSquareList(origL);
  //origL is now (1, 4, 9)

We have an origL pointer which points to the created IntList.  Now, inside of dSquareList, we have the parameter L.  L is the pointer that is moved around inside the method.

(Recall `L = L.tail`)

As we iterate through the method, origL always points the the beginning of the IntList, but L moves to the right until it reaches the null value at the end.

The reason that dSquareList is destructive is because we change the values of the **underlying IntList**.  As we go along, we square each value.

By the end of the method L is null, and origL is still pointing at the beginning of the IntList **but** every value in that IntList is now squared.

origL, and methodL both point to:
0 -> 1 -> 2 -> 3 -> null

Throughout the method, origL does not move.  But methodL points at 0 -> 1 -> 2 -> 3 -> null, then 1 ->2 -> 3 -> null, and so on until the end
of the method when its pointing to null.

Since the method is squaring the underlying IntList, at the end:

-methodL is pointing to null

-origL is pointing at 0 -> 1 -> 4 -> 9 -> null

If this is still confusing, ask a TA to draw a diagram of the code execution.  Pointers and IntLists might seem confusing at first, but its important that you understand these concepts!


Now, look at at squareListIterative and squareListRecursive.  These methods are both non-destructive.  That is, the underlying IntList passed into the methods do **not**  get modified.
The iterative version is usually quite a bit messier than the recursive version, since it takes some careful pointer action to create a new IntList, build it up, and return it.  Try to understand what this code is doing, but don't stress if it doesn't all make sense right away.
Then look at the recursive version- try to reason why this is non-destructive.  It can help to write out the the linked list diagram if you get confused.

Finally, look at the test method `testDSquareList` in `IntListTest.java`.  Notice that this test checks whether or not dSquareList is destructive.

Create a test for squareListRecursive that checks that it is **not** destructive.  You will probably also want to test whether or not squareListRecursive is correct.

### Implementing Destructive vs NonDestructive Methods

Finally, lets get our hands dirty by writing our own methods:  dcatenate and catenate.

Both methods take in two IntLists, and concatenates them together.  So catenate(IntList A, IntList B) and dcatenate(IntList A, IntList B) both result in an IntList which contains the elements of A followed by the elements of B.
The only difference between these two methods is that dcatenate modifies the original IntList A (so its destructive) and catenate does not.

IntList problems can be tricky to think about, and there are always several approaches which can work.  Don't be afraid to pull out pen and paper or a whiteboard and work out some examples!
It's also often useful to think about base cases (when A is null, for example)- this works well for building up a recursive solution especially.

Finally, you'll want to test your methods.  Create a testDCatenate and testCatenate methods, which test for both correctness and whether or not the methods are destructive.

<a name="recap"</a> Recap
-------------------------------
In this lab, we went over:

-Unit testing in general

-JUnit syntax and details

-Writing JUnit tests

-Non-destructive vs Destructive methods

-IntLists and pointers

-Writing IntList methods destrutively/nondestructively/recursively/iteratively

-Using JUnit to test for correctness and destructiveness


