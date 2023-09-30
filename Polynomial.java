import java.io.*;
import java.util.*;
class Polynomial {
	double[] coeff;
	int[] exp;

	public Polynomial(File f) throws IOException {
		
		Scanner input = new Scanner(f);
		String poly = input.next();
		
		int terms = poly.split("[-+]").length;
		if (poly.charAt(0) == '-') terms--;
		coeff = new double[terms];
		exp = new int[terms];
		
		char[] chars = poly.toCharArray();
		boolean isNegative = false;
		
		int index_coeff = 0;
		int index_exp = 0;
		int i = 0;
		while (i < chars.length) {
			if (chars[i] == '-') {
				isNegative = true;
			}
			
			if (Character.isDigit(chars[i])) {
				int j = i;
				String number = "";
				while (j < chars.length && chars[j] != 'x' && chars[j] != '+' && chars[j] != '-') {
					number += chars[j];
					j++;
				}
				
				if (chars[j] == '+' || chars[j] == '-') {
					exp[0] = 0;
					index_exp++;
				}
				
				double num = Double.parseDouble(number);
				
				if (isNegative) {
					coeff[index_coeff] = -1.0 * num;
					isNegative = false;
				} else {
					coeff[index_coeff] = num;
				}
				index_coeff++;
				i = j;
				continue;
			}
			
			if (chars[i] == 'x') {
				int j = i+1;
				String exponent = "";
				while (j < chars.length && chars[j] != '+' && chars[j] != '-') {
					exponent += chars[j];
					j++;
				}
				
				int exponent_num = Integer.parseInt(exponent);
				exp[index_exp] = exponent_num;
				index_exp++;
				i = j;
				continue;
			}
			
			i++;
		}
		
	}
	
	public Polynomial() {
		coeff = new double[1];
		exp = new int[1];
		coeff[0] = 0.0;
		exp[0] = 0;
	}
	
	public Polynomial(double[] input, int[] input_exp) {
		coeff = new double[input.length];
		coeff = input;
		exp = new int[input_exp.length];
		exp = input_exp;
	}
	
	
	public Polynomial add(Polynomial p) {
		
		// Determine resultant degree
		int degree = 0;
		
		for (int curr_exp: this.exp) {
			if (curr_exp > degree) {
				degree = curr_exp;
			}
		}
		
		for (int curr_exp: p.exp) {
			if (curr_exp > degree) {
				degree = curr_exp;
			}
		}
		
		// Add coefficents
		double[] outWithZeros = new double[degree + 1];
		for (int i = 0; i < this.coeff.length; i++) {
			outWithZeros[this.exp[i]] += this.coeff[i];
		}
		for (int i = 0; i < p.coeff.length; i++) {
			outWithZeros[p.exp[i]] += p.coeff[i];
		}
		
		// Determine length of resultant arrays, disregarding any zero coefficients
		int nonZeroTerms = 0;
		
		for (double single_coeff: outWithZeros) {
			if (single_coeff != 0.0) nonZeroTerms++;
		}
		
		double[] outCoeff = new double[nonZeroTerms];
		int[] outExp = new int[nonZeroTerms];
		
		int out_index = 0;
		for (int i = 0; i < outWithZeros.length; i++) {
			if (outWithZeros[i] != 0.0) {
				outCoeff[out_index] = outWithZeros[i];
				outExp[out_index] = i;
				out_index++;
			}
		}
		
		Polynomial pout = new Polynomial(outCoeff, outExp);
		return pout;
	}
	
	public Polynomial multiply(Polynomial p) {
		
		// Determine degree of resultant
		int degree1 = 0;
		for (int curr_exp: this.exp) {
			if (curr_exp > degree1) {
				degree1 = curr_exp;
			}
		}
		
		int degree2 = 0;
		for (int curr_exp: p.exp) {
			if (curr_exp > degree2) {
				degree2 = curr_exp;
			}
		}
		
		int degree = degree1+degree2;
		
		double outCoeffWithZeros[] = new double[degree+1];
		int outExpWithZeros[] = new int[degree+1];
		
		for (int i = 0; i < this.exp.length; i++) {
			for (int j = 0; j < p.exp.length; j++) {
				double coeffProd = this.coeff[i] * p.coeff[j];
				int resExp = this.exp[i] + p.exp[j];
				
				outCoeffWithZeros[resExp] += coeffProd;
				outExpWithZeros[resExp] = resExp;
			}
		}
		
		// Remove zeros
		int nonzeros = 0;
		for (double curr_coeff: outCoeffWithZeros) {
			if (curr_coeff != 0.0) nonzeros++;
		}
		
		double outCoeff[] = new double[nonzeros];
		int outExp[] = new int[nonzeros];
		
		int index = 0;
		for (int i = 0; i < outCoeffWithZeros.length; i++) {
			if (outCoeffWithZeros[i] != 0.0) {
				outCoeff[index] = outCoeffWithZeros[i];
				outExp[index] = outExpWithZeros[i];
				index++;
			}
		}
		
		Polynomial pout = new Polynomial(outCoeff, outExp);
		return pout;
		
	}
	
	public double evaluate(double arg) {
		double len = (double) this.coeff.length;
		double result = 0;
		for (int i = 0; i < len; i++) {
			result += this.coeff[i] *  Math.pow(arg, this.exp[i]);
		}
		return result;
	}
	
	public boolean hasRoot(double arg) {
		if (this.evaluate(arg) == 0.0) {
			return true;
		}
		return false;
	}
	
	public void saveToFile(String filename) throws IOException {
		FileWriter f = new FileWriter(filename, false);
		for (int i = 0; i < coeff.length; i++) {
			if (coeff[i] > 0 && i == 0) f.write(Double.toString(coeff[i]));
			else if (coeff[i] > 0) f.write("+" + Double.toString(coeff[i]));
			else if (coeff[i] < 0) f.write(Double.toString(coeff[i]));
			if (i != 0) f.write("x" + Integer.toString(exp[i]));
		}
		f.close();
	}
}