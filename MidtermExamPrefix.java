import java.util.ArrayDeque;

public class MidtermExamPrefix {
    public static void main(String[] args) {
        String infix = "a*b-c";
        String prefix = infixToPrefix(infix);

        System.out.println("Infix: " + infix);
        System.out.println("Prefix: " + prefix);
    }

    public static int getPrecedence(char operator) {
        if (operator == '+' || operator == '-')
            return 1;
        else if (operator == '*' || operator == '/')
            return 2;
        else if (operator == '^')
            return 3;
        else 
            return 0;
    }

    public static String infixToPrefix(String infix) {
        String reversed = new StringBuilder(infix).reverse().toString();
        
        ArrayDeque<Character> operatorStack = new ArrayDeque<Character>();
        StringBuilder prefix = new StringBuilder();
        
        for (int i = 0; i < reversed.length(); i++) {
            char currentCharacter = reversed.toCharArray()[i];
            
            if (Character.isLetterOrDigit(currentCharacter))
                prefix.append(currentCharacter);

            else if (currentCharacter == '(')
                operatorStack.push(currentCharacter);

            else if (currentCharacter == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(')
                    prefix.append(operatorStack.pop());

                operatorStack.pop();
            }
            else {
                while (!operatorStack.isEmpty() && getPrecedence(currentCharacter) <= getPrecedence(operatorStack.peek()))
                    prefix.append(operatorStack.pop());
                
                operatorStack.push(currentCharacter);
            }
        }
        
        while (!operatorStack.isEmpty())
            prefix.append(operatorStack.pop());
        
        return prefix.reverse().toString();
    }
}