import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.*;

public class word_counter {
    private static final int NUM_THREADS = 10;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        String directoryPath = "docs/";
        String resultFilePath = "result/count.txt";

        long startTime = System.currentTimeMillis();

        File directory = new File(directoryPath);
        File[] textFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (textFiles == null || textFiles.length == 0) {
            System.out.println("No text files found in the directory.");
            return;
        }

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        Future<WordCountResult>[] futures = new Future[textFiles.length];

        for (int i = 0; i < textFiles.length; i++) {
            File textFile = textFiles[i];
            futures[i] = executorService.submit(new WordCounterTask(textFile));
        }

        StringBuilder resultBuilder = new StringBuilder();
        int totalWordCount = 0;

        for (Future<WordCountResult> future : futures) {
            WordCountResult wordCountResult = future.get();
            resultBuilder.append(wordCountResult.getFileName()).append(": ").append(wordCountResult.getWordCount()).append(" words\n");
            totalWordCount += wordCountResult.getWordCount();
        }

        executorService.shutdown();

        long endTime = System.currentTimeMillis();
        long totalTimeTaken = endTime - startTime;

        resultBuilder.append("Total word count for all text files: ").append(totalWordCount).append("\n")
                .append("Total time taken: ").append(totalTimeTaken).append(" milliseconds");

        String result = resultBuilder.toString();

        // Print the result
        System.out.println(result);

        // Save the result to an external file
        try (FileWriter writer = new FileWriter(resultFilePath)) {
            writer.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class WordCountResult {
        private final String fileName;
        private final int wordCount;

        WordCountResult(String fileName, int wordCount) {
            this.fileName = fileName;
            this.wordCount = wordCount;
        }

        public String getFileName() {
            return fileName;
        }

        public int getWordCount() {
            return wordCount;
        }
    }

    static class WordCounterTask implements Callable<WordCountResult> {
        private final File textFile;

        WordCounterTask(File textFile) {
            this.textFile = textFile;
        }

        @Override
        public WordCountResult call() throws IOException {
            StringBuilder text = new StringBuilder();
            try (Scanner scanner = new Scanner(textFile)) {
                while (scanner.hasNextLine()) {
                    text.append(scanner.nextLine()).append(" ");
                }
            }
            String[] words = text.toString().split("\\s+");
            return new WordCountResult(textFile.getName(), words.length);
        }
    }
}
