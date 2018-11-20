# project2CSC312
### Lam and Sam aka LAMUELSOFT

## Requirements
1. Start a contest
  * The user will start a contest with a url http://localhost:8080/newcontest. 
  * The response will return a random integer between 1-1000
  * This number will be used as the contestid. Do not use a contestid that is already in use.
  * For each subsequent request to the server, the contestid must be specified on the url.
  * Each contest will have a 120 second timer, once 0 is reached, subsequent requests will return status code HttpServletResponse.SC_GONE. 

2. Accessing a letter
To access a letter at a specific location:
(http://localhost:8080//wordfinder?contest=<contest id received as response to newcontest>&game=<1 to 3>&pos=<column><row>)
contest:the contest id received in when starting a contest (see step 1).
game:1-3
Column: a-e
Row: 1-5
If the user submits a request, with an invalid contest id, game or pos, you must responde with the status code: response.SC_BAD_REQUEST.

3. Submitting a solution
to submit a solution to the contest, a specific url is used:
http://localhost:8080//solution?contest=<contest id received as response to newcontest>&game=<1 to 3>&solution=<word>
solution: the word which is the solution
If a submission is valid we must:
return SC_OK and in the html text, the number of seconds it took to resolve the problem and how many letters were requested.
If a submission is invalid for a game:
the contest id must be considered invalid, and further request using this contestid should return response.SC_BAD_REQUEST.

For a valid submission, keep a list of the 5 fastest (in term of request of letters) contest.

The solutions must be in this form:
Game 1:  position A1:A3, solution: zap
Game 2:  position E3:E5, solution: zig
Game 3:  position  C2:C4, solution: zag

4. Top Score
The url : https:// localhost:8080/topscore, will return in descending order the list of the 5 fastest times.

Output should be in this form:
contest id, time in seconds

5. Word list
the url https:// localhost:8080/words, will return this list of words:
zap
zep
zip
zag
zig

6. Unit tests:
you must provide individual unit tests for each item that you must test
A unit test must be created for:
-validation that the word list is correct
-validation of starting a new contest
-validation of requesting for a letter (valid and invalid values for each parameter and combination).
-validation of the timeout process for a contest (see https://docs.oracle.com/javase/tutorial/essential/concurrency/sleep.html)
-validation of submitting a solution (valid and invalid submission + management of the contest)
-validation of the top score

