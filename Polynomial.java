class Polynomial {
	double[] coeff;

	public Polynomial() {
		coeff = new double[1];
		coeff[0] = 0.0;
	}
	
	public Polynomial(double[] input) {
		coeff = new double[input.length];
		coeff = input;
	}
	
	public Polynomial add(Polynomial p) {
		int len = Math.min(this.coeff.length, p.coeff.length);
		int max_len = Math.max(this.coeff.length, p.coeff.length);
		double[] out = new double[max_len];
		
		for (int i = 0; i < len; i++) {
			out[i] = p.coeff[i] + this.coeff[i];
		}
		
		if (this.coeff.length > p.coeff.length) {
			for (int i = len; i < max_len; i++) {
				out[i] = this.coeff[i];
			}
		} else if (this.coeff.length < p.coeff.length) {
			for (int i = len; i < max_len; i++) {
				out[i] = p.coeff[i];
			}
		}
		
		Polynomial pout = new Polynomial(out);
		return pout;
	}
	
	public double evaluate(double arg) {
		double len = (double) this.coeff.length;
		double result = 0;
		int index = 0;
		for (int i = 0; i < len; i++) {
			result += this.coeff[i] *  Math.pow(arg, i);
		}
		return result;
	}
	
	public boolean hasRoot(double arg) {
		if (this.evaluate(arg) == 0.0) {
			return true;
		}
		return false;
	}
}