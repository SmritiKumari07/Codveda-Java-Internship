import java.io.*;

import java.nio.file.Files;

import java.nio.file.Path;

import java.util.Scanner;

public class FileProcessor {

    public static class Stats {
        public long lines=0, words=0, chars=0;
        @Override public String toString() {
            return String.format("Lines: %d%nWords: %d%nCharacters: %d%n", lines, words, chars);
        }
    }

    public static Stats processFile(Path inputPath) throws IOException {
        Stats stats = new Stats();
        try (BufferedReader br = Files.newBufferedReader(inputPath)) {
            String line;
            while ((line = br.readLine()) != null) {
                stats.lines++;
                stats.chars += line.length() + 1; // +1 for newline
                String[] tokens = line.trim().split("\\s+");
                if (line.trim().isEmpty()) continue;
                stats.words += tokens.length;
            }
        }
        return stats;
    }

    public static void writeStats(Path outputPath, Stats stats) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(outputPath)) {
            bw.write("File processing results:\n");
            bw.write(stats.toString());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("= File Processor =");
        System.out.print("Enter input file path: ");
        String in = sc.nextLine().trim();
        System.out.print("Enter output file path: ");
        String out = sc.nextLine().trim();

        Path inputPath = Path.of(in);
        Path outputPath = Path.of(out);

        if (!Files.exists(inputPath)) {
            System.out.println("Input file does not exist: " + inputPath);
            return;
        }

        try {
            Stats stats = processFile(inputPath);
            writeStats(outputPath, stats);
            System.out.println("Processing complete. Results written to " + outputPath.toAbsolutePath());
            System.out.println(stats);
            System.out.println("Task 2 of Level 2 accomplished by Smriti Kumari");
        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
