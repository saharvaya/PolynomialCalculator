/**
 * @author Itay Bouganim, 305278384
 * @author Sahar Vaya , 205583453
 */
package Logic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Coefficients.*;
import Polynomials.*;

/*
 * A class that is responsible for parsing a user input polynomial string.
 * Will return a valid polynomial expression decomposed to its factors in order to perform operations.
 */
public class StringAnalyzer<T> {
	
	//Fields
	private Map<String, T> polynomialComponents; //  A map that matches a String described exponent to a Type T coefficient value.
	
	//Constructor
	public StringAnalyzer()
	{
		this.polynomialComponents=new HashMap<String, T>();
	}
	
	//Methods
	/*
	 * Parses a string type input scalar.
	 * @return A Scalar type scalar according to the field the end-user chose to work over.
	 */
	public Scalar analyzeToScalar (String scalar)
	{
		polynomialComponents = stringToComponents(scalar);
		Scalar output = Calculator.getScalarOverField();
		T value = this.polynomialComponents.get("0");
		output.setValue(value);
		return output;
	}
	
	/*
	 * Parses a string type input polynomial, decomposes it to the polynomial terms.
	 * @return a Polynomial type expression according to the field the end-user chose to work over.
	 */
	public Polynomial analyzeToPolynomial (String polynomial)
	{
		polynomialComponents = stringToComponents(polynomial);
		Polynomial output = new Polynomial();
		for (Map.Entry<String, T> component : polynomialComponents.entrySet()) 
		{
			int exponentValue = Integer.parseInt(component.getKey());
			Scalar coefficient = Calculator.getScalarOverField();
			Scalar exponent = Calculator.getScalarOverField();
			coefficient.setValue(component.getValue());
			exponent.setIntegerValue(exponentValue);
			PolyTerm term = new PolyTerm(coefficient, exponent);
			output.addTerm(term);
		}
		return output;
	}
	
	/*
	 * Analyzes the input string to its components.
	 * Splits the string do separate variable input and differentiate between exponents and coefficients.
	 * @param poly A string representing a polynomial expression to analyze to components.
	 * @return A map that matches exponents to the correct coefficients.
	 */
	private Map<String, T> stringToComponents (String poly)
	{
		//Splits different polynomial terms.
		String[] terms = poly.replace(" ", "").split("(?=\\+|\\-)");
		for(int i=0; i<terms.length ; i++)
		{
			String temp = terms[i].replace("+", "");
			terms[i]=temp;
		}
		Map<String, T> termMap = new HashMap<>();
		for (String term : terms)
		{
			String[] splitTerm=null;
			//Splits in each term the coefficient and the exponent.
			splitTerm = term.split("x", 2);
			if(term.contains("X"))
				splitTerm = term.split("X", 2);
		    String exponent="0";
		    if (splitTerm.length > 1)
		    {
		    	//Splits exponents.
		        String stringExponent = splitTerm[1].replace("^", "");
		        exponent = stringExponent.isEmpty() ? "1" : stringExponent;
		    }
		    String coefficient = (splitTerm[0].isEmpty()) ? "1" : ("-".equals(splitTerm[0])) ? "-1" : splitTerm[0];
		    classifyCoefficients (termMap, coefficient , exponent);
		}
		return termMap;
	}
	
	/*
	 * Classifies coefficients to the correct field that is in current use.
	 * Adds to the terms map the exponent that match the each coefficient.
	 * Checks the map to see if a key of the current exponent already exists.
	 * If current exponents already exists in the terms map, adds common power coefficients to a single term.
	 * @param termMap A map of exponents and coefficients to add the coefficient to the corresponding exponent.
	 * @param coefficient A string coefficient to add to the terms map.
	 * @param exponent A string representing the exponent that matches the input coefficient.
	 */
	private void classifyCoefficients (Map termMap, String coefficient , String exponent)
	{
		if (Calculator.fieldRepresentation.equals('R'))
		{
			Double coefficientValue = Double.parseDouble(coefficient);
			//Check if exponent already exists to add term coefficients.
		    if(termMap.containsKey(exponent))
		    {
		    	termMap.replace(exponent, (Double)termMap.get(exponent)+coefficientValue);
		    }
		    else termMap.put(exponent, Double.valueOf(coefficientValue));
		}
		if (Calculator.fieldRepresentation.equals('Q'))
		{
			String coefficientValue = coefficient;
			//Check if exponent already exists to add term coefficients.
		    if(termMap.containsKey(exponent))
		    {
		    	Scalar s1 = new RationalScalar();
		    	Scalar s2 = new RationalScalar();
		    	s1.setValue((String)termMap.get(exponent));
		    	s2.setValue(coefficientValue);
		    	Scalar added = new RationalScalar();
		    	added = s1.add(s2);
		    	termMap.replace(exponent, added.getValue());
		    }
		    else termMap.put(exponent, coefficientValue);
		}
	}
}
