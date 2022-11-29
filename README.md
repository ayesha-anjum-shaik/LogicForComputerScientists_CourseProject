# N Queens Problem Solution using SAT solver

## Satisfiability test of clauses and its application
Team Members :
- Venu Madhav Pendurthi
- Ayesha Anjum Shaik
- Sai Pranitha Kambham

## Introduction to the Problem Statement
- ”MiniSat is a minimalistic, open-source SAT solver, developed to help researchers and developers alike to get started on SAT”. We use this due to its key features,
  - Easy to install, run, modify in all OS
  - Highly efficient and provides optimal output
- MiniSat accepts the input in DIMACS CNF(Conjunctive Normal Form) format, which is simple line-oriented text clauses format built from term, clause and expression
- Problem Statement : To place the ’n’ queens in an nxn chessboard so that no two queens can attack each other either horizontally, vertically or diagonally
- Input : positive integer n
- Output : Optimal way how n queens can be placed on n*n chessboard so that no two queens can kill each other in an array format 1 to n with the positive numbers representing the queen positions and negative digits representing the
empty locations.
