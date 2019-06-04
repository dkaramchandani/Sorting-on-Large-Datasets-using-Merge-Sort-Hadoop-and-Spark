#!/bin/bash
#SBATCH --nodes=1
#SBATCH --ntasks-per-node=1
#SBATCH --partition=compute
#SBATCH --output="mysort20GB.log"

make
echo 'MySort begins...' 
java -Xmxs64m -Xmx2g MySort "/input/data-20GB.in" 4
echo 'Validation of MySort begins...'
/usr/bin/valsort "/tmp/MySortedFile.out" 