#!/bin/bash
#

#the name of the job
#SBATCH --job-name=hello

#the stdout output of the job
#SBATCH --output=hello.out

#available nodes
#SBATCH --nodes=4

#number of tasks
#SBATCH --ntasks=4

#the source file
SOURCE=hello.c

#the executable
EXEC=hello.exe

#number of nodes to use in the run
NODES=4

#compile and execute
mpicc -o $EXEC $SOURCE
mpiexec -np $NODES -f ../nodes.txt ./$EXEC
