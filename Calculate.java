import java.util.Arrays;
import java.util.ArrayDeque;

/**
 * Class to interpret and compute the result of arithmetic expressions
 * in INFIX format -
 */
public class Calculate {

  /**
   * Converts infix into postfix then calculate the postfix
   * 
   * @param input String infix equation
   * @return Double computed answer
   */
  public static Double calculate(String input) {
    ArrayDeque<Object> infix = Tokenizer.readTokens(input);
    int length = infix.size();

    ArrayDeque<Object> outputQueue = new ArrayDeque<>();
    ArrayDeque<Character> operatorStack = new ArrayDeque<>();

    for (int i = 0; i < length; i++) {
      Object firstObj = infix.getFirst();

      // if item is double
      if (firstObj instanceof Double) {
        outputQueue.addLast((Double) firstObj);
        infix.removeFirst();
      } else if (firstObj instanceof Character) {
        if ((Character) firstObj == '+' || (Character) firstObj == '-' || (Character) firstObj == '*'
            || (Character) firstObj == '/') {
          if (!operatorStack.isEmpty()) {
            // check precedence
            while (operatorStack.peekFirst() != null
                && (getPrecedence(operatorStack.peekFirst()) >= getPrecedence((Character) firstObj))) {
              outputQueue.addLast(operatorStack.getFirst());
              operatorStack.removeFirst();
            }
          }
          operatorStack.push((Character) firstObj);
          infix.removeFirst();
        } else if ((Character) firstObj == '(') {
          operatorStack.push((Character) firstObj);
          infix.removeFirst();
        } else if ((Character) firstObj == ')') {
          while (operatorStack.getFirst() != '(') {
            outputQueue.addLast(operatorStack.getFirst());
            operatorStack.removeFirst();
            // check mismatch parenthesis
            if (operatorStack.isEmpty()) {
              throw new RuntimeException("Mismatch Parenthesis");
            }
          }
          infix.removeFirst();
          operatorStack.removeFirst();
        }
      }
    }

    // Error handling
    if (!operatorStack.isEmpty() && infix.isEmpty()) {
      if (operatorStack.size() >= 1) {
        if (operatorStack.peekFirst() == '+' || operatorStack.peekFirst() == '-'
            || operatorStack.peekFirst() == '/' || operatorStack.peekFirst() == '*') {
          while (!operatorStack.isEmpty()) {
            outputQueue.addLast(operatorStack.pop());
          }
        }
      }
      if (operatorStack.size() == 1) {
        // compare mismatch ()
        if (operatorStack.peekFirst() == '(' || operatorStack.peekFirst() == ')') {
          throw new RuntimeException("Mismatched Parenthesis");
        }
      }
    }

    // change postfix to array, then change to String
    Object postfix[] = outputQueue.toArray();
    return Postfix.compute(toString(postfix));
  }

  /**
   * Converts array into a String
   * 
   * @param queue queue Object turned into String
   * @return String equation
   */
  public static String toString(Object queue[]) {
    String input = "";

    for (Object values : queue) {
      input += values + " ";
    }
    return input;
  }

  /**
   * returns Precedence of operators
   * 
   * @param operator Character operator
   * @return int precedence level of the operator
   */
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

    // System.out.println("Answer: " + Calculate.calculate("3 + 2 * 5 "));

    if (args.length == 0) {
      // If no arguments passed, print instructions
      System.err.println("Usage: java Calculate <expr>");
    } else {
      // Otherwise, echo what was read in for now
      try {
        System.out.println("Answer: " + Calculate.calculate(args[0]));
      } catch (Exception e) {
        System.err.println("Error. Cannot compute.");
      }
    }
  }

}