Paper Round
-----------

Please implement code to deliver the user stories below as described by their acceptance criteria (ACs).
You should not need to spend more than two or three hours on the assignment. You should aim to deliver a working solution that is straightforward, but also maintainable and bug free. The intention is that it should be representative of what you would produce 'on the job'.
You may find ambiguities in the requirements that in the 'real world' you would clarify with a business analyst. For the purpose of this test, please highlight any assumptions that you have had to make in developing your solution.
A zipped project skeleton using maven is provided for convenience. You should feel free to use any other tools you are more comfortable with. It is not essential to use the skeleton.

Ammendments
-----------
Changed to build tool to gradle -> please use ./gradlew clean check build
Used Java 7 for the implenmentation with Guava and Groovy / Spock for TDD. 

User Stories
============


Story 1
-------

A town planner is responsible for keeping track of the layout of a street and numbering houses. The layout is stored in a street specification file. An example is given in street1.txt

The street specification file can be interpreted as follows:
	- The street specification file lists all the house numbers in the street from west to east.
	- House numbering starts from 1
	- All the houses on the left-hand (north) side of the street are odd numbered
	- All the houses on the right-hand (south) side of the street are even numbered
	- For example, a street specification file containing "1 3 2" can be interpreted as there being, from west to east:
			house #1 on the left-hand (north) side of the street, followed by house #3, also on the left-hand side of the street, followed by house #2 on the right-hand (south) side of the street.
			(Note: in the real world houses on a street are frequently directly opposite each other. For this purposes of this exercise, this is assumed never to be the case!)
	- On each side of the street no number can be skipped and no number can be used more than once

Acceptance criteria: As a town planner, given an abitrary street specification file, I want:

AC1. to be confident that the file is valid.
AC2. to know how many houses there are in a given street
AC3. to know how many houses are on the left hand (north) side of the street
AC4. to know how many houses are on the right hand (south) side of the street

AS1. Street specifications always run from west to east (left to right)
AS2. That house number one is always on the north side of the street
AS3. That house number one is always the first house at the west end of the street
AS4. House number increase in value on each side of the street (ie 1 3 5, 2 4 6 not 4 2 6 or 1 5 3 etc.)
AS5. The street specification is all on the first and single line in the file
AS5. Invalid files will result in run time exceptions with an appropriate message


Story 2
-------

A paper boy or girl delivers newspapers to all the houses in a given street. At the moment they are experimenting with two approaches to delivering papers:
(1) In one approach they first travel from west to east delivering to the houses on the left hand (north) side of the street. They then travel east to west delivering to houses on the south side of the street.
(2) In the second approach they deliver to each house in turn from west to east, regardless of which side of the road they are on.
They may want to try different approaches in future.

Acceptance criteria:  As a newspaper boy or girl given a street, I want to know for each of the approaches (1) and (2):

AC5. the list of house numbers in the order that I will be delivering to, so that I can sort by satchel in advance
AC6. how many times I will have to cross the road from one side to the other to make my deliveries

AS1. The paper boy / girl always starts at house number one which is on the north side and is the first house at the west end of the street
