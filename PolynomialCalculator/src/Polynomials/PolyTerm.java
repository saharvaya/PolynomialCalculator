/**
 * @author Itay Bouganim, 305278384
 * @author Sahar Vaya , 205583453
 */
package Polynomials;
import java.lang.*;
import Coefficients.*;
import Logic.Calculator;

/*
 * A class that describes a polynomial term which is part of a polynomial expression.
 */
public class PolyTerm implements Comparable<PolyTerm> {

	//Fields
	private Scalar coefficient; // The coefficient of the polynomial term.
	private Scalar exponent; // The exponent/power that the variable in the polynomial term is raised by.
	
	//Constructors
	public PolyTerm(Scalar coefficient, Scalar exponent)
	{
		this.coefficient=coefficient;
		this.exponent=exponent;
	}
	
	public PolyTerm(Scalar coefficient, int exponent)
	{
		this.coefficient=coefficient;
		Scalar scalarExponent = Calculator.getScalarOverField();
		scalarExponent.setIntegerValue(exponent);
		this.exponent = scalarExponent;
	}
	
	//Methods
	/*
	 * Check if the input PolyTerm can be added to the current PolyTerm by checking if they have the same power.
	 * @param term A PolyTerm to perform the check with.
	 * @return true if both terms can be added (same power), false otherwise.
	 */
	boolean canAdd(PolyTerm term)
	{
		return this.exponent.equals(term.getExponent());
	}
	
	/*
	 * Checks if addition between current PolyTerm and input PolyTerm can be performed.
	 * @param term A PolyTerm to try to perform the addition with.
	 * @return A PolyTerm which is the result of the addition if possible, if not returns null.
	 */
	PolyTerm add(PolyTerm term)
	{
		if(canAdd(term))
		{
			PolyTerm output = new PolyTerm(this.coefficient.add(term.getCoefficient()), this.exponent);
			return output;
		}
		return null;
	}
	
	/*
	 * Performs multiplication between current PolyTerm and input PolyTerm 
	 * @param term A PolyTerm to perform the multiplication with.
	 * @return a PolyTerm which is the result of the multiplication.
	 */
	PolyTerm mul(PolyTerm term)
	{
		PolyTerm output = new PolyTerm(this.coefficient.mul(term.getCoefficient()), this.exponent.add(term.getExponent()));
		return output;
	}
	
	/*
	 * Evaluates the current PolyTerm with an input scalar.
	 * @param scalar A Scalar to perform the evaluation with.
	 * @return a scalar which is the result of the evaluation.
	 */
	Scalar evaluate(Scalar scalar)
	{
		Scalar output = Calculator.getScalarOverField();
		output.setValue(scalar.pow(this.exponent).getValue());
		output.setValue(this.coefficient.mul(output).getValue());
		return output;
	}
	
	/*
	 * Derivates the current PolyTerm
	 * @return a new PolyTerm which is the derivative of the current PolyTerm.
	 */
	PolyTerm derivate()
	{
		PolyTerm output;
		Scalar newCoefficient = Calculator.getScalarOverField();
		newCoefficient.setValue((this.coefficient.mul(this.exponent)).getValue());
		if(!(this.exponent.getIntegerValue()==0))
		{
			int newExponent = this.exponent.getIntegerValue()-1;
			output = new PolyTerm(newCoefficient, newExponent);
		}
		else output = new PolyTerm(newCoefficient, 0);
		return output;
	}
	
	public boolean equals(PolyTerm pt)
	{
		return (this.exponent==pt.getExponent() && this.coefficient.equals(pt.getCoefficient()));
	}
	
	@Override
	public String toString()
	{
		if (this.coefficient.toString(false)==null)
			return "";
		else if (this.exponent.getIntegerValue()==0)
			return this.coefficient.toString(true);
		else if (this.exponent.getIntegerValue()==1)
			return this.coefficient.toString(false)+"x";
		else {
			return this.coefficient.toString(false)+"x^"+this.exponent.getIntegerValue();
			}
	}
	
	public int compareTo(PolyTerm term) {
		return this.exponent.getIntegerValue()-term.getExponent().getIntegerValue();
	}
	
	//Getters and Setters
	public Scalar getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Scalar coefficient) {
		this.coefficient = coefficient;
	}

	public Scalar getExponent() {
		return this.exponent;
	}

	public void setExponent(Scalar exponent) {
		this.exponent = exponent;
	}
}
