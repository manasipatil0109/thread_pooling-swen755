
# Word Counter with Thread Pooling

## Description
This project uses Java to create a multi-threaded word counting tool. Using a thread pool of ten threads, the program reads several text files from a directory, counts the words in each file concurrently, and then writes the result to an output file. The main goal is to illustrate the "thread pooling" architectural strategy, which improves efficiency by eliminating overhead and reusing threads.

## Objectives
- Execute a performance-critical operation and demonstrate how to use a thread pool with ten threads.
- Use threads to handle several files to demonstrate thread management.
- Effectively count the words in a collection of text files.

## How to Run the Code

Java Development Kit (JDK) 8 or above installed on your system. A folder named `docs` containing `.txt` files that you want to process. Ensure you have permission to create a result file in the `result` directory (read-write access).

1. Clone or download the project source code.
2. Compile the code using the following command:
   ```bash
   javac word_counter.java
   ```
3. Run the compiled code using:
   ```bash
   java word_counter
   ```
   Ensure that the `docs/` directory with text files is in the same location as the code or update `directoryPath` accordingly.

4. The result will be printed to the console and saved in `result/count.txt`.

## Code Explanation
This Java program is a multi-threaded word counter designed to process multiple .txt files concurrently. 

**Thread Pool**: Uses an ExecutorService with a fixed thread pool of 10 threads to manage task execution, ensuring threads are reused for efficiency.

**WordCounterTask Class**: Implements Callable and reads a text file line by line, counts the words, and returns the word count along with the file name.

**Main Method**:
  - Reads .txt files from a specified directory.
  - Submits WordCounterTask instances to the thread pool for concurrent processing.
  - Collects results using Future objects, aggregates word counts, and writes them to the console and an output file.

### Functionalities
- **Thread Pooling**: Reuses threads for processing tasks, improving performance over creating new threads for each task.
- **Concurrency**: Processes files in parallel, reducing the total time for word counting.
- **File Handling**: Reads and writes text files efficiently.

## Frameworks and Libraries Used

**Java Concurrency Package**
- java.util.concurrent.ExecutorService
- java.util.concurrent.Callable
- java.util.concurrent.Future

**Java I/O Package**
- java.io.File
- java.io.FileWriter
- java.util.Scanner

**Core Java Libraries**
- String and StringBuilder
