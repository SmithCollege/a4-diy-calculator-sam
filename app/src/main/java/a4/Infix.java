package a4;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class Infix {

    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        } else {
            return -1; // For unsupported operators, such as parentheses or invalid characters
        }
    }

    /**
     * turns an infix into a postfix by adding operators to a stack
     * and using the order of PEMDAS
     * if the token is a number, it will be added to the output queue,
     * if the token is an operator, and it has higher or equal precedence,
     * it will pop it to the output queue and will then push the current operator onto the stack
     * if the token is a left parenthesis, it will be pushed back to the stack
     * if the token is a right parenthesis, pop operators until a left parenthesis is found
     * if there are still tokens left and if the what is popped from the operator stack are parenthesies, 
     * then it will throw a runtime exception. when what is popped is an operator, then it will be popped onto the output queue.
     * @param tokens
     * @return 
     */

    public static Queue<Object> infixToPostfix(ArrayDeque<Object> tokens) {
        ArrayDeque<Object> tokenQueue = Tokenizer.readTokens(tokens); // Read from Tokenizer
        Queue<Object> outputQueue = new ArrayDeque<>();
        Stack<Character> operatorStack = new Stack<>();

        while (!tokenQueue.isEmpty()) {
            Object token = tokenQueue.poll();

            if (token instanceof Double) {
                outputQueue.offer(token);
            } else if (token instanceof Character) {
                char operator = (char) token;

                if (operator == '(') {
                    operatorStack.push(operator);
                } else if (operator == ')') {
                    while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                        outputQueue.offer(operatorStack.pop());
                    }
                    if (operatorStack.isEmpty()) {
                        throw new RuntimeException("Mismatched parentheses");
                    }
                    operatorStack.pop(); // Remove '('
                } else { // Operator case: +, -, *, /
                    while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(operator)) {
                        outputQueue.offer(operatorStack.pop());
                    }
                    operatorStack.push(operator);
                }
            }
        }

        // Pop remaining operators
        while (!operatorStack.isEmpty()) {
            char remaining = operatorStack.pop();
            if (remaining == '(' || remaining == ')') {
                throw new RuntimeException("Mismatched parentheses");
            }
            outputQueue.offer(remaining);
        }

        return outputQueue;
    }
}