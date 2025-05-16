package a4;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.lang.Integer;
import java.util.*;

/**
 * Class to interpret and compute the result of arithmetic expressions
 */
public class Postfix {

  /**
   * Calculates the postfix and returns the answer
   * 
   * @param input String Postfix equation
   * @return Double Postfix calculated
   */
  public static Double compute(String input) {
    ArrayDeque<Object> queue = Tokenizer.readTokens(input);
    ArrayDeque<Double> stack = new ArrayDeque<Double>();
    int length = queue.size();

    for (int i = 0; i < length; i++) {
      Object value = queue.getFirst();

      if (value instanceof Double) {
        stack.push((Double) value);
        queue.removeFirst();
      } else if (value instanceof Character) {
        if (stack.size() < 2) {
          throw new RuntimeException("There are not enough numbers in the stack");
        }

        Double num = stack.pop();
        Double num2 = stack.pop();
        if ((Character) value == ('*')) {
          stack.push(num * num2);
        } else if ((Character) value == '/') {
          stack.push(num2 / num);
        } else if ((Character) value == '+') {
          stack.push(num + num2);
        } else if ((Character) value == '-') {
          stack.push(num2 - num);
        } else if ((Character) value == '^') {
          stack.push(Math.pow(num, num2));
        }
        queue.removeFirst();
      }
    }

    //when the stack does not have items left
    if (stack.size() > 1) {
      System.out.println(stack);
      throw new RuntimeException("There are too many numbers in the stack.");
    }
    return stack.pop();
  }


  public static void main(String[] args) {
    // System.out.println("Answer: " + compute("3 2 + 5 *"));
    if (args.length == 0) {
      // If no arguments passed, print instructions
      System.err.println("Usage: java Postfix <expr>");
    } else {
      System.out.println("Answer: " + compute(args[0]));

    }
  }

}