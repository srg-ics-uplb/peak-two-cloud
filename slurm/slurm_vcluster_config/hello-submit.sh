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

#time limit (D-HH:MM), terminate the job after two minutes
#if not yet done
#SBATCH -t 0-0:02

#the executable
EXEC=hello.exe

#number of nodes to use in the run
NODES=4

#execute
mpiexec -np $NODES -f ../nodes.txt ./$EXEC
