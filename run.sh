echo "Enter the number of queens: ":
read n
java nqueens.java $n
minisat queens.cnf output
cat output