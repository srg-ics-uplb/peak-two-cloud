#!/bin/bash
#


if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <executable>"
    exit
fi


#the name of the job
#SBATCH --job-name=mpijob

#the stdout output of the job
#SBATCH --output=output.txt

#available nodes
#SBATCH --nodes=4

#number of tasks
#SBATCH --ntasks=4

#time limit (D-HH:MM), terminate the job after two minutes
#if not yet done
#SBATCH -t 0-0:02

#the executable
EXEC=$1

#number of nodes to use in the run
NODES=4

#execute
mpiexec -np $NODES -f ../nodes.txt ./$EXEC
