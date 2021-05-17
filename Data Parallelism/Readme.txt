
Sequential streams outperformed parallel streams when the number of elements in the collection was less than 100,000.
Parallel streams performed significantly better than sequential streams when the number of elements was more than 100,000.

Simplest formula for measuring parallelism is "NQ" model as provided by Brian Goetz in his presentation.

NQ Model:

N x Q > 10000
where,
N = number of items in dataset
Q = amount of work per item

It means if you have a large number of datasets and less work per item(For example: Sum), parallelism might help you run program faster and vice versa is also true. So if you have less number of datasets and more work per item(doing some computational work), then also parallelism might help you in achieving results faster.



ForkJoinPool takes a big task, splits it into smaller tasks, and those smaller tasks split themselves again into subtasks until each subtask is atomic or not divisible. The ForkJoinPool is basically a specialized implementation of ExecutorService implementing the work-stealing algorithm.The Fork/Join Framework addresses this limitation by introducing another layer between the tasks and the threads executing them, which allows the threads to put blocked tasks on the side and deal with them when all their dependencies are executed. 

Fork is to divide a large task into parallel executions of several subtasks. Similar to Map in MapReduce.
Join is the result of merging the execution of these subtasks, and finally the result of this large task. Similar to Reduce in MapReduce.

The Fork/Join Framework makes use of a special kind of thread pool called FrkJoinPool
To support parallelism for collections using parallel streams, a common ForkJoinPool is used internally.
The ForkJoin framework provides two types of tasks: 'RecursiveTask' and 'RecursiveAction'.  The difference between these two is that RecursiveTask can return a value while RecursiveAction cannot.


The only feasible approach to tackling large-data problems is to divide and conquer.
The master-slave pattern and Map Reduce Pattern works based on the divide and conquer principle
