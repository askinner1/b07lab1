import java.io.*;
public class Driver {
	public static void main(String [] args) throws IOException {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1_co = {6,5};
		int[] c1_exp = {0, 3};
		Polynomial p1 = new Polynomial(c1_co, c1_exp);
		double [] c2_co = {-2, -9};
		int[] c2_exp = {1, 4};
		Polynomial p2 = new Polynomial(c2_co, c2_exp);
		Polynomial s = p1.add(p2);
		
		for (double c: s.coeff) {
			System.out.print(c + ", ");
		}
		System.out.println();
		
		for (int e: s.exp) {
			System.out.print(e + ", ");
		}
		System.out.println();
		
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		if(s.hasRoot(1)) {
				System.out.println("1 is a root of s");
		}
		else {
				System.out.println("1 is not a root of s");
		}
		
		Polynomial m = p1.multiply(p2);
		System.out.println("m(0.1) = " + m.evaluate(0.1));
		
		m.saveToFile("polyout.txt");
	}
}