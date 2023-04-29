import java.util.ArrayDeque;

public class MidtermExamPostfix {
    public static void main(String[] args) {
        String infix = "a*b-c";
        String postfix = infixToPostfix(infix);

        System.out.println("Infix: " + infix);
        System.out.println("Postfix: " + postfix);
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

    public static String infixToPostfix(String infix) {
        ArrayDeque<Character> operatorStack = new ArrayDeque<Character>();
        StringBuilder postfix = new StringBuilder();
        
        for (int i = 0; i < infix.length(); i++) {
            char currentCharacter = infix.toCharArray()[i];
            
            if (Character.isLetterOrDigit(currentCharacter)) 
                postfix.append(currentCharacter);
            
            else if (currentCharacter == '(') 
                operatorStack.push(currentCharacter);
                
            else if (currentCharacter == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(')
                    postfix.append(operatorStack.pop());
                    
                operatorStack.pop();
            } 
            else {
                while (!operatorStack.isEmpty() && getPrecedence(currentCharacter) <= getPrecedence(operatorStack.peek()))
                    postfix.append(operatorStack.pop());

                operatorStack.push(currentCharacter);
            }
        }
        
        while (!operatorStack.isEmpty())
            postfix.append(operatorStack.pop());
        
        return postfix.toString();
    }
}
