import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import selfish.*;
import selfish.deck.*;

public class GameDriver {

    /**
     * A helper function to centre text in a longer String.
     * 
     * @param width The length of the return String.
     * @param s     The text to centre.
     * @return A longer string with the specified text centred.
     */
    public static String centreString(int width, String s) {
        return String.format("%-" + width + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    /**
     * @param GameDriver Class constructor.
     */
    public GameDriver() {
    }

    public static void main(String[] args) {
        // GameEngine game=new GameEngine();
        // ArrayList<Astronaut> name=new ArrayList<Astronaut>();
        // Console con=System.console();
        // Boolean flag=true;
        // String str;
        // String nm[]=new String[5];
        // String choice;
        // int count=1;
        // //Reading file and adding new players
        // File file=new
        // File("C:\\Users\\Garry\\GitRepos\\comp16412-coursework-2__g49678gs\\io\\art.txt");
        // BufferedReader br= new BufferedReader(new FileReader(file));
        // while(flag.equals(true)&&(br.readLine()) != null){
        // for(int i=0;i<17;i++){
        // System.out.println(br.readLine());
        // }
        // System.out.print("Player "+count+" Name? ");
        // str=con.readLine();
        // nm[0]=str;
        // // name.add(new Astronaut(str,game));
        // count++;
        // System.out.print("Player "+count+" Name? ");
        // str=con.readLine();
        // nm[1]=str;
        // // name.add(new Astronaut(str,game));
        // count++;
        // while(flag.equals(true)||count<=5)
        // {
        // System.out.print("Add another? [Y]es/[N]o :");
        // choice=con.readLine();
        // if(choice.equals("Y"))
        // {
        // System.out.print("Player "+count+" Name? ");
        // str=con.readLine();
        // nm[count-1]=str;
        // //name.add(new Astronaut(str,game));
        // count++;
        // }
        // else
        // {
        // break;
        // }
        // }
        // if(count==3){
        // System.out.print("After a dazzling (but doomed) space mission "+nm[0]+" and
        // "+nm[1]+" are floating in the space and their oxygen supplies are running
        // low. Only the first back to the ship will survive!");
        // }

        // if(count==4){
        // System.out.print("After a dazzling (but doomed) space mission "+nm[0]+",
        // "+nm[1]+" and "+nm[2]+" are floating in the space and their oxygen supplies
        // are running low. Only the first back to the ship will survive!");
        // }

        // if(count==5){
        // System.out.print("After a dazzling (but doomed) space mission "+nm[0]+",
        // "+nm[1]+", "+nm[2]+" and "+nm[3]+" are floating in the space and their oxygen
        // supplies are running low. Only the first back to the ship will survive!");
        // }

        // if(count==6){
        // System.out.print("After a dazzling (but doomed) space mission "+nm[0]+" and
        // "+nm[1]+", "+nm[2]+", "+nm[3]+" and "+nm[4]+" are floating in the space and
        // their oxygen supplies are running low. Only the first back to the ship will
        // survive!");
        // }

        // break;
        // }
        // // GameDeck d=new
        // GameDeck("C:\\Users\\Garry\\GitRepos\\comp16412-coursework-2__g49678gs\\io\\ActionCards.txt");
        // // SpaceDeck s=new
        // SpaceDeck("C:\\Users\\Garry\\GitRepos\\comp16412-coursework-2__g49678gs\\io\\SpaceCards.txt");

    }

}
