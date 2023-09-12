import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.io.*;
import java.util.*;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LRU {
    final int capacity = 10;
    private int size;
    final Map<String , Node> linkedListNodeMap;
    final DoublyLinked DLL;

    LRU() throws IOException {
        this.size = size;
        this.linkedListNodeMap = new HashMap<>(capacity);
        this.DLL = new DoublyLinked();
        this.unload_cache();


    }
    private void save_cache() throws IOException{

        FileWriter hh = new FileWriter("C:\\Users\\user\\Desktop\\cache.txt");
        BufferedWriter mm = new BufferedWriter(hh);
        mm.close();

        FileWriter fw = new FileWriter("C:\\Users\\user\\Desktop\\cache.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        Node current = this.DLL.head;
        while (current != null) {
            bw.write(current.data.getName() + " " + current.data.getRating() +"\n");
            current = current.next;
        }

        bw.close();
        fw.close();

    }
    private void unload_cache() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\user\\Desktop\\cache.txt"));
        while (br.ready()){
            Anime a = new Anime();
            String s = br.readLine();


            String[] tokens = s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); // This will split the string wherever there's a digit
            String name = tokens[0].substring(0,tokens[0].length()-1);
            String rating = tokens[1];



            a.setName(name);
            a.setRating(rating);

            Node to_add = this.DLL.addLast(a);
            this.linkedListNodeMap.put(a.getName() , to_add);
        }
        this.size = this.linkedListNodeMap.size();
        br.close();
    }
    public void Put(Anime aa) throws IOException {
        if (this.size== 10){
            this.DLL.deleteLast();


        }
        Node to_add = DLL.addfirst(aa);
        this.linkedListNodeMap.put(aa.getName(), to_add);

        this.size++;
        this.save_cache();
    }

    public String Get(Anime aa) throws IOException {
        String rate = null;
        if (this.linkedListNodeMap.containsKey(aa.getName())) {
            if (aa.getName() == null) {
                 rate = linkedListNodeMap.get(aa.getRating()).data.getName();
                 aa.setName(rate);
            }
            else {
                rate = linkedListNodeMap.get(aa.getName()).data.getRating();
                aa.setRating(rate);
            }
            this.DLL.DeleteSpecficNode(this.linkedListNodeMap.get(aa.getName()));
            Node temp = this.DLL.addfirst(aa);
            this.linkedListNodeMap.replace(aa.getName(),this.linkedListNodeMap.get(aa.getName()), temp);

        }
        else if (aa.getName()==null){
            Node current = this.DLL.head;
            while (current != null) {
                if (current.data.getRating().equals(aa.getRating())){
                    this.save_cache();
                    return current.data.getName();
                }
                current = current.next;
            }
        }

        this.save_cache();
        return rate;
    }
    public boolean Search_in_cache(Anime anime){
        if (this.linkedListNodeMap.containsKey(anime.getName())){
            return true;
        }
        else if (anime.getName()==null){
            Node current = this.DLL.head;
            while (current != null) {
                if (current.data.getRating().equals(anime.getRating())){
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    public void Display2(){
        Node current = this.DLL.head;
        while (current != null) {
            System.out.print(current.data.getName() + " " +current.data.getRating()  );
            current = current.next;
        }
    }
    public void Display1(){
        this.linkedListNodeMap.forEach((key, value) -> System.out.println(key + " " + value));
    }










}
