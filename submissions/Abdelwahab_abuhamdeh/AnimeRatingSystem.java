package org.example;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;

public class AnimeRatingSystem {
    private final int CACHE_SIZE = 10;
    final private  LRUCache animeCache = new LRUCache(CACHE_SIZE);
    private final String LOG_FILE = "LOG.txt";
    private final String ANIME_FILE = "anime.txt";

    public AnimeRatingSystem() {
        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(LOG_FILE, true));

            // Create a shutdown hook to close the logWriter gracefully.
            final BufferedWriter finalLogWriter = logWriter;

            // Handles when the programme is not closed correctly
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    finalLogWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            
            // Read user input in a loop until the user enters "man of a culture"
            while (true) {
                System.out.print("Enter anime name, rating, or 'man of a culture': ");
                String userInput = reader.readLine().trim();

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();


                if (userInput.equalsIgnoreCase("man of a culture")) {
                    logWriter.write("User input: {\"" + userInput +"\"} At "+formatter.format(date)+ "\n");
                    break;
                }
                Anime anime = new Anime();
                String response = processUserInput(userInput,anime);
                if(anime.exist()){
                    System.out.println(anime);

                }
                else System.out.println(response);
                logWriter.write("User input: {\"" + userInput +"\"}, User output: {"+response+"} - "+formatter.format(date)+ "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (logWriter != null) {
                    logWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private  String processUserInput(String userInput,Anime anime) {
        // Check if the user input is a number (rating)
        if (Pattern.matches("^\\d+(\\.\\d+)?$", userInput)) {//pattern that matches with floating and integer numbers
            double ratingInput = Double.parseDouble(userInput);
            if(ratingInput>10||ratingInput<0){
                return "Rating Out Of Range!";
            }
            String name = getAnimeName(ratingInput);
            if(name == null){
                return "Anime Doesn't Exist!";
            }

            name = name.strip();
            double rating = getAnimeRating(name);
            anime.setRating(rating);
            anime.setName(name);
            return name;
        } else {
            Double rating = getAnimeRating(userInput);
            if(rating == -1){
                return "Anime Doesn't Exist!";
            }
            anime.setName(userInput);
            anime.setRating(rating);
            return rating.toString().strip();
        }
    }
    private double getAnimeRating(String name){
        String[] parts = name.split(" ");
        StringBuilder filteredName = new StringBuilder();
        for (String part : parts) {
            filteredName.append(part);
        }
        filteredName = new StringBuilder(filteredName.toString().toLowerCase().strip());
        double val = animeCache.getRating(filteredName.toString());

        if(val != -1){
            return val;
        }
        val = getAnimeRatingFromFile(filteredName.toString());
        animeCache.putRating(filteredName.toString(), val);
        return val;
    }
    private double getAnimeRatingFromFile(String target){
        try (BufferedReader fileReader = new BufferedReader(new FileReader(ANIME_FILE))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] parts = line.split(" ");
                StringBuilder name = new StringBuilder();
                for(int i = 0; i < parts.length-1 ; ++i){
                    name.append(parts[i]);
                }
                name = new StringBuilder(name.toString().toLowerCase().strip());
                double rating = Double.parseDouble(parts[parts.length-1]);
                if(name.toString().equals(target)){
                    return rating;
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private String getAnimeName(double rating) {
        String val = animeCache.getName(rating);
        if(val != null){
            return val;
        }
        val =  getAnimeNameFromFile(rating);
        if(val == null ){
            return null;
        }
        animeCache.putName(rating,val);
        return val;

    }
    private String getAnimeNameFromFile(double target){
        String val= null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(ANIME_FILE))) {
            String line;
            double mn = 10;
            while ((line = fileReader.readLine()) != null) {
                //System.out.println(line);
                String[] parts = line.split(" ");
                StringBuilder name = new StringBuilder();
                for(int i = 0; i < parts.length-1 ; ++i){
                    name.append(parts[i]);
                    name.append(" ");
                }
                name = new StringBuilder(name.toString().strip());
                double rating = Double.parseDouble(parts[parts.length-1]);
                if(rating >= target){
                    if(rating <= mn){
                        val = name.toString();
                        mn = rating;
                    }
                }

            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return  val;
    }
    private class LRUCache {
        final private Map<String, Double> getRatingMap = new LinkedHashMap<>();
        final private Map<Double, String> getAnimeMap = new LinkedHashMap<>();


        final private int capacity;

        public LRUCache(int capacity) {
            this.capacity = capacity;
        }

        public String getName(double key) {
            if (!getAnimeMap.containsKey(key)) return null;
            String val = getAnimeMap.get(key);
            putName(key, val);
            return val;
        }

        public void putName(double key, String value) {
            if (!getAnimeMap.containsKey(key) && (getAnimeMap.size() == capacity)) {
                getAnimeMap.remove(getAnimeMap.keySet().iterator().next());
            }
            getAnimeMap.remove(key);
            getAnimeMap.put(key, value);
        }

        public double getRating(String key) {
            if (!getRatingMap.containsKey(key)) return -1;
            double val = getRatingMap.get(key);
            putRating(key, val);
            return val;
        }

        public void putRating(String key, double value) {
            if (!getRatingMap.containsKey(key) && (getRatingMap.size() == capacity)) {
                getRatingMap.remove(getRatingMap.keySet().iterator().next());
            }
            getRatingMap.remove(key);
            getRatingMap.put(key, value);
        }
    }
}
