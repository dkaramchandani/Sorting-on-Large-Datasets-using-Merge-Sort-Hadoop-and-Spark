#!/bin/bash
#SBATCH --partition=compute
#SBATCH --output="SparkSort20GB.log"

make
spark-submit --class SparkSort --master yarn --deploy-mode client --driver-memory 2g --executor-memory 6g --executor-cores 3 --num-executors 3 SparkSort.jar /input/data-20GB /user/dkaramchandani/output-spark
hadoop jar /opt/hadoop-2.9.0/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.9.0.jar teravalidate /user/dkaramchandani/output-spark /user/dkaramchandani/report-spark
hadoop fs -get /user/dkaramchandani/report-spark/part-r-00000
