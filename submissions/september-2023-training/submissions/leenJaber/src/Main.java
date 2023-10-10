import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input  = new BufferedReader(new InputStreamReader(System.in));
        String animeName = input.readLine();


        boolean ischar = true;
        String temp_input = animeName;


        String ans =null;
        for (char c :temp_input.toCharArray()){
            if (Character.isDigit(c)){ischar=false; break;}
        }
        if (animeName.equals("man of a culture")){
            System.exit(0);
        }

        Anime anime = new Anime();
        if(ischar){

            anime.setName(animeName.toLowerCase());
        }
        else{
            anime.setRating(animeName );
        }



        LRU cache = new LRU();
        Writer write = new Writer();
        if (cache.Search_in_cache(anime)){
            ans =cache.Get(anime);
            System.out.println(ans);


        }

        else{
            if (!anime.Find_anime(anime).isEmpty()){
                ans = anime.Find_anime(anime);
                System.out.println(ans);
                if(anime.getName()!=null){anime.setRating(ans);}
                else{anime.setName(ans);}

                cache.Put(anime);


            }
            else if (anime.Find_anime(anime).isEmpty()){
                System.out.println("Anime wasn't found in the file!");
                System.out.println("Enter rating for the provided anime: ");
                String rate_it = input.readLine();
                anime.setRating(rate_it);
                cache.Put(anime);
                write.write_if_doesnt_exist(anime);


            }
        }

        write.write_in_log(animeName , ans);


    }
}