import java.util.Arrays;
import java.util.ArrayDeque;

/**
 * Class to interpret and compute the result of arithmetic expressions
 * in INFIX format -
 */
public class Calculate {
  ArrayDeque<Object> stack = new ArrayDeque<Object>();
  // ArrayDeque<Object> queue = new ArrayDeque<Object>();

  public static Double calculate(String input) {
    ArrayDeque<Object> infix = Tokenizer.readTokens(input);
    int length = infix.size();

    ArrayDeque<Object> outputQueue = new ArrayDeque<>();
    ArrayDeque<Character> operatorStack = new ArrayDeque<>();

    for (int i = 0; i < length; i++) {
      Object firstObj = infix.getFirst();
      System.out.println("Output: " + outputQueue);
      System.out.println("Stack" + operatorStack);

      if (firstObj instanceof Double) {
        outputQueue.addLast((Double) firstObj);
        infix.removeFirst();
        // System.out.println(outputQueue);
      } else if (firstObj instanceof Character) {
        if ((Character) firstObj == '+' || (Character) firstObj == '-' || (Character) firstObj == '*'
            || (Character) firstObj == '/' || (Character) firstObj == '^') {
          System.out.println(firstObj);
          if (!operatorStack.isEmpty()) {
            System.out.println(operatorStack.peekFirst() != null);
            System.out.println((getPrecedence(operatorStack.peekFirst()))); // >= getPrecedence((Character) firstObj)));
            System.out.println(getPrecedence((Character) firstObj));

            while (operatorStack.peekFirst() != null
                && (getPrecedence(operatorStack.peekFirst()) >= getPrecedence((Character) firstObj))) {
              System.out.println("while");

              outputQueue.addLast(operatorStack.getFirst());
              System.out.println(outputQueue);
              operatorStack.removeFirst();
              System.out.println("removed");
              System.out.println(operatorStack);
              // operatorStack.getFirst();
            }
          }
          operatorStack.push((Character) firstObj);
          infix.removeFirst();
        } else if ((Character) firstObj == '(') {
          operatorStack.push((Character) firstObj);
          infix.removeFirst();
        } else if ((Character) firstObj == ')') {
          // System.out.println(firstObj);
          // System.out.println("stuck");
          // System.out.println(operatorStack);
          // System.out.println(operatorStack.getFirst());
          while (operatorStack.getFirst() != '(') {
            outputQueue.addLast(operatorStack.getFirst());
            operatorStack.removeFirst();
            // System.out.println(operatorStack);
            // System.out.println(outputQueue);
            // System.out.println(operatorStack);
          }
          infix.removeFirst();
          operatorStack.removeFirst();
          // System.out.println(operatorStack.removeFirst());
        }
      }
    }
    if (infix.size() == 0) {
      if (operatorStack.peekFirst() == '(' || operatorStack.peekFirst() == ')') {
        throw new RuntimeException("Mismatched Parenthesis");
      } else if (operatorStack.peekFirst() == '+' || operatorStack.peekFirst() == '-'
          || operatorStack.peekFirst() == '/' || operatorStack.peekFirst() == '*' || operatorStack.peekFirst() == '^') {
        outputQueue.addLast(operatorStack.pop());
      }
    }

    if (operatorStack.size() == 0 || infix.size() != 0) {
      throw new RuntimeException("Cannot compute.");
    } else {
      outputQueue.addLast(operatorStack.pop());

    }

    System.out.println("End stack:" + operatorStack);
    System.out.println("End Queue:" + outputQueue);
    System.out.println("end In:" + infix);

    Object postfix[] = outputQueue.toArray();
    // System.out.println("String:" + Arrays.toString(postfix));
    // System.out.println( Postfix.compute(toString(postfix)));
    return Postfix.compute(toString(postfix));

  }

  public static String toString(Object queue[]) {
    String input = "";

    for (Object values : queue) {
      input += values + " ";
    }
    return input;
  }

  public static int getPrecedence(Object operator) {
    int prec = 0;
    if ((Character) operator == '^') {
      prec = 4;
    } else if ((Character) operator == '*' || (Character) operator == '/') {
      prec = 3;
    } else if ((Character) operator == '+' || (Character) operator == '-') {
      prec = 2;
    }
    return prec;
  }

  /** Run short test */
  public static void main(String[] args) {

    System.out.println("Answer: " + Calculate.calculate("2 ^ 1 ^ 3"));

    // if (args.length == 0) {
    // // If no arguments passed, print instructions
    // System.err.println("Usage: java Calculate <expr>");
    // } else {
    // // Otherwise, echo what was read in for now
    // System.out.println("Answer: " + Calculate.calculate(args[0]));
    // }
  }

}