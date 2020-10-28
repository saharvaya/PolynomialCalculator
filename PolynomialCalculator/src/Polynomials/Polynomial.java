/**
 * @author Itay Bouganim, 305278384
 * @author Sahar Vaya , 205583453
 */
package Polynomials;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import Coefficients.Field;
import Coefficients.RationalScalar;
import Coefficients.Scalar;
import Logic.Calculator;

/*
 * Describes a polynomial expression.
 */
public class Polynomial {

	//Fields
	private List<PolyTerm> terms; // A list of all polynomial terms that consists the polynomial expression.
	
	//Constructor
	public Polynomial ()
	{
		this.terms= new LinkedList<PolyTerm>();
	}
	
	//Methods
	/*
	 * Adds input polynomial to current polynomial.
	 * @param poly A polynomial to perform the addition with.
	 * @return a polynomial which is the result of the addition.
	 */
	public Polynomial add(Polynomial poly)  {
		Collections.sort(this.terms);
		Polynomial output = new Polynomial();
		List<PolyTerm> termsToAdd = poly.getTerms();
		int inputLength = termsToAdd.size();
		int localLength = this.terms.size();
		int currInput = 0;
		int currLocal = 0;
		PolyTerm local = this.terms.get(currLocal);
		PolyTerm input = termsToAdd.get(currInput);
		while (currInput<inputLength && currLocal<localLength)
		{
			local = this.terms.get(currLocal);
			input = termsToAdd.get(currInput);
			int comparison = local.compareTo(input);
			if (comparison==0)
			{
				PolyTerm toAdd = new PolyTerm(input.getCoefficient().add(local.getCoefficient()), input.getExponent());
				output.addTerm(toAdd);
				currInput++;
				currLocal++;
			}
			else if (comparison>0)
			{
				output.addTerm(input);
				currInput++;
			}
			else if (comparison<0)
			{
				output.addTerm(local);
				currLocal++;
			}
		}
		while(currInput<inputLength)
		{
			input = termsToAdd.get(currInput);
			currInput++;
			output.addTerm(input);
		}
		while(currLocal<localLength)
		{
			local = this.terms.get(currLocal);
			currLocal++;
			output.addTerm(local);
		}
		return output;
	}
	
	/*
	 * Multiplies input polynomial and the current polynomial.
	 * @param poly A polynomial to perform multiplication with.
	 * @return A polynomial which is the result of the Multiplication
	 */
	public Polynomial mul(Polynomial poly) {
		Polynomial output = new Polynomial();
		for (PolyTerm term1 : this.terms)
		{
			Polynomial temp = new Polynomial();
			for(PolyTerm term2 : poly.getTerms())
			{
				temp.addTerm(term1.mul(term2));
			}
			if(output.getTerms().size()!=0)
				output = output.add(temp);
			else 
			{
				for (int i=0; i<temp.numberOfTerms(); i++)
				{
					output.getTerms().add(temp.getTerms().get(i));
				}
			}
		}
		return output;
	}
	
	/*
	 * Evaluates the current polynomial with an input scalar.
	 * @param scalar A Scalar to perform the evaluation with.
	 * @return a scalar which is the result of the evaluation.
	 */
	public Scalar evaluate(Scalar scalar) {
		Scalar sumEvaluation= Calculator.getScalarOverField();
		sumEvaluation.setIntegerValue(0);
		for (PolyTerm term : terms)
		{
			sumEvaluation.setValue(term.evaluate(scalar).add(sumEvaluation).getValue());
		}
		return sumEvaluation;
	}
	
	/*
	 * Derivates the current polynomial.
	 * @return A new polynomial which is the derivative of the current polynomial.
	 */
	public Polynomial derivate() {
		Polynomial output= new Polynomial();
		for (PolyTerm term : this.terms)
		{
			PolyTerm temp = term.derivate();
			output.addTerm(temp);
		}
		return output;
	}
	
	public boolean equals (Polynomial poly) {
		Collections.sort(this.terms);
		Collections.sort(poly.getTerms());
		for(int i=0 ; i<this.terms.size(); i++)
		{
			if (!(this.terms.get(i).equals(poly.getTerms().get(i))))
				return false;
		}
		return true;
	}

	public String toString() {
		Collections.sort(terms);
		if (this.terms.isEmpty())
			return "";
		Iterator<PolyTerm> iter = this.terms.iterator();
		String polynomial = iter.next().toString();
		while (iter.hasNext())
		{
			PolyTerm temp = iter.next();
			String term = temp.toString();
			if (term=="")
				polynomial+="";
			else if (temp.getCoefficient().isNegative() | polynomial.isEmpty())
				polynomial+=term;
			else polynomial+="+"+term;
		}
		if(polynomial.isEmpty())
			polynomial="0";
		return polynomial;
	}
	
	/*
	 * @return The size of the polynomial that is determined by the amount of polynomial terms that it consists.
	 */
	public int numberOfTerms () {
		return this.terms.size();
	}
	
	/*
	 * Adds a term to the polynomial list of terms.
	 * @param term A term to add to the current polynomial.
	 */
	public void addTerm (PolyTerm term) {
		this.terms.add(term);
	}
	
	//Getters and Setters
	public List<PolyTerm> getTerms() {
		Collections.sort(this.terms);
		return this.terms;
	}
}
