/**
 * @author Itay Bouganim, 305278384
 * @author Sahar Vaya , 205583453
 */
package Coefficients;

/*
 * A class representing a scalar over the reals field, implements the Scalar interface
 */
public class RealScalar implements Scalar<Double> {

	//Fields
	private Double value; // The decimal numeric value of the real scalar.
	
	//Constructors
	public RealScalar (double decimalValue)
	{	
		this.value=decimalValue;
	}
	
	public RealScalar ()
	{	
		this.value=null;
	}
	
	//Methods
	/*
	 * Adds a real scalar to the current scalar.
	 * @param scalar A Scalar type to add to the current scalar.
	 * @return A scalar which is the result of the addition.
	 */
	public Scalar add(Scalar scalar) {
		RealScalar output = new RealScalar (this.value+((RealScalar) scalar).getValue());
		return output;
	}

	/*
	 * Multiplies a real scalar to the current scalar.
	 * @param scalar A Scalar type to multiply with the current scalar.
	 * @return A scalar which is the result of the multiplication.
	 */
	public Scalar mul(Scalar scalar) {
		RealScalar output = new RealScalar (this.value*((RealScalar) scalar).getValue());
		return output;
	}
	
	/*
	 * Raises current real scalar to the power of the input scalar.
	 * @param power A Scalar type representing the power to raise current scalar to.
	 * @return A Scalar type which is the result of raising the current scalar to the parameter power.
	 */
	public Scalar pow(Scalar power) {
		int n = power.getIntegerValue();
		if (n<0)
			throw new IllegalArgumentException ("Can not raise to a negative exponent");
		RealScalar output;
		if(n==0) {
			output = new RealScalar (1); }
		else if (n==1) {
			output=this; }
		else
		{
			output = this;
			while (n>1)
			{
				output = (RealScalar) mul(output);
				n--;
			}
		}
		return output;
	}

	/*
	 * Negates the value of the current real scalar.
	 * @return A Scalar which is the result of multiplying the current scalar by -1.
	 */
	public Scalar neg() {
		return mul(new RealScalar(-1));
	}

	/*
	 * Inverses the current real scalar.
	 * @return A Scalar which is the result of dividing 1 by the current scalar.
	 */
	public Scalar inv() {
		RealScalar output = new RealScalar (1/this.value);
		return output;
	}

	@Override
	public boolean equals(Scalar scalar) {
		return (this.value == ((RealScalar) scalar).getValue());
	}

	public String toString(boolean isExponentZero)
	{
		String value;
		//Checks if can be presented as a natural number.
		if(this.value % 1 == 0) 
		{
			int naturalNumber = this.value.intValue();
			if(naturalNumber==0)
				return "";
			else if(naturalNumber==1 & !isExponentZero)
				return "";
			else if(naturalNumber==-1 & !isExponentZero)
				return "-";
			else return naturalNumber+"";
		}
		else 
		{
			String checkDecimal = Double.toString(Math.abs(this.value));
			int integerPlaces = checkDecimal.indexOf('.');
			int decimalPlaces = checkDecimal.length() - integerPlaces - 1;
			//Check if decimal value has more than 3 digits after decimal point to shorten presented value.
			if(decimalPlaces>=3)
				value = String.format("%.3f", this.value);	
			else value = this.value+"";
			return value;
		}
	}
	
	/*
	 * Checks if the current scalar is negative.
	 * @return true if scalar has negative value, false otherwise.
	 */
	public boolean isNegative()
	{
		return this.value<0;
	}

	//Getters and Setters
	public Double getValue() {
		return this.value;
	}
	
	public void setValue(Double value) {
		this.value = value;
	}

	public Integer getIntegerValue() {
		return value.intValue();
	}

	public void setIntegerValue(Integer value) {
		this.value = value.doubleValue();
	}
}
