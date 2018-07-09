# NFA to DFA Converter
by Shevis Johnson

A simple java program that takes a Nondeterministic Finite Automaton (NFA) and converts it to an equivalent Deterministic Finite Automaton (DFA) if one exists.

## Sample nfa_example.nfa:
```
{1}	{2}	{3}
a	b
{2}
{3}
{1}, a  = {3}
{1}, b  = {2}
{2}, a = {1}
{2}, a = {2}
{2}, EPS = {3}
{3}, b = {1}
```
* Line 1 = list of states
* line 2 = list of symbols
* line 3 = start state
* line 4 = set of accept states
* line 5-EOF = transition function

A diagram of the state machine represented in the above example:
<img src="https://kraylus.cloud/public/NFA.png" alt="Example NFA" height="395" width="633">

Built and compiled in Java using IntelliJ
