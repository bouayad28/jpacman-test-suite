# Software Quality & Testing Report

## Assignment 0

### Question 3

The reason why we can not exhaustively test our entire software project is because even if it does not include many branches, inputs and outputs, it would be realistically impossible to test all possible combinations of input values. Instead we should for example prioritise the combinations of values that are most common or that are most likely to cause faults.

### Question 4

The pesticide paradox says that like a pesticide, testing only gets rid of those bugs that the test cases cover. The consequence of this paradox is that once you have run the same tests over and over again, no new information can be derived as no new bugs will be found. In addition, more subtle bugs will not be detected by these tests. This implies for software testers that we have to constantly write new test cases as we cannot depend on old test cases made a long time ago to find new bugs.

### Question 5

We should automate the test execution process as much as possible because this is the part of the testing process that depends least on human input, is prone to human error, is rather monotonous and thus lends itself best to automation.

## Assignment 1

### Question 6

`DefaultPlayerInteractionMap` and `CollisionInteractionMap`, the code in these classes is not called directly or indirectly by the smoke test. This is because the `DefaultPlayerInteractionMap` is a class meant to enable future extensions to the collision system, but is currently not used. The `CollisionInteractionMap` is in turn only used by this class, therefore it is unused as well.

### Question 7

Yes, it is covered. When commenting out the line that moves the player, the player does not consume the pellet on the square it has moved to, therefore not getting ten points for the pellet. This can be used as an indicator of that fact that indirectly obtaining points by moving on the board functions correctly. It however is of limited use as it does not make clear in what part the defect is present, it covers multiple methods from multiple classes.

### Question 8

This change causes the neighbour maps of the squares to receive the incorrect squares. Specifically for `Direction.EAST`, this results in the east neighbour of a square to be the exact same square. Moving the player to what should be the east will result in it staying on the same square. This in turn causes the player to not get any points, for the reasons established in question 7. Again it was not trivial to establish all this, because the behaviour spans many methods and classes. In conclusion, end-to-end testing such as this is only useful for establishing the presence of faults, not where or under what exact circumstances. More in-depth testing such a unit testing is needed for that.

### Question 9

`Game` together with its concrete implementation `SinglePlayerGame` appears to function as a sort of container that holds most of the other classes either directly or indirectly. It coordinates the communication between these classes and handles the lifecycle of the game - setting it up and tearing it down. `Level` is concerned with the board, the player and the NPCs. It handles collisions between these and can notify other classes that the game has ended. `Board` contains the grid of `Square`s and its dimensions, and enables other classes to obtain squares and check the validity of coordinates. `Unit` is a generic representation of moving characters on the board. It enables the movement of characters across the board.

### Question 12

![Board boundary check domain matrix](images/domain_matrix.png)

### Question 14

To avoid code repetition, one can make create a setup method and put into it classes and objects that are required by each test. Then, one can annotate it with either the `@BeforeEach` or the `@BeforeAll` annotations. This ensures that, as the name suggests, before each test instance or once before all test instances, the setup method is run and thus all the required instantiations and setup work is carried out.

### Question 15

The advantages of such an approach is that any changes that happen to a class in one test is not carried over to the next test, thereby preventing unwanted spillover effects that could influence an otherwise accurate test. 
 
### Question 16
 
`assertEquals(1, a)` is better because in case of failure, it would provide the user with more information as to what went wrong, for instance one could learn that 1 was expected but that 3 was actually provided. In the case that `assertTrue(1 == a)` is false, one only learns that this is not the case but there is not more information provided as to why this assert statement is wrong. In addition, assertEquals will call the equals() method whereas `1 == a` does not, therefore depending on the behavior of the `equals()` method.
  
### Question 17
 
A reason not to test private methods is that they are not part of the public interface of the unit (class) that is being tested. Any mistakes in private methods that are used in a certain way by methods that are part of the public interface can be caught by tests for those methods. Because these private methods are always used by other methods, it does not make sense to test them in isolation.
 
### Question 18
 
 Based on our newly created classes, the adequacy of JPACMAN has increased as through our tests it was established that the nextAiMove method of the Clyde and Inky classes and the withinBorders method of the Board class behave as expected. As we only committed code after it was checked by the Checkstyle, PMD and Spotbugs plugin, we never run into troubles with the continuous integration server testing. Finally, we committed and pushed onto a branch for every section of the assignment i.e. once for boundary testing. In addition, we varied who carried out the git interactions.
