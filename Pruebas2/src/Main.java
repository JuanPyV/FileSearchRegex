import java.util.Scanner;

public class Main {

    public static void main(String args[]) {

        Scanner input = new Scanner(System.in);

        RegexNFA regex = Regex.Regex(input.nextLine());
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (regex.Accept(line)) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }
        }
        /*
        RegexNFA a = Regex.Regex("a");
        System.out.println(a.Accept("b"));
        System.out.println(a.Accept("bb"));
        System.out.println(a.Accept("bbe"));
        System.out.println(a.Accept("eb"));
        System.out.println(a.Accept("ebb"));
        System.out.println(a.Accept("ebeb"));
        */
    }
}
