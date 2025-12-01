package com.baker.billy.core;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public abstract class Puzzle {
    protected String[] input;
    protected int year;
    protected int day;

    /**
     * Creates a new puzzle
     *
     * @param year The year
     */
    public Puzzle(int year) {
        String name = getClass().getSimpleName();
        day = Integer.parseInt(name.substring(6));
        this.year = year;
        setup();
    }

    /**
     * Polls the initial data from the specified input file
     */
    private void setup() {
        // Ensure file structure is setup
        String root = "src/main/resources/year/" + year;
        Path rootDirectory = Paths.get(root);

        if(!Files.exists(rootDirectory)) { // Technically this isn't accurate, the program may not have access to the directory
            try {
                Files.createDirectory(rootDirectory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String fileName = root + "/day" + day + ".puz";
        Path file = Paths.get(fileName);
        if(!Files.exists(file)) {
            System.out.println("Retrieve input file from web");
            try {
                var result = SimpleRequest.sendRequest("https://adventofcode.com/" + year + "/day/" + day + "/input");
                createInputFile(fileName, result.body());
            } catch(IOException | InterruptedException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        // Read in input file
        try (BufferedReader rdr = new BufferedReader(new FileReader(fileName))) {
            ArrayList<String> rawInput = new ArrayList<>();
            String line;

            // Read all input into the initial container
            while((line = rdr.readLine()) != null) {
                rawInput.add(line);
            }

            input = rawInput.toArray(new String[0]);
        } catch(FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Could not find the specified file: " + fileName,
                    "File Not Found", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "There was an error conducting IO operations:\n" + e.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void createInputFile(String fileName, String input) throws IOException {
        Path file = Paths.get(fileName);
        if(!Files.exists(file)) {
            Files.createFile(file);
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write((input.replaceFirst("[\\r\\n]+$", "")));
            writer.flush();
        }
    }

    /**
     * Does both tasks for the day
     */
    public long doTasks() {
        System.out.printf("======================== DAY %d ========================\n", day);
        long start = System.nanoTime();
        System.out.println("---- Task 1 ----");
        task1();
        System.out.println("\n---- Task 2 ----");
        task2();
        long elapsed = System.nanoTime() - start;
        System.out.println();
        System.out.printf("Total task runtime was %fms\n", elapsed / 1000.0 / 1000.0);
        return elapsed;
    }

    /**
     * Accomplishes the first task for the day
     */
    protected abstract void task1();

    /**
     * Accomplishes the second task for the day
     */
    protected abstract void task2();

    /**
     * Dynamically gets a new puzzle
     *
     * @param className The name of the class to instantiate
     * @return A new puzzle
     */
    public static Puzzle getPuzzle(int year, String className) {
        if(className == null) {
            throw new IllegalArgumentException("Path to input file or class name is null");
        }
        try {
            Class clazz = Class.forName(className);
            Constructor<Puzzle> con = clazz.getConstructor(int.class);
            return con.newInstance(year);
        } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                InvocationTargetException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
