#!/bin/bash
#
#SBATCH --job-name=hello
#SBATCH --output=hello.out
#SBATCH --nodes=4
#SBATCH --ntasks=4

mpicc -o hello.exe hello.c
mpiexec -np 4 -f ../nodes.txt ./hello.exe


