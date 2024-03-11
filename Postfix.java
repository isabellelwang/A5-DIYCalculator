import java.io.StringReader;
import java.util.ArrayDeque;
import java.lang.Integer;
import java.util.*;

/**
 * Class to interpret and compute the result of arithmetic expressions
 * in POSTFIX format -
 */
public class Postfix {

  public static Double compute(String input) {
    ArrayDeque<Object> queue = Tokenizer.readTokens(input);
    int length = queue.size();
    ArrayDeque<Double> stack = new ArrayDeque<Double>();

    for (int i = 0; i < length; i++) {
      // System.out.println("Start: " + stack);
      // System.out.println(queue);
      Object queueValue = queue.getFirst();
      // System.out.println(queue.getFirst());

      if (queueValue instanceof Double) {
        stack.push((Double) queueValue);
        queue.removeFirst();
        // System.out.println("Add to stack: " + stack);
        // System.out.println("Remove from queue" + queue);
      } else if (queueValue instanceof Character) {
        Double num1 = stack.pop();
        Double num2 = stack.pop();

        if ((Character) queueValue == ('+')) {
          // System.out.println("add");
          stack.push(num1 + num2);
        } else if ((Character) queueValue == '-') {
          // System.out.println("sub");
          stack.push(num2 - num1);
        } else if ((Character) queueValue == '*') {
          // System.out.println("mult");
          stack.push(num1 * num2);
        } else if ((Character) queueValue == '/') {
          // System.out.println("div");
          stack.push(num2 / num1);
        }
        queue.removeFirst();
      }
    }

    return stack.pop();

  }

  /** Run short test */
  public static void main(String[] args) {
    //System.out.println("Answer: " + compute(" 3 2 + 5 *"));

    if (args.length == 0) {
      // If no arguments passed, print instructions
      System.err.println("Usage: java Postfix <expr>");
    } else {
      // Otherwise, echo what was read in for now
      //Scanner input = new Scanner(new StringReader(args[0]));
      System.out.println("Answer: " + compute(args[0]));
        // System.out.println(input.next());

    }
  }

}