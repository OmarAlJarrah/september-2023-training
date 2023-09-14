public class AnimeRating
{
    String animeShowName;
    float rating;

    AnimeRating(String animeShowName, int rating)
    {
        this.animeShowName = animeShowName;
        this.rating = rating;
    }
    
    @Override
    public String toString()
    {
        return animeShowName + " " + rating;
    }
}