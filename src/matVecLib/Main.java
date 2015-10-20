package matVecLib;

/**
 * Created by roberto on 08/10/15.
 */
public class Main {

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {

        Normal3 n = new Normal3(1,2,3);

        System.out.println(n.mul(0.5));

    }
}
