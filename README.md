# Revision Tool
*Asks questions and checks your answers.*
*This is a school project.*

### Usage

Run the tool. You will be asked to choose between a ['loose marking mode' and a 'strict marking mode'](#marking). After choosing, you should see a question pop up, and you will be allowed to type in an answer. When you are finished, press Enter.

The revision tool will then display if you got the question correct, and if you're wrong, it will display the correct answer. The revision tool will then loop to the next question, until it is finished. However, if there are wrong answers, it will ask you the same question again (as some sort of correction).

### Marking

There will be two modes: loose marking mode and strict marking mode.

In strict marking mode, your answer is simply checked against a model answer. If they do not match (even if you're just missing a fullstop!) it will regard your answer as incorrect.

In loose marking mode, your answer is checked against a list of keywords. If your answer contails all of the keywords, you are correct.

Some questions will only have strict marking mode (e.g. those with short answers). However, no questions will only have loose marking mode.

### Editing

Go to a file called "questions.txt". You will find several preset questions there.
To add a new question, start a new line. Each line should contain only 1 question, and then a | (vertical bar), and then the model answer.

If you wish to allow loose marking mode for this question, type another |, followed by a keyword, and then another |, and so on. You may have as many keywords as you want in a question (but make sure that the keywords are absolutely necessary in your answer, i.e. not something like 'is', 'the', etc.)

The validity of the question is checked during initialization. A question is considered invalid if there is no question, no answer, or any one of the keywords are blank.
