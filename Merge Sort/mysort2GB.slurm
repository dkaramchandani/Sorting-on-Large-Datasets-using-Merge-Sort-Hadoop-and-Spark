#!/bin/bash
#SBATCH --nodes=1
#SBATCH --ntasks-per-node=1
#SBATCH --partition=compute
#SBATCH --output="mysort2GB.log"

make
echo 'MySort begins...' 
java  MySort "/input/data-2GB.in" 4
echo 'Validation of MySort begins...'
/usr/bin/valsort "/tmp/MySortedFile.out" 