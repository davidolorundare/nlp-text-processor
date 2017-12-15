
# Natural Language Processing: Text Processor
Academic Project - A Text (Pre)Processor program. 

## Overview:

This commandline program takes input from the user in the form of a plain text document, extracts some textual properties of its contents and outputs the result in a given output text file. Specifically, these extracts contents outputted include:

- Number of paragraphs.
- Number of sentences.
- Number of words (i.e. word "tokens" )
- Number of distinct words (i.e. "word types").
- A list of the word frequency counts.  Words are ordered by frequency (in the descending order), and words which have the same frequency count are ordered by lexicographical order (in ascending order).

The program makes extensive use of built-in Java language string-processing and regular expression libraries to perform text preprocessing tasks such as sentence-segmentation and other string processing functionality.

Words were tokenized such that:

- Leading and trailing punctuation marks are separated into individual tokens.  For example, "(3A):" is made into four tokens ("(", "3A", ")" and ":"), and "$3.19" is converted made into two tokens ("$" and "3.19").  

- Contractions are separated into individual tokens (without expanding to true words).  Although some contractions are ambiguous (e.g. "they'd" could be "they would" or "they had"), a number of simple rules were applied during development of the program:
	- words ending with n't -- e.g. "don't" -> "do" and "not"
	- words ending with 'll -- assume "will"; e.g. "they'll" -> "they" and "will"
	- words ending with 've -- assume "have"; e.g. "they've" -> "they" and "have"
	- words ending with 'd -- assume "would"; e.g. "they'd" -> "they" and "would"
	- words ending with 're -- assume "are"; e.g. "they're" -> "they" and "are"
	- words ending with 's -- assume "is" IF the preceding word is a personal pronoun; e.g. "it's" -> "it" and "is".  Personal pronouns (in the subject/nominative case) that apply here are "he", "she" and "it".
	- words ending with 's -- assume possessive (i.e., an apostrophe-s) if the preceding word is not a personal pronoun; e.g. "phone's" -> "phone" and "'s" special one -- I'm (or i'm) -- assume "am".

	- For any contractions encountered, only one contraction is separated. If a word contains multiple contractions, only the last/right-most one is separated.



## Screenshots:


### Program execution: the data-small.txt file on the terminal

![alt text](https://github.com/davidolorundare/image-repo/blob/master/nlp-text-processor-images/data-small-execution.png "NLP Text-processor program running data-small file on the terminal")

---

### Program execution: the data-medium.txt file on the terminal

![alt text](https://github.com/davidolorundare/image-repo/blob/master/nlp-text-processor-images/data-medium-execution.png "NLP Text-processor program running data-medium file on the terminal")

---

### Program execution: the HG-heldout-utf8.txt file on the terminal

![alt text](https://github.com/davidolorundare/image-repo/blob/master/nlp-text-processor-images/HG-heldout-utf8.png "NLP Text-processor program running large held-out utf8-encoded file on the terminal")

---


## Usage:

The build folder, in this repository, contains all the executable files needed for running the program. Download the folder to your desktop first.

Open a terminal (or commandline shell) and navigate to the build directory. i.e. '/build/'
The format for running the program is:

```>> java TextPreProcessorMain "inputFile" "outputFile"```


where ‘inputFile’ is the name of the .txt file containing the text data to be preprocessed,
and ‘outputFile’ is the name of the .txt file wherein the results of the
pre-processing should be stored

For example, while in the 'build' directory:

 This command will run the program on the large-size input file ‘HG-heldout-utf8.txt’ with the output being stored in the 'output-HG-heldout-utf8.txt' file.
 
```>> java TextPreProcessorMain "HG-heldout-utf8.txt" "output-HG-heldout-utf8.txt"```
 

This command will run the program on the small-size input file ‘data-small.txt’ with the output being stored in the 'myOutput.txt' file.
 
```>> java TextPreProcessorMain "data-small.txt" "myOutput.txt"```


This command will run the program on the medium-size input file ‘data-medium.txt’ with the output being stored in the 'myOutput.txt' file.
 
```>> java TextPreProcessorMain "data-medium.txt" "myOutput.txt"```

---

## Running Demo:

![alt text](https://media.giphy.com/media/xUNd9DQNyBNehjKIMg/giphy.gif "demo of program running in terminal")

---
