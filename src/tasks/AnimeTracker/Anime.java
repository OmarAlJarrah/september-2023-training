package tasks.AnimeTracker;

/**
 * A record representing an anime with its name and rating.
 * Implements Comparable to allow for sorting based on the anime's rating.
 */
public record Anime(String anime, double rate) implements Comparable<Anime> {

    /**
     * Returns a formatted string representation of the anime.
     *
     * @return A string in the format "{anime} {rate}".
     */
    @Override
    public String toString() {
        return "%s %.2f".formatted(anime, rate);
    }

    /**
     * Compares this anime to another anime based on their ratings.
     *
     * @param anime The other anime to compare to.
     * @return A negative value if this anime's rating is less than the other anime's rating,
     *         zero if they have the same rating, or a positive value if this anime's rating
     *         is greater than the other anime's rating.
     */
    @Override
    public int compareTo(Anime anime) {
        return Double.compare(this.rate, anime.rate);
    }
}
