Thread:

Creating a Thread is far slower than using Thread-pool.
You can change the priority of a thread.
The max number of threads in a process related to resources.
Using Thread is a better option when the task is relatively long-running

Thread-Pool:

Running a Thread on thread-pool is far faster than directly creating a Thread.
The Thread-pool is useful for short-lived operation.

Single Thread Executor : A thread pool with only one thread. So all the submitted tasks will be executed sequentially. Method : Executors.newSingleThreadExecutor()

Cached Thread Pool : A thread pool that creates as many threads it needs to execute the task in parrallel. The old available threads will be reused for the new tasks. If a thread is not used during 60 seconds, it will be terminated and removed from the pool. Method : Executors.newCachedThreadPool()

Fixed Thread Pool : A thread pool with a fixed number of threads. If a thread is not available for the task, the task is put in queue waiting for an other task to ends. Method : Executors.newFixedThreadPool()

Scheduled Thread Pool : A thread pool made to schedule future task. Method : Executors.newScheduledThreadPool()

Single Thread Scheduled Pool : A thread pool with only one thread to schedule future task. Method : Executors.newSingleThreadScheduledExecutor()




Runnable vs Callable

A Callable<V> instance returns a result of type V, whereas a Runnable instance doesn't.
A Callable<V> instance may throw checked exceptions, whereas a Runnable instance can't
We can not pass/use Callable to an individual thread for execution i.e. Callable can be used only in Executor Framework. But, Runnable can be passed to an individual thread for execution (new Thread(new CustomRunnable())), as well as can be used in Executor Framework.
