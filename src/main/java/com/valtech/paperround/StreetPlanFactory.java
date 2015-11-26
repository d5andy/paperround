package com.valtech.paperround;

import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.ImmutableList.copyOf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

public class StreetPlanFactory {
	
	public static StreetPlan createFromStream(InputStream stream) {
		return create(readLineAndHandleExceptions(stream));
	}
	
	public static StreetPlan create(String streetSpec) {
		return new StreetPlan(parseStreetSpecifiction(streetSpec));
	}

	private static String readLineAndHandleExceptions(InputStream stream) {
		try {
			return readFirstLineFromInputStream(stream);
		} catch (IOException e) {
			throw new RuntimeException("Unable to process street specification file. Error: " + e.getMessage());
		}
	}

	private static String readFirstLineFromInputStream(InputStream stream) throws IOException {
		return new BufferedReader(new InputStreamReader(stream)).readLine();
	}
	
	private static ImmutableList<Integer> parseStreetSpecifiction(String streetSpec) {
		return copyOf(transformToNumbers(splitLine(streetSpec)));
	}

	private static ImmutableList<String> splitLine(String line) {
		return copyOf(line.split(" "));
	}

	private static Collection<Integer> transformToNumbers(ImmutableList<String> streetNumbers) {
		return transform(streetNumbers, convertStringToInteger());
	}

	private static Function<String, Integer> convertStringToInteger() {
		return new Function<String, Integer>() {
			@Override
			public Integer apply(String input) {
				try {
					return Integer.parseInt(input);
				} catch (NumberFormatException e) {
					throw new RuntimeException("Unable to parse house numbers. Unexcepted string: " + input);
				}
			
			}
		};
	}
}
