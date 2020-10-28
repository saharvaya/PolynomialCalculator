/**
 * @author Itay Bouganim, 305278384
 * @author Sahar Vaya , 205583453
 */
package Coefficients;

import java.math.BigDecimal;
import java.math.BigInteger;

/*
 * A class representing a scalar over the rational field, implements the Scalar interface
 */
public class RationalScalar implements Scalar<String> {

	//Fields
	private Integer numerator; // An integer representation of the numerator part of the fraction.
	private Integer denominator; // An integer representation of the denominator part of the fraction.
	private Double decimalValue; //A numeric decimal value of the division (numerator/denominator)
	private boolean reduced; // Determines whether the fraction reduced already.
	private boolean isNatural; // Determines whether the fraction represents a natural number (numerator/1)

	//Constructors
	public RationalScalar (int numerator, int denominator)
	{	
		if(denominator==0)
			throw new IllegalArgumentException ("Division by zero is prohibited!");
		this.numerator=numerator;
		this.denominator=denominator;
		if(CheckNatural())
		{
			this.numerator=toNatural();
			this.denominator= 1;
			this.isNatural=true;
			this.reduced = true;
		}
		else 
		{
			this.isNatural=false;
		}
		this.reduced=false;
		this.decimalValue =(double) this.numerator/this.denominator;
	}
	
	public RationalScalar (int numerator)
	{
		this.reduced=true;
		this.numerator=numerator;
		this.denominator= 1;
		this.isNatural=true;
		this.decimalValue=(double) this.numerator;
	}
	
	public RationalScalar ()
	{
		this.reduced=false;
		this.numerator=null;
		this.denominator=null;
		this.isNatural=false;
	}
	
	//Methods
	/*
	 * Adds a rational scalar to the current scalar.
	 * @param scalar A Scalar type to add to the current scalar.
	 * @return A scalar which is the result of the addition.
	 */
	public Scalar add(Scalar scalar) {
		RationalScalar output;
		int otherDenominator = ((RationalScalar) scalar).getDenominator();
		int otherNumerator = ((RationalScalar) scalar).getNumerator();
		if(otherDenominator == this.denominator) {
			output = new RationalScalar (otherNumerator+this.numerator, this.denominator); }
		else
		{
			int newNumerator = (otherDenominator*this.numerator)+(this.denominator*otherNumerator);
			int newDenominator = (this.denominator*otherDenominator);
			output = new RationalScalar (newNumerator, newDenominator);
		}
		return output;
	}

	/*
	 * Multiplies a real scalar to the current scalar.
	 * @param scalar A Scalar type to multiply with the current scalar.
	 * @return A scalar which is the result of the multiplication.
	 */
	public Scalar mul(Scalar scalar) {
		int otherDenominator = ((RationalScalar) scalar).getDenominator();
		int otherNumerator = ((RationalScalar) scalar).getNumerator();
		RationalScalar output = new RationalScalar (this.numerator*otherNumerator, this.denominator*otherDenominator);
		return output;
	}
	
	/*
	 * Raises current rational scalar to the power of the input scalar.
	 * @param power A Scalar type representing the power to raise current scalar to.
	 * @return A Scalar type which is the result of raising the current scalar to the parameter power.
	 */
	public Scalar pow(Scalar power) {
		int n = power.getIntegerValue();
		if (n<0)
			throw new IllegalArgumentException ("Can not raise to a negative exponent");
		RationalScalar output;
		if(n==0) {
			output = new RationalScalar (1); }
		else if (n==1) {
			output=this; }
		else
		{
			output = this;
			while (n>1)
			{
				output = (RationalScalar) mul(output);
				n--;
			}
		}
		return output;
	}

	/*
	 * Negates the value of the current rational scalar.
	 * @return A Scalar which is the result of multiplying the current scalar by -1.
	 */
	public Scalar neg() {
		return mul(new RationalScalar(-1));
	}

	/*
	 * Inverses the current rational scalar.
	 * @return A Scalar which is the result of dividing 1 by the current scalar.
	 */
	public Scalar inv() {
		if(isNatural) {
			return new RationalScalar(1,this.numerator); }
		else {
			return new RationalScalar(this.denominator,this.numerator); }
	}

	@Override
	public boolean equals(Scalar scalar) {
		return (this.decimalValue == ((RationalScalar) scalar).getDecimalValue());
	}
	
