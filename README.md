# search-engine
A basic search engine written in Java after taking the course Information Retrieval at UvA.

When taking the course Information Retrieval at the University of Amsterdam we built a sophisticated search engine in Python,
using several frameworks. I wanted to see if I could do the same myself in Java, using no external libraries. 

At the moment the search engine reads and tokenizes a (very small, handmade) set of documents, and ranks them using tf-idf scoring. 

## Future endeavours 
Future enhancements could be to build a general Score class, which can be extended to use other measures such as BM25,
Dirichlet Prior, Jelinek-Mercer smoothing and even Learning-To-Rank at some point. The data set should also be significantly
increased, as it currently only holds a very small sample of documents used for testing.
