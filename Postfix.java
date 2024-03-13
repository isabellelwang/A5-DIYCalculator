import java.io.StringReader;
import java.util.ArrayDeque;
import java.lang.Integer;
import java.util.*;

/**
 * Class to interpret and compute the result of arithmetic expressions
 * in POSTFIX format -
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
    int length = queue.size();
    ArrayDeque<Double> stack = new ArrayDeque<Double>();

    for (int i = 0; i < length; i++) {
      Object queueValue = queue.getFirst();

      if (queueValue instanceof Double) {
        stack.push((Double) queueValue);
        queue.removeFirst();
      } else if (queueValue instanceof Character) {
        if (stack.size() < 2) {
          throw new RuntimeException("There are insufficient amount of numbers in the stack");
        }

        Double num1 = stack.pop();
        Double num2 = stack.pop();
        if ((Character) queueValue == ('+')) {
          stack.push(num1 + num2);
        } else if ((Character) queueValue == '-') {
          stack.push(num2 - num1);
        } else if ((Character) queueValue == '*') {
          stack.push(num1 * num2);
        } else if ((Character) queueValue == '/') {
          stack.push(num2 / num1);
        } else if ((Character) queueValue == '^') {
          stack.push(Math.pow(num1, num2));
        }
        queue.removeFirst();
      }
    }

    //If stack size still has items left in stack
    if (stack.size() > 1) {
      System.out.println(stack);
      throw new RuntimeException("There are excess numbers in the stack.");
    }
    return stack.pop();
  }

  /** Run short test */
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