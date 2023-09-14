import java.io.*;
import java.util.*;

public class Main
 {

    static String log = "";

    static public void LogAndPrint(String s) 
    {
        log += s + "\n";
        System.out.println(s);
    }

    public static void main(String[] args) 
    {

        LinkedHashMap<String, AnimeRating> lruCache = new LinkedHashMap<>(16, 0.75f, true) 
        {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, AnimeRating> eldest) 
            {
                return size() > 10;
            }
        };

        List<AnimeRating> animeRatings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("sample-input.txt"))) 
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                int lastSpaceIndex = line.lastIndexOf(" ");
                String animeName = line.substring(0, lastSpaceIndex);
                String ratingStr = line.substring(lastSpaceIndex + 1);
                AnimeRating rating = new AnimeRating(animeName, Integer.parseInt(ratingStr));
                animeRatings.add(rating);
            }
        } 

        catch (IOException e) 
        {
            LogAndPrint("Error reading file");
        }

        Scanner sc = new Scanner(System.in);

        try 
        {
            while (true) 
            {
                LogAndPrint("Enter a command: ");
                String input = sc.nextLine();
                log += input + "\n";
                if (input.matches("[0-9]+")) 
                {
                    int n = Integer.parseInt(input);
                    if (n < 0 || n > 10) 
                    {
                        log += "Rating should be between 0 and 10\n";
                        throw new IllegalArgumentException("Rating should be between 0 and 10");
                    }

                    for (AnimeRating rating : animeRatings) 
                    {
                        if (rating.rating >= n) 
                        {
                            lruCache.put(rating.animeShowName.toLowerCase(), rating);
                            LogAndPrint(rating.toString());
                        }
                    }
                } else if (!"man of culture".equalsIgnoreCase(input)) 
                {
                    AnimeRating rating = lruCache.get(input.toLowerCase());
                    if (rating != null) 
                    {
                        LogAndPrint("From cache: " + rating);
                    }

                    boolean found = false;
                    for (AnimeRating anime : animeRatings) 
                    {
                        if (anime.animeShowName.equalsIgnoreCase(input)) 
                        {
                            lruCache.put(anime.animeShowName.toLowerCase(), anime);
                            LogAndPrint(anime.toString());
                            found = true;
                            break;
                        }
                    }

                    if (!found) 
                    {
                        LogAndPrint("Anime not found. Please provide a rating.");
                        LogAndPrint("Enter a rating for " + input);
                        String rate = sc.nextLine();
                        log += rate + "\n";
                        if (rate.matches("[0-9]+")) 
                        {
                            int n = Integer.parseInt(rate);
                            if (n < 0 || n > 10) 
                            {
                                log += "Rating should be between 0 and 10\n";
                                throw new IllegalArgumentException("Rating should be between 0 and 10");
                            }
                            AnimeRating newRating = new AnimeRating(input, n);
                            animeRatings.add(newRating);
                        }
                    }
                }
                else
                 {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("LOG.txt"))) 
                    {
                        writer.write(log);
                    } catch (IOException e) 
                    {
                        LogAndPrint("An error occurred while writing to the log file.");
                    }
                    break;
                }
            }
        } 
        catch (Exception e) 
        {
            LogAndPrint("An error occurred: " + e.getMessage());
        } 
        finally 
        {
            sc.close();
        }
    }
}
