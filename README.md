# Hadoop Main Project

This is the main project which will be in charge of loading tweets retrieved from the _Hapoop-PythonTwitter_. In the present repository, we implemented two main programs: a __workflow__ and a __scheduler__. The workflow was implemented in _Hue_. Its main task is to 

## Pre-requisites

IntelliJ must be installed in the environment, along with the whole __Hadoop ecosystem__, that is: HDFS - Hive - YARN - Oozie


## Content

In order to generate the _jar_ executable, you must execute __sbt clean package__ inside the project. The _clean_ command is used to erase older versions, and _package_ to generate the jar package.

Subsequently, send the jar package to _edge node_ using the command:

```
scp [filepath]  hapoop@edge6.sagean.fr:/home/hapoop/
```

Put the necessary files in HDFS by executing in the edge node:

> hdfs dfs -put [filepath]
   ... for example: hdfs dfs -put [json file]

Finally, run FromJsonFile again:

spark-submit --class FromJsonFile hello_2.11-1.0.jar path / to / myfile path / to / anotherfile
