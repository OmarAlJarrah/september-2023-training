import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.function.Supplier;

import java.util.*;
public class Anime {
    private String Name;
    private String rating;
    Anime(){
        this.Name = null;
        this.rating = null;
    }

    public String Find_anime(Anime given_anime) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\user\\Desktop\\sample-input.txt"));
        List<Anime> list=new ArrayList<Anime>();

        while (br.ready()) {

            Anime a = new Anime();
            String s = br.readLine();
            StringTokenizer st = new StringTokenizer(s, " ");
            String name = "";
            String rating = "";

            int numTokens = st.countTokens();
            for (int i = 0; i < numTokens - 1; i++) {
                name += st.nextToken().toLowerCase() + " ";
            }

            rating = st.nextToken();

            a.setRating(rating);
            a.setName(name);

            if (given_anime.Name == null) {
                int number = Integer.parseInt(rating);
                int given_rating = Integer.parseInt(given_anime.rating);

                if (number > given_rating) {
                    list.add(a);}

                if (number==given_rating){
                    br.close();
                    return name;

                    }


            }

            else{
                if (given_anime.Name.equals(name.substring(0,name.length()-1))){
                    br.close();
                    return a.rating;

                }

            }
        }
        if (given_anime.Name==null){
            String element = list.get(0).getName();
            br.close();
            return element;

        }
        br.close();
        return "";
    }
    public String getName(){
        return Name;
    }
    public String getRating(){
        return rating;
    }
    public void setName(String newName){
        this.Name = newName;
    }
    public void setRating(String newRating){
        this.rating = newRating;
    }
    

}
