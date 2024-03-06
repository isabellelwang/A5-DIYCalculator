import java.util.Scanner;
import java.io.StringReader;
import java.util.ArrayDeque;

/** 
 * Class to interpret and compute the result of arithmetic expressions 
 * in INFIX format - 
 */
public class Calculate {

  /** Run short test */
  public static void main(String[] args) {

    if (args.length == 0) {
      // If no arguments passed, print instructions
      System.err.println("Usage:  java Calculate <expr>");
    } else {
      // Otherwise, echo what was read in for now
      Scanner input = new Scanner(new StringReader(args[0]));
      while (input.hasNext()) {
        System.out.println(input.next());
      }
    }
  }

}