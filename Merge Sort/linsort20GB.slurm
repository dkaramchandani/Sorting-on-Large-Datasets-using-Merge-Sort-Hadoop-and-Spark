#!/bin/bash
#SBATCH --nodes=1
#SBATCH --ntasks-per-node=1
#SBATCH --partition=compute
#SBATCH --output="linsort20GB.log"

echo 'Linux sorting begins...' 
time LC_ALL=C /usr/bin/sort --parallel=4 -o "/tmp/linsort20GB.out" "/input/data-20GB.in"
echo 'Validation of Linux sorting begins...' 
time /usr/bin/valsort "/tmp/linsort20GB.out"
