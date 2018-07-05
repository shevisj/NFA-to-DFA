/**
 * Created by Albert on 2/26/17.
 */

package com.OGCoders;

import java.io.*;
import java.util.*;

public class FileHandler {

    private String fileName;

    public FileHandler(String fName){
        this.fileName = fName;
    }

    public NFA getContents() throws FileNotFoundException {

        NFA nfaContents = null;
        try{

            BufferedReader br = new BufferedReader(new FileReader(this.fileName));


            //first line
            String line = br.readLine();
            String[] tempFirst = line.split("\t|\\{|\\}",-1);
            List<Integer> list = new ArrayList<Integer>();

            for(String s : tempFirst) {
                if(s != null && s.length() > 0) {
                    list.add(Integer.parseInt(s));
                }
            }
            Integer[] listOfStates = (Integer[])list.toArray(new Integer[list.size()]);


            //second line
            String secLine = br.readLine();
            String[] listOfSymbols = secLine.split("\t");


            //third line
            String thirdLine = br.readLine();
            String[] tempThird = thirdLine.split("\\{|\\}",-1);
            List<Integer> list2 = new ArrayList<Integer>();

            for(String s : tempThird){
                if(s != null && s.length() > 0) {
                    list2.add(Integer.parseInt(s));
                }
            }
            Integer[] startState = (Integer[])list2.toArray(new Integer[list2.size()]);

            //fourth line
            String fourthLine = br.readLine();
            String[] tempFourth = fourthLine.split("\\{|\\}",-1);
            List<Integer> list3 = new ArrayList<Integer>();

            for(String s : tempFourth){
                if(s != null && s.length() > 0) {
                    list3.add(Integer.parseInt(s));
                }
            }
            //System.out.println("AA: "+ list3.toString());
            Integer[] setOfAcceptStates = (Integer[])list3.toArray(new Integer[list3.size()]);

            String fileLine = null;
            List<String> transitionTemp = new ArrayList<String>();
            while((fileLine = br.readLine()) != null){
                transitionTemp.add(fileLine);

            }

            String[] transitionFunction = (String[])transitionTemp.toArray(new String[transitionTemp.size()]);


            nfaContents = new NFA(listOfStates,listOfSymbols,startState,setOfAcceptStates,transitionFunction);






        }catch(FileNotFoundException e){
            throw e;
        }catch(IOException e){
            e.printStackTrace();
        }

        return nfaContents;
    }




}
