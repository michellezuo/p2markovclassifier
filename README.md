# Project 2: Classifying Markov Models, Fall 2026

This is the high-level project document for Project P2-Markov-Classifying in CompSci 201 at Duke University, Fall 2026.

For complete project details including git, programming tasks, and analysis questions, see [the _details_ document](docs/details.md). This `README` document supplies a high-level overview of what the project is about, including early engagement points.
 
## Introduction

In Project P1: Markov you were asked to implement two different Markov Model classes
to _generate_ text probabilistically after training on different authors. Those models,
`SimpleMarkovModel` and `HashMarkovModel` are *generative AI Markov Models*. 

In this project you'll implement a new class `ClassifyingModel`, which also extends `BaseMarkovModel`, but which shares is
implementation ideas as a variant of `HashMarkovModel`. 

This new `ClassifyingModel` is used
to *classify* unknown texts after training on many different authors. You'll train several models on the
works of say `N` authors, each model representing a Markov Model for one of the authors, say
M<sub>1</sub>, M<sub>2</sub>, ..., M<sub>N</sub>. Then, for a set of unknown files, 
say U<sub>1</sub>, U<sub>2</sub>,..., U<sub>k</sub>, you'll find the maximum likelyhood estimate (MLE)
for each of the `k` unknown files that it's authored by each of the `N` authors represented by a trained model. The largest of these MLE values will "predict" or "classify" the author of the unknown text as the most likely generator of the unknown text.


## General Work for this project

Your goal is to finish implementing the class `ClassifyingModel` and use it to train many different models using a provided driver program. The driver uses these models to classify texts whose authorship is "unknown" in the sense that these texts weren't part of the training process.

Summary:

  - Fork/Clone the project
- Complete the implementation of `ClassifyingModel` with and without memoizing. Explained fully in [details document](docs/details.md).
- Test your implementation with the JUnit tests in `TestClassifyingModel`.
- Veryify results for each unknown text compared to correct results in the [details document](docs/details.md).
- Run the class `Classifier201` as described to predict the best (maximal MLE) for unknown texts to answer the analysis questions. There's more work for analysis questions than in previous assignments.

Details about `ClassifyingModel` can be found 
in the [details document](docs/details.md) with complete details on finishing the project.

## Reading/Running code at Start (optional, Engagement points)

### Similar code in AuthorShip.java

We discussed at a very high level the code in [AuthorShip.java](src/AuthorShip.java) as part of class. That code
trains a single order-zero Markov Model on the works of one author. Then it
tries to identify which one of `k` unknown files U<sub>1</sub>, U<sub>2</sub>,..., U<sub>k</sub> is most
likely authored by the single author used to train the model.

In this assignment you'll create `N` Markov Models and then
identify which of each of the `k` unknown files is most likely written by each of the `N` authors. However,
you'll do this using an order-1 or order-2 Markov Model rather than the order zero model in [Authorship.java](src/AuthorShip.java).

For up to 14 engagement points read that code (and for more points run it) answering the questions in [https://forms.cloud.microsoft/r/n5k9ztkCzF](https://forms.cloud.microsoft/r/n5k9ztkCzF).


## Submitting and Grading

The autograder will use the same tests that are in `TestClassifyingModel.java`. The autograder will *NOT* test for speed based on memoizing, but your MLE results should be the same regardless of whether memoizing is used.



### Grading

| Section.  | points |
|-----------|--------|
|Analysis   |     2 |
|Code       |      8 |  
