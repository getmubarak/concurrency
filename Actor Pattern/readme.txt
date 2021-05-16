reading a file of size 51.5 MB with 963366 words and counting the number of words with it using different versions of concurrency.

<b> Plan 1: Make a multithreaded version of threads in java </b>

Total thread processing time for 50 threads appear to be 29174 milliseconds ms(without synchronised). It appears to be 30317 milliseconds ms(with synchronised). This happens, because locks due to synchronised introduce a new menace: Deadlock. With multiple locks in place, performance suffers and processing time increases.

In this scenario, you also need to worry about how the interleaved execution of multiple threads fulfills the invariant which is processing the complete file at once takes place.

<b> Plan 2: Make an actor using scala </b>

Upon execution of this code, it uses 9990.50850 ms to count the number of words in a file. It uses a round-robin pool of 2 actors and routes 10 files having 1128024 words to all the actors. Increasing actors will require more heap size but reduces processing time.
