
Sequential streams outperformed parallel streams when the number of elements in the collection was less than 100,000.
Parallel streams performed significantly better than sequential streams when the number of elements was more than 100,000.



ForkJoinPool takes a big task, splits it into smaller tasks, and those smaller tasks split themselves again into subtasks until each subtask is atomic or not divisible. The ForkJoinPool is basically a specialized implementation of ExecutorService implementing the work-stealing algorithm.The Fork/Join Framework addresses this limitation by introducing another layer between the tasks and the threads executing them, which allows the threads to put blocked tasks on the side and deal with them when all their dependencies are executed. 

Fork:  Split larger task into smaller tasks. Ex: Task 1.1 splits to Task 1.1.1 and Task 1.1.2
Join: Get result from immediate subtasks. Ex: Task 1.1 take results from Task 1.1.1 and Task 1.1.2

The Fork/Join Framework makes use of a special kind of thread pool called FrkJoinPool