	private int gcd(int a, int b) {
	    BigInteger b1 = BigInteger.valueOf(a);
	    BigInteger b2 = BigInteger.valueOf(b);
	    BigInteger gcd = b1.gcd(b2);
	    return gcd.intValue();
	}
	
	/*
	 * Checks if the fraction can be reduce using gcd function.
	 * If it can be reduced, reduces the fraction parts.
	 */
	private void fractionReduction()
	{	
		if(!reduced)
		{
			int gcd = gcd(this.numerator, this.denominator);
			this.numerator=this.numerator/gcd;
			this.denominator=this.denominator/gcd;
			this.reduced=true;
		}
	}
	
	/*
	 * Receives a double value and convert it to a rational scalar parameters.
	 * Sets the numerator and denominator fields according to the conversion.
	 * @param value A double decimal value to convert to numerator and denominator parts.
	 */
	public void valueToRational(Double value){  
		String valueString = value+"";
		String[] parts = null;
		BigDecimal number = new BigDecimal(valueString);
		parts = number.toString().split("\\.");
		BigDecimal denominator = BigDecimal.TEN.pow(parts[1].length());
		BigDecimal numerator = (new BigDecimal(parts[0]).multiply(denominator))
				.add(new BigDecimal(parts[1]));
		this.numerator = numerator.intValue();
		this.denominator = denominator.intValue();
	}   
	
	public String toString(boolean isExponentZero)
	{
		fractionReduction();
		//Checks if can be presented as a natural number.
		if(this.denominator==1)
		{
			if(this.numerator==0)
				return null;
			else if(this.numerator==1 & !isExponentZero)
				return "";
			else if(this.numerator==-1 & !isExponentZero)
				return "-";
			else return this.numerator+"";
		}
		else return this.numerator+"/"+this.denominator;
	}
	
	/*
	 * Checks if the current rational scalar is a natural number.
	 * return true if current scalar is natural, else otherwise.
	 */
	private boolean CheckNatural()
	{
		return this.numerator%this.denominator == 0;
	}
	
	/*
	 * Convert the rational scalar to natural (denominator 1).
	 * @return An integer type which is the natural number representing the current scalar.
	 */
	private Integer toNatural()
	{
		return this.numerator/this.denominator;
	}
	
	/*
	 * Checks if the current scalar is negative.
	 * @return true if scalar has negative value, false otherwise.
	 */
	public boolean isNegative()
	{
		if(this.numerator<=0)
		{
			if(this.denominator<0)
				return false;
			else return true;			
		}
		else if(this.denominator<0)
			return true;
		return false;
	}
	
	//Getters and Setters
	public Integer getNumerator() {
		return this.numerator;
	}

	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}

	public Integer getDenominator() {
		return this.denominator;
	}

	public void setDenominator(Integer denominator) {
		this.denominator = denominator;
	}
	
	public boolean isNatural() {
		return this.isNatural;
	}

	
	public Double getDecimalValue() {
		return decimalValue;
	}

	public String getValue() {
		return this.numerator+"/"+this.denominator;
	}

	public void setValue(String value) {
		String[] fraction = new String[2];
		//Separates the string representing the current rational scalar value to numerator and denominator.
		if(value.contains("/")) 
		{
			fraction = value.split("/", 2);
			this.numerator = Integer.parseInt(fraction[0]);
			this.denominator = Integer.parseInt(fraction[1]);
			try 
			{
				if (denominator==0)
					throw new IllegalArgumentException("Divison by zero is prohibited! shame on you!");
			}
			catch (IllegalArgumentException e)
			{
				System.out.print("\n"+e.getMessage());
			}
			fractionReduction();
			this.decimalValue = (double)this.numerator/(double)this.denominator;
		}
		else if (value.contains("."))
		{
			this.decimalValue=Double.parseDouble(value);
			valueToRational(this.decimalValue);
		}
		else 
		{
			this.numerator = Integer.parseInt(value);
			this.denominator = 1;		
			this.decimalValue = (double)this.numerator/(double)this.denominator;
		}
	}

	public Integer getIntegerValue() {
		return decimalValue.intValue();
	}
	
	public void setIntegerValue(Integer value) {
		this.decimalValue = value.doubleValue();
		this.numerator = value;
		this.denominator = 1;
	}
}
