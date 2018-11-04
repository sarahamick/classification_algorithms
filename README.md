# classification_algorithms

Description: Implementation of the data mining classification algorithms (1) kNN and (2) ID3 decision trees (w/ 
Information Gain) on example dataset

Data: 3000 tuples, each describing a mushroom in a mix of 22 nominal and binary attributes (including: pressence of 
bruising, cap color, stalk height, etc). See Resources/agaricus-lepiotaexplanation.txt for full description. Mushrooms 
labelled edible or poisonous. 

Input: models trained on 2000 mushrooms, tested on remaining 1000 (selected randomly)

Output: whether test mushroom x is poisonous or edible

Classifier #1: K-Nearest Neighbors: Classifies a tuple by calculating the k-nearest neighbors to that tuple, and
assigning it the most common class of the neighbors.

Classifier #2: ID3 Decision Tree: Buids a decison tree using Information Gain to calculate splitting criteria. Predicts a
tuple's class by traversing that tuple through the tree, and identifying the class of the leaf where it lands.

To run: 
  To run with kNN: $ java Main knn
  To run with ID3 $ java Main id3
  
