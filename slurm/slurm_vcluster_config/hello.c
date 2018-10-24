#include <stdio.h>
#include <mpi.h>

int main(int argc, char** argv) {
    int myrank, nprocs,len;
    char nodename[128];
 
    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &nprocs);
    MPI_Comm_rank(MPI_COMM_WORLD, &myrank);

    MPI_Get_processor_name(nodename, &len);
    nodename[len]='\0';
    printf("Hello from processor %d (%s) of %d\n", myrank, nodename,nprocs);

    MPI_Finalize();
    return 0;
}

