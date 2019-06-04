#!/bin/bash
#SBATCH --partition=compute
#SBATCH --output="HadoopSort8GB.log"

make
hadoop jar HadoopSort.jar HadoopSort /input/data-8GB /user/dkaramchandani/output-hadoop
hadoop jar /opt/hadoop-2.9.0/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.9.0.jar teravalidate /user/dkaramchandani/output-hadoop /user/dkaramchandani/report-hadoop
hadoop fs -get /user/dkaramchandani/report-hadoop/part-r-00000