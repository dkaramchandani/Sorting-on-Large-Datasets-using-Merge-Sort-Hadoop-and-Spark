Dinesh Karamchandani - A20407484

###############Sorting using Hadoop and Spark #######################################	

This assignment aims at doing sorting Hadoop on a multiple nodes, using MapReduce
This is also the 2nd part of the sorting Large datasets using Shared memory, Hadoop and Spark, as it focuses on the Hadoop and Spark implementations.

A. Hadoop Sort:

In this I have used Hadoop's MapReduce based implementation to sort the large datasets.
To do this I have designed my own Mapper and Reducer classes, using Java. I have provided the mapper with input as key (10 Bytes) and value(90 Bytes), and in the 
reducer I am merging these 2 values and writing them to the final output file.

As per the given data, sorting was be performed on 8GB, 20GB and 80GB data, which is what has been showcased in the results.

The commands for MySort are implemented in the below 2 files:
	● hadoopsort8GB.slurm
	● hadoopsort20GB.slurm
	● hadoopsort80GB.slurm

The results of the Shared Memory sort program implemented in the MySort.java file	 are present in the below 2 log files:
	● HadoopSort8GB.log
	● HadoopSort20GB.log
	● HadoopSort80GB.log

Also these log files mention the time taken by the program for the implementation of the sorting is mentioned.

B. Spark Sort:

Although this is Spark we are using , it also utilizes Hadoop's MapReduce based implementation to sort the large datasets.
In this I am using sortByKey() method to sort the data based on the first 10 bytes of the input data.

As per the given data, sorting was be performed on 8GB, 20GB and 80GB data, which is what has been showcased in the results.

The commands for MySort are implemented in the below 2 files:
	● sparksort8GB.slurm
	● sparksort20GB.slurm
	● sparksort80GB.slurm

The results of the Shared Memory sort program implemented in the MySort.java file	 are present in the below 2 log files:
	● SparkSort8GB.log
	● SparkSort20GB.log
	● SparkSort80GB.log

Also these log files mention the time taken by the program for the implementation of the sorting is mentioned.


	
C. Linux Sort 
The Linux Sort is used as a 'benchmark' to compare the performance of the above program with itself as the base. The Linux sort is also run a single shared memory node so as to emulate similar conditions for the work to be done and be fair.
Since we are using multi-threading in the above program, here too we are using multi-threading so that we can easily compare the performance of our program.

Since this was already done in the part A of this assignment hence we will be only using the values which had got earlier.

#########################How to run##################################

Assumptions : 
- This git is cloned or downloaded and extracted on the Linux testbed you want to test.
- The Linux testbed you have supports slurm based batch scheduling

A. Make file
1. Open the terminal for the instance you are running on.
2. Run the make file using the below command, without the quotes
		"make all"
3. The above is just in case you want to compile the programs separately	
		
A. Hadoop Sort:

1. Open the terminal for the instance you are running on.
2. Assuming you have saved the cloned/downloaded git(& extracted it contents on ur linux testbed), Navigate to folder called "cs553-pa2b"
3. Inside the above folder you will find the slurm files to execute 
4. Execute the slurm files as mentioned below for 8GB, 20GB and 80GB datasets,
   
		● hadoopsort8GB.slurm
		● hadoopsort20GB.slurm
		● hadoopsort80GB.slurm

To run any of these above slurm files you will need to run the below command with '(x)' being replaced by 8,20 or 80 for 8GB,20GB and 80 GB datasets
		sbatch hadoopsort(x)GB.slurm
		
		eg: sbatch hadoopsort20GB.slurm
	
5. The above would execute the script for that workload and save the output in its respective log file.
				for eg: "sbatch hadoopsort20GB.log"  would submit job for sorting 20GB files, the output for the same would then be saved in "hadoopsort20GB.log"
	The list of log files we will for running the above slurm files is as below : 
		● HadoopSort8GB.log
		● HadoopSort20GB.log
		● HadoopSort80GB.log
	
B. Spark Sort:

1. Open the terminal for the instance you are running on.
2. Assuming you have saved the cloned/downloaded git(& extracted it contents on ur linux testbed), Navigate to folder called "cs553-pa2b"
3. Inside the above folder you will find the slurm files to execute 
4. Execute the slurm files as mentioned below for 8GB, 20GB and 80GB datasets,
   
		● sparksort8GB.slurm
		● sparksort20GB.slurm
		● sparksort80GB.slurm

To run any of these above slurm files you will need to run the below command with '(x)' being replaced by 8,20 or 80 for 8GB,20GB and 80 GB datasets
		sbatch sparksort(x)GB.slurm
		
		eg: sbatch sparksort20GB.slurm
		
	
5. The above would execute the script for that workload and save the output in its respective log file.
				for eg: "sbatch sparksort20GB.log"  would submit job for sorting 20GB files, the output for the same would then be saved in "sparksort20GB.log"
	The list of log files we will for running the above slurm files is as below : 
		● SparkSort8GB.log
		● SparkSort20GB.log
		● SparkSort80GB.log