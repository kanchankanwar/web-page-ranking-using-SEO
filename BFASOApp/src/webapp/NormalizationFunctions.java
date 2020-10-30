package webapp;

import java.util.*;
public class NormalizationFunctions {

	/*
	 * Normalize values
	 * 
	 * @param value 	value
	 * @param max		max value
	 * @param min		min value
	 * @return percentage
	 */
	public static double percentage(final double value,final double max,final double min)
	{
		return (value - min) / (max - min);
	}
	
	/*
	 * Normalize a value based on a sigmoid function
	 * 
	 * @param value to normalize
	 * @return value between 0 to 1
	 */
	public static double sigmoid(final double value)
	{
		return (1 / (1 + Math.pow(Math.E, (-1 * value))));
	}
	
	/*
	 * Normalize the doubles of the input map values.
	 * Translated from programming collective intelligence
	 * @param input input map
	 * @param smallBetter if a smaller value is preferred
	 * @return input map with normalized values
	 */
	public static Map<String, Double> normalizeMap(final Map<String,Double> input, final boolean smallerIsBetter)
	{
		final Map<String, Double> returnMap = new HashMap<>();
		
		double vsmall = 0.00001;
		System.out.println("In normalize map");
		if(smallerIsBetter)
		{
			double minScore = Collections.min(input.values());
			for(final Map.Entry<String, Double> entry : input.entrySet())
			{
				final double normed = minScore / Math.max(vsmall, entry.getValue());
				returnMap.put(entry.getKey(), normed);
			}
		}
		else {
			double maxScore = Collections.max(input.values());
			if(maxScore == 0)
				maxScore = vsmall;
			for(final Map.Entry<String, Double> entry : input.entrySet())
			{
				final double normed = entry.getValue() / maxScore;
				returnMap.put(entry.getKey(), normed);
			}
		}
		return returnMap;
	}
}
