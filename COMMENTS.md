Comments
========

11/Sep/2017
-----------
- fix red test for DocxMedTester.
  - I feel silly...


10/Sep/2017
----------
### Package Organisation
Was talking to someone today about the package organisation, and how I think
the way I have organised them is generating a lot of code smell -- the mediator
package seems to be importing from a lot of them.  I think I've added a todo to
add a branch and refactor it all as such.

### Is it time for a rewrite?
Been thinking whether I should apply 'courage' and ditch the whole thing, and
start it again from scratch. But this time, try to use diagrams to model the
problem and solution domain before I start coding. Added a TODO for that, but
may never get to it.


09/Sep/2017
----------
### Complex Objects
Other refactoring points are done, or I changed my mind about them. One that
seems to still be baffling me though is the DocxMediator, which I am unable to
test due to it using too complex object, which call on final methods and cannot
be stubbed, for example. Test the base entities instead, and think of
DocxMediator as a main method, rather than a component. Something to think
about, but for now the TODO is to test all the base units.
