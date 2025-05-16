# Revision Tool
*A command-line application that asks questions and checks your answers. This is an IT assignment for my secondary school.*

### Usage

Initially, you will be asked to choose between a ['loose marking mode' and a 'strict marking mode'](#marking-modes).

The revision tool then displays a question. Type your answer and press Enter to submit it.

The revision tool will display if your answer is correct; if not, it also displays the correct answer. It repeats this process for every question in your [questions.txt](#editing) file. It will also display questions that you got wrong again, until you answer it correctly.

### Marking modes

In strict marking mode, your answer is checked against the correct answer. If they do not match (even if you are only missing e.g. punctuation), your answer is incorrect.

In loose marking mode, your answer is checked against a list of keywords. If your answer contains all of the keywords, your answer is correct.

Some questions will only have strict marking mode (e.g. those with short answers). However, no questions will only have loose marking mode.

### Editing

Go to a file called "questions.txt". You will find several default questions there.
To add a new question, start a new line. Each line should contain only 1 question, and then a | (pipe symbol), and then the model answer.

If you wish to allow loose marking mode for this question, type another |, followed by a keyword, and then another |, another keyword, and so on. You may have as many keywords as you want in a question (but make sure that the keywords are absolutely necessary in your answer, i.e. not something like 'is', 'the', etc.)

The validity of the question is checked during initialization. A question is considered invalid if there is no question, there is no answer, or any one of the keywords are blank.
