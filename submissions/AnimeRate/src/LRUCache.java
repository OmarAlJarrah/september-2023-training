import global.Constants;
import global.TxtFile;

import java.util.*;

class LRUCache {
    private final int capacity;
    private final Map<String, Anime> cache;
    private final LinkedList<Anime> linkedList;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.linkedList = new LinkedList<>();
    }

    public Anime get(String key) {
        if (cache.containsKey(key)) {
            Anime anime = cache.get(key);
            // Move the accessed Anime to the front of the linked list
            linkedList.remove(anime);
            linkedList.addFirst(anime);
            return anime;
        }
        return null;
    }

    public void put(String key, float value) {

        if (cache.size() >= capacity) {
            // Remove the least recently requested Anime from the cache and linked list
            Anime lruAnime = linkedList.removeLast();
            cache.remove(lruAnime.getName());
        }

        try {
            Anime newAnime = new Anime(key, value);
            cache.put(newAnime.getName(), newAnime);
            linkedList.addFirst(newAnime);
        }
        catch (IllegalArgumentException e) {
            String warning = "Warning: this anime won't add to the cache cause its rate is invalid please edit its rate in the anime txt file and try again.";
            System.out.println(warning);
            System.out.println(value);

            // Write to Log file -> PROGRAM
            TxtFile.writeToLogFile(Constants.LOG_PRINT_WARNING, warning);

            // Write to Log file -> USER
            TxtFile.writeToLogFile(Constants.LOG_PRINT_ANIME_NAME, Float.toString(value));
        }
    }
}
