/**
 * @author Itay Bouganim, 305278384
 * @author Sahar Vaya , 205583453
 */
package Coefficients;

/*
 * A Scalar interface to determine operation valid for all implemented scalar classes.
 */
public interface Scalar<T> {
	
	//Type T determines a type which is the way to represent the instance of the scalar.
	public Scalar add(Scalar s); // Adds two scalar implemented types.
	public Scalar mul(Scalar s); // Multiplies two scalar implemented types.
	public Scalar pow(Scalar exponent); // Raises a scalar implemented type to the power of an exponent scalar.
	public Scalar neg(); // Multiplies a scalar implemented type by negative 1.
	public Scalar inv(); // Inverses a scalar implemented type (1/scalar).
	public boolean equals (Scalar s); // Returns true if one scalar type is equal to the input scalar type.
	public boolean isNegative(); // Returns true if the scalar value is a negative number.
	public T getValue(); // Returns the value of the scalar.
	public void setValue(T value); // Sets the value of the scalar.
	public Integer getIntegerValue(); // Returns the rounded integer value of the scalar.
	public void setIntegerValue(Integer value); // Sets integer value of the scalar.
	public String toString(boolean isExpZero); // returns a string representing the scalar type.
}
