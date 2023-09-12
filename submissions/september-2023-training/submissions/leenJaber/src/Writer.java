import java.util.*;
import java.io.*;

public class Writer  {
    public void write_in_log(String user , String program) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\user\\Desktop\\LOG.txt" , true) );

        bw.write(user + '\t' + program + '\n');
        bw.close();

    }
    public void write_if_doesnt_exist(Anime anime) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\user\\Desktop\\sample-input.txt", true) );
        bw.write(anime.getName() +'\t' + anime.getRating() + '\n');
        bw.close();
    }





}
