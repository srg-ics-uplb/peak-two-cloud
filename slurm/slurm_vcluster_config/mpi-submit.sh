#!/bin/bash
#
#SBATCH --job-name=mpijob
#SBATCH --output=result.txt
#SBATCH --nodes=4
#SBATCH --ntasks=4


mpicc -o hello.exe hello.c
mpiexec -np 4 -f ../nodes.txt ./hello.exe


#p2c-mpi-slurm hello.c 2
#p2c-mpi-slurm mpi_array.c 4
