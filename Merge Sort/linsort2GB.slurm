#!/bin/bash
#SBATCH --nodes=1
#SBATCH --ntasks-per-node=1
#SBATCH --partition=compute
#SBATCH --output="linsort2GB.log"

echo 'Linux sorting begins...' 
time LC_ALL=C /usr/bin/sort --parallel=4 -o "/tmp/linsort2GB.out" "/input/data-2GB.in"
echo 'Validation of Linux sorting begins...' 
time /usr/bin/valsort "/tmp/linsort2GB.out"
