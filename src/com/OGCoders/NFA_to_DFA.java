package com.OGCoders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;

/**
 * Created by Shevis Johnson on 2/27/17.
 */

public class NFA_to_DFA {

    public Integer[] nfaStates;
    public String[] alphabet;
    public Integer nfaStartState;
    public Integer[] nfaAcceptStates;
    public String[] nfaTransitions;

    public ArrayList<ArrayList<Integer>> dfaStates;
    public ArrayList<HashMap<String, String>> nfaTransitionsPrepd;
    public ArrayList<DFA_Transition> dfaTransitions = new ArrayList<DFA_Transition>();
    public ArrayList<Integer> dfaStartStates;
    public ArrayList<ArrayList<Integer>> dfaAcceptStates;

    public NFA_to_DFA(Integer[] _nfaStates, String[] _alphabet, Integer _nfaStartState, Integer[] _nfaAcceptStates, String[] _nfaTransitions) {
        this.nfaStates = _nfaStates;
        this.alphabet = _alphabet;
        this.nfaStartState = _nfaStartState;
        this.nfaAcceptStates = _nfaAcceptStates;
        this.nfaTransitions = _nfaTransitions;
    }

    public NFA_to_DFA(NFA input) {
        this.nfaStates = input.listOfStates;
        this.alphabet = input.listOfSymbols;
        this.nfaStartState = input.startState;
        this.nfaAcceptStates = input.setOfAcceptStates;
        this.nfaTransitions = input.transitions;
    }

    public NFA_to_DFA() {
        this.nfaStates = new Integer[] {};
        this.alphabet = new String[] {};
        this.nfaStartState = null;
        this.nfaAcceptStates = new Integer[] {};
        this.nfaTransitions = new String[] {};
    }

    public String convert() {
        StringBuilder sb = new StringBuilder();
        dfaStates = buildPowerSet(nfaStates);
        //System.out.println(dfaStates.toString());
        nfaTransitionsPrepd = prepareTransitions(nfaTransitions);
        dfaStartStates = epsilonClose(nfaStartState, nfaTransitionsPrepd);
        dfaTransitions = translateTransitions(nfaStartState, nfaTransitionsPrepd);
        dfaAcceptStates = getAcceptStates(dfaStates, nfaAcceptStates);

        sb.append(fomatArrayLong(dfaStates) + "\n");
        for (int i = 0; i < alphabet.length; ++i) {
            sb.append(alphabet[i] + " ");
        }
        sb.append("\n"+fomatArray(dfaStartStates));
        sb.append("\n"+fomatArrayLong(dfaAcceptStates));

        for (int j = 0; j < dfaTransitions.size(); ++j) {
            sb.append("\n"+dfaTransitions.get(j).toString());
        }

        return sb.toString();
    }

    private ArrayList<ArrayList<Integer>> buildPowerSet(Integer[] _nfaStates) {

        ArrayList<ArrayList<Integer>> output = new ArrayList<ArrayList<Integer>>();             // create empty return object

        for (int i = 1; i < Math.pow(2, _nfaStates.length); ++i) {             // iterate i over 1...2^(N)

            char[] binaryForm = Integer.toBinaryString(i).toCharArray();       // store binary string of i as char array

            ArrayList<Integer> subOutput = new ArrayList<Integer>();

            for (int j = 0; j < binaryForm.length; ++j) {                       // iterate through binaryForm,
                if (binaryForm[j] == '1') {                                     // use binary string as index map for creating power set
                    subOutput.add(_nfaStates[(_nfaStates.length - binaryForm.length) + j]);
                }
            }
            output.add(subOutput);                                              // add subset to final output
        }
        return output;
    }

