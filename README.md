[![Build Status](https://travis-ci.org/khorshidi/packer.svg?branch=master)](https://travis-ci.org/khorshidi/packer)
[![codecov](https://codecov.io/gh/khorshidi/packer/branch/master/graph/badge.svg)](https://codecov.io/gh/khorshidi/packer)
# Packer
A library to solve [0/1 Knapsack](https://en.wikipedia.org/wiki/Knapsack_problem) scenarios.

## 0/1 Knapsack
Given weights and values of `n` items, put these items in a knapsack of capacity `W` to get the maximum total value in the knapsack.
I used [Dynamic Programming](https://en.wikipedia.org/wiki/Dynamic_programming) to solve the problem in this project.

### build
```bash
mvn clean pacakge
```

### run tests
```bash
mvn clean test
```

### usage
``` java
String solutions = Packer.pack("/absolute/path/to/input/file");
```
### input file format
Input file must contains lines that represent a scenario of Knapsack problem.
The line pattern is
```
W : (item_1.index,item_1.weight,€item_1.cost) (item_2.index,item_2.weight,€item_2.cost) ... (item_n.index,item_n.weight,€item_n.cost)
```
For example this is a valid line:
```
81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)
```
the output for the file:
```
81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)
8 : (1,15.3,€34)
75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78) 
56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)
```
will be:
```
4
-
2,7
8,9
```
