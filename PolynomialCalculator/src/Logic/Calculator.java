/**
 * @author Itay Bouganim, 305278384
 * @author Sahar Vaya , 205583453
 */
package Logic;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Coefficients.Field;
import Coefficients.RationalScalar;
import Coefficients.RealScalar;
import Coefficients.Scalar;
import Polynomials.*;
import Presentation.CLI;

public class Calculator {

	//Fields
	public static Character fieldRepresentation; // A character representation of the current field used by the calculator..
	private static StringAnalyzer analyzer; // A string analyzer used to parse user input strings.
	
	//Methods
	/*
	 * Main method.
	 */
	public static void main(String[] args) 
	{
		Calculator.Initiate();
		CLI.Initiate();
	}
	
	/*
	 * Initialize the calculator (sets current scalar field to null and constructs the string analyzer).
	 */
	public static void Initiate()
	{
		fieldRepresentation = null;
		analyzer = new StringAnalyzer();
	}
	
	/*
	 * Parses two polynomial strings to Polynomial expressions using the stringAnalyzer.
	 * @param term1 first string to parse to a polynomial and add.
	 * @param term2 second string to parse to a polynomial and add.
	 * @return A string representing the polynomial which is the result of the addition of the two input polynomials. 
	 */
	public static String addition(String term1, String term2)
	{	
		try 
		{
			Polynomial poly1 = analyzer.analyzeToPolynomial(term1);
			Polynomial poly2 = analyzer.analyzeToPolynomial(term2);
			Polynomial output = poly1.add(poly2);
			return output.toString();
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	/*
	 * Parses two polynomial strings to Polynomial expressions using the stringAnalyzer.
	 * @param term1 first string to parse to a polynomial and multiply. 
	 * @param term2 second string to parse to a polynomial and multiply.
	 * @return A string representing the polynomial which is the result of the multiplication of the two input polynomials. 
	 */
	public static String multiplication(String term1, String term2)
	{
		try 
		{
		Polynomial poly1 = analyzer.analyzeToPolynomial(term1);
		Polynomial poly2 = analyzer.analyzeToPolynomial(term2);
		Polynomial output = poly1.mul(poly2);
		return output.toString();
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	/*
	 * Parses a polynomial string and a scalar string to Polynomial expression and A Scalar type using the stringAnalyzer.
	 * @param term A string to parse to a polynomial and evaluate using the input string scalar. 
	 * @param stringScalar A string to parse to a Scalar and evaluate with.
	 * @return a string representing the scalar which is the result of the evaluation.
	 */
	public static String evaluation(String term, String stringScalar)
	{
		try 
		{
		Polynomial poly = analyzer.analyzeToPolynomial(term);
		Scalar scalar = analyzer.analyzeToScalar(stringScalar);
		Scalar output = poly.evaluate(scalar);
		if (output.getIntegerValue().equals(0))
			return "0";
		else return output.toString(true);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	/*
	 * Parses a polynomial string to Polynomial using the stringAnalyzer.
	 * @param term A string to parse to a polynomial and derivate.
	 * @return a string representing the Polynomial which is the result of the derivation of the input polynomial expression.
	 */
	public static String derivate(String term)
	{
		try 
		{
		Polynomial poly = analyzer.analyzeToPolynomial(term);
		Polynomial output = poly.derivate();
		return output.toString();
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	/*
	 * @return A new Scalar type depending on user choice of field.
	 */
	public static Scalar getScalarOverField ()
	{
		if (fieldRepresentation == 'R')
		{
			return new RealScalar();
		}
		if (fieldRepresentation == 'Q')
		{
			return new RationalScalar();
		}
		return null;
	}

	//Getters and Setters
	public static void setFieldRepresentation(Character representation) {
		fieldRepresentation = representation;
	}
}
