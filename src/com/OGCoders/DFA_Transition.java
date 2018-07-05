package com.OGCoders;

import java.util.ArrayList;

/**
 * Created by Shevis Johnson on 2/28/17.
 */
public class DFA_Transition {
    public ArrayList<Integer> states;
    public String value;
    public ArrayList<Integer> destinations;

    public DFA_Transition(ArrayList<Integer> _states, String _value, ArrayList<Integer> _destinations) {
        this.states = _states;
        this.value = _value;
        this.destinations = _destinations;
        //for (int k = 0; k < _destinations.size(); ++k) {
        //    System.out.println(Integer.toString(k)+" inside: "+_destinations.get(k).toString());
        //}
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder("{");
        int i;
        for (i = 0; i < states.size()-1; ++i) {
            sb.append(Integer.toString(states.get(i)) + ",");
        }
        sb.append(Integer.toString(states.get(i)) + "}, ");

        sb.append(value+" = ");

        int j;
        sb.append("{");
        for (j = 0; j < destinations.size()-1; ++j) {
            sb.append(Integer.toString(destinations.get(j)) + ",");
        }
        sb.append(Integer.toString(destinations.get(j)) + "}");

        //now original string is changed
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!DFA_Transition.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final DFA_Transition other = (DFA_Transition) obj;

        return this.toString().equals(other.toString());
    }
}

