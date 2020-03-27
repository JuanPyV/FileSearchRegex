public class Regex {

    public static RegexNFA Union(RegexNFA left, RegexNFA right) {
        // Create a new start and end state. The start state epsilons to the left
        // and right start states. Then epsilon left and right final states to new
        // final state.

    	RegexNFA union = new RegexNFA(new State(), new State());

        union.Start.EpsilonTransitions.add(left.Start);
        union.Start.EpsilonTransitions.add(right.Start);

        left.Final.EpsilonTransitions.add(union.Final);
        right.Final.EpsilonTransitions.add(union.Final);

        return union;
    }

    public static RegexNFA Star(RegexNFA nfa) {
        nfa.Final.EpsilonTransitions.add(nfa.Start);
        nfa.Final = nfa.Start;

        return nfa;
    }

    public static RegexNFA Concatenate(RegexNFA left, RegexNFA right) {
        // Create a new nfa that starts at left.start and ends at right.final.
        // Transition on epsilon between left.final and right.start.

    	RegexNFA concatenation = new RegexNFA(left.Start, right.Final);
        left.Final.EpsilonTransitions.add(right.Start);

        return concatenation;
    }

    public static RegexNFA Regex(String regex) {

        if (regex.length() == 0 || regex.equals("e")) {
            State state = new State();
            return new RegexNFA(state, state);
        }
        if (regex.length() == 1) {
            State left = new State();
            State right = new State();
            if (regex.equals("a")) {
                left.TransitionA = right;
            } else if (regex.equals("b")) {
                left.TransitionB = right;
            } else {
                throw new IllegalStateException("Invalid regex: " + regex);
            }
            return new RegexNFA(left, right);
        }

        String leftUnion = ParserHelper.StringBeforeSameLevelUnion(regex);
        if (leftUnion.length() != regex.length()) {
            String rightUnion = regex.substring(leftUnion.length() + 1);
            return Union(Regex(leftUnion), Regex(rightUnion));
        }

        // String is of the form "ab(..(...).)a*(...)*bb..."
        // IE, no unions to worry about.

        if (regex.length() > 1 && regex.charAt(1) == '*') {
        	RegexNFA left = Star(Regex(regex.substring(0, 1)));
            if (regex.length() == 2) return left;
            RegexNFA right = Regex(regex.substring(2));
            return Concatenate(left, right);
        }

        if (regex.charAt(0) != '(') {
            return Concatenate(Regex(regex.substring(0, 1)), Regex(regex.substring(1)));
        }

        String group = ParserHelper.MatchParentheses(regex);
        if (regex.length() > group.length() && regex.charAt(group.length()) == '*') {
        	RegexNFA left = Star(Regex(group.substring(1, group.length() - 1)));
            if (regex.length() == group.length() + 1) return left;
            RegexNFA right = Regex(regex.substring(group.length() + 1));
            return Concatenate(left, right);
        } else {
        	RegexNFA left = Regex(regex.substring(1, regex.length() - 1));
            if (regex.length() == group.length() + 2) return left;
            RegexNFA right = Regex(regex.substring((group.length() + 1)));
            return Concatenate(left, right);
        }
    }
}
