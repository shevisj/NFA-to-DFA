package com.OGCoders;

/**
 * Created by Albert on 2/27/17.
 */
public class NFA {
    public Integer[] listOfStates;
    public String[] listOfSymbols;
    public Integer startState;
    public Integer[] setOfAcceptStates;
    public String[] transitions;

    NFA(Integer[] _listOfStates,String[] _listOfSymbols,Integer[] _startState,Integer[] _setOfAcceptStates,String[] _transitions) {
        this.listOfStates = _listOfStates;
        this.listOfSymbols = _listOfSymbols;
        this.startState = _startState[0];
        this.setOfAcceptStates = _setOfAcceptStates;
        this.transitions = _transitions;
    }
}
