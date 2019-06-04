Dinesh Karamchandani - A20407484

###############Sort on Single Shared Memory Node #######################################	

This assignment aims at doing external data sort on a single shared memory node, with multi-threading.
This is also the 1st part of the sorting Large datasets using Shared memory, Hadoop and Spark, as it focuses only on the Shared memory approach.

A. Shared Memory Sort:

As the main criteria of this approach is to cater for sorting of large datasets, hence multi-threading was recommended course of action. Along with that I have designed the external sort program using quicksort and k-way merging to ensure minimum number of reads and writes to the disk as that is the main bottleneck here.
The input data for this was generated using gensort suite (http://www.ordinal.com/gensort.html),which has the initial 10 bytes as key and remaining 90 bytes as value.
As per given data, sorting was to be performed on 2GB and 20GB data, which is what has been showcased in the results.

The commands for MySort are implemented in the below 2 files:
	● mysort2GB.slurm
	● mysort20GB.slurm

The results of the Shared Memory sort program implemented in the MySort.java file are present in the below 2 log files:
	● mysort2GB.log
	● mysort20GB.log

Also these log files mention the time taken by the program for the implemented sorting on the external data using shared memory node.


	
B. Linux Sort 
The Linux Sort is used as a 'benchmark' to compare the performance of the above program with itself as the base. The Linux sort is also run a single shared memory node so as to emulate similar conditions for the work to be done and be fair.
Since we are using multi-threading in the above program, here too we are using multi-threading so that we can easily compare the performance of our program.
The commands for Linux sort are implemented in the below 2 files:
	● linsort2GB.slurm
	● linsort20GB.slurm
Whereas the results are saved in the below files:
	● linsort2GB.log
	● linsort20GB.log 

Also these log files mention the time taken by the program for the implemented sorting on the external data using shared memory node.
#########################How to run##################################

Assumptions : 
- This git is cloned or downloaded and extracted on the Linux testbed you want to test.
- The Linux testbed you have supports slurm based batch scheduling

A. Shared Memory sort:

1. Open the terminal for the instance you are running on.
2. Navigate to folder named "PA2A"
3. Execute the slurm files as mentioned below for 2GB and 20GB datasets
   Slurm files to execute: 
		● mysort2GB.slurm
		● mysort20GB.slurm

		eg: sbatch mysort2GB.slurm
	
4. The above would execute the script for the 2GB workload and the output is present in the log file called, 
				"mysort{x}GB.log" 
where {x} = 2GB or 20GB for 2GB and 20GB loads of large datasets.
5. The results of the above scripts are present in the below file, based on the slurm files you have executed so my`sort2GB.slurm after execution will give us mysort2GB.log file containing the results of the sort.
B. Linux sort:

1. Open the terminal for the instance you are running on.
2. Navigate to folder named "PA2A"
3. Execute the slurm files as mentioned below for 2GB and 20GB datasets
   Slurm files to execute: 
		● linsort2GB.slurm
		● linsort20GB.slurm

		eg: sbatch linsort2GB.slurm
	
4. The above would execute the script for the 2GB workload and the output is present in the log file called, 
				"linsort{x}GB.log" 
where {x} = 2GB or 20GB for 2GB and 20GB loads of large datasets.
5. The results of the above scripts are present in the below file, based on the slurm files you have executed so linsort2GB.slurm after execution will give us linsort2GB.log file containing the results of the sort.