    private ArrayList<HashMap<String, String>> prepareTransitions(String[] _transitions) {
        ArrayList<HashMap<String, String>> output = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < _transitions.length; ++i) {
            String curr = _transitions[i];

            String[] split = curr.split("\\s");
            String state = split[0].replaceAll("[^0-9]","");
            String value = split[1];
            String destination = split[3].replaceAll("[^0-9]","");
            HashMap<String, String> temp = new HashMap<String, String>(3);
            temp.put("state", state);
            temp.put("value", value);
            temp.put("destination", destination);
            output.add(temp);
            //System.out.print(state + ":");
            //System.out.print(value + ":");
            //System.out.print(destination + ":\n");
        }
        return output;
    }

    private ArrayList<Integer> epsilonClose(Integer _nfaState, ArrayList<HashMap<String, String>> _nfaTransitions) {
        ArrayList<Integer> output = new ArrayList<Integer>();
        output.add(_nfaState);
        for (int i = 0; i < _nfaTransitions.size(); ++i) {
            if (_nfaTransitions.get(i).get("value").equals("EPS")) {
                output.add(Integer.parseInt(_nfaTransitions.get(i).get("destination")));
            }
        }
        Integer[] stockArr = new Integer[output.size()];
        stockArr = output.toArray(stockArr);
        return output;
    }

    private ArrayList<DFA_Transition> translateTransitions(Integer _dfaStartState, ArrayList<HashMap<String, String>> _nfaTransitions) {
        ArrayList<ArrayList<Integer>> availableStates = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> _dfaStartStates = epsilonClose(_dfaStartState, _nfaTransitions);
        availableStates.add(_dfaStartStates);

        //System.out.println(_dfaStartState);
        //System.out.println(_dfaStartStates);
        ArrayList<DFA_Transition> output = new ArrayList<DFA_Transition>();


        while (availableStates.size() > 0) {
            ArrayList<Integer> currentStartState = availableStates.remove(0);

            for (int i = 0; i < alphabet.length; ++i) {
                ArrayList<Integer> newstate = newState(currentStartState, alphabet[i], _nfaTransitions);

                //for (int k = 0; k < newstate.size(); ++k) {
                //    System.out.println(Integer.toString(i)+": "+newstate.get(k).toString());
                //}

                DFA_Transition transition = new DFA_Transition(currentStartState, alphabet[i], newstate);

                //System.out.println("Q: "+transition.toString());

                if (newstate.size() > 0 && (!output.contains(transition))) {
                    //System.out.println("A: "+transition.toString());
                    boolean foundIt = false;
                    for (int j = 0; j < output.size(); ++j) {
                        if (output.get(j).states.equals(newstate)) {
                            foundIt = true;
                        }
                    }
                    if (!foundIt) {
                        availableStates.add(newstate);
                    }
                    output.add(transition);
                }
            }
        }

        return output;

    }

    private ArrayList<Integer> newState(ArrayList<Integer> current, String input, ArrayList<HashMap<String, String>> _nfaTransitions) {
        ArrayList<Integer> output = new ArrayList<Integer>();

        for (int i = 0; i < _nfaTransitions.size(); ++i) {
            for (int j = 0; j < current.size(); ++j) {
                if (_nfaTransitions.get(i).get("state").equals(Integer.toString(current.get(j))) && _nfaTransitions.get(i).get("value").equals(input)) {
                    output.addAll(epsilonClose(Integer.parseInt(_nfaTransitions.get(i).get("destination")), _nfaTransitions));
                }
            }
        }
        Set<Integer> hs = new HashSet<>();
        hs.addAll(output);
        output.clear();
        output.addAll(hs);
        return output;
    }

    private ArrayList<ArrayList<Integer>> getAcceptStates(ArrayList<ArrayList<Integer>> _dfaStates, Integer[] _nfaAcceptStates) {

        //for (int k = 0; k < _nfaAcceptStates.length; ++k) {
        //    System.out.println(_nfaAcceptStates[k].toString());
        //}
        ArrayList<ArrayList<Integer>> output = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < _dfaStates.size(); ++i) {
            for (int j = 0; j < _nfaAcceptStates.length; ++j) {
                if (_dfaStates.get(i).contains(_nfaAcceptStates[j])) {
                    output.add(_dfaStates.get(i));
                }
            }
        }
        return output;
    }

    private String fomatArray(ArrayList<Integer> input) {
        StringBuilder sb=new StringBuilder("{");
        int i;
        for (i = 0; i < input.size()-1; ++i) {
            sb.append(Integer.toString(input.get(i)) + ",");
        }
        sb.append(Integer.toString(input.get(i)) + "}");
        return sb.toString();
    }

    private String fomatArrayLong(ArrayList<ArrayList<Integer>> input) {
        StringBuilder sb=new StringBuilder("{");
            int i = 0;
            for (i = 0; i < input.size()-1; ++i) {
                //ArrayList<Integer> sublist =
                sb.append("{");
                for (int j = 0; j < input.get(i).size()-1; ++j) {
                    sb.append(input.get(i).get(j)+",");
                }
                sb.append(Integer.toString(input.get(i).get(input.get(i).size()-1)) + "}, ");
            }
            sb.append("{");
            for (int j = 0; j < input.get(input.size()-1).size()-1; ++j) {
                sb.append(input.get(input.size()-1).get(j)+",");
            }
            sb.append(Integer.toString(input.get(input.size()-1).get(input.get(input.size()-1).size()-1)) +"}}");


        return sb.toString();
    }

}
