package com.valtech.paperround

import static com.google.common.collect.ImmutableList.copyOf
import spock.lang.Specification
import spock.lang.Unroll

class PaperRoundSpecfication extends Specification {
	
	@Unroll
	void "deliver the papers to all the houses in the correct order"() {
		given: "a street plan"
		PaperRound paperRound = createPaperRound(houseNumbers)
		
		expect:
		List<Integer> orderedHouseNumbers = paperRound.getOrderedHouseNumbers(deliveryStrategy)
		orderedHouseNumbers.containsAll(houseNumbers)
		orderedHouseNumbers == expectedOrder
		
		where:
		deliveryStrategy 			| houseNumbers		| expectedOrder   
		new PaperRound.OneWay()		| [1, 3, 2, 5, 4]	| [1, 3, 2, 5, 4]
		new PaperRound.TwoWay()		| [1, 3, 2, 5, 4]	| [1, 3, 5, 4, 2]
	}
	
	@Unroll
	void "count the number of times required to cross the road for a strategy"() {
		given: "a street plan"
		PaperRound paperRound = createPaperRound(houseNumbers)
		
		expect:
		paperRound.getRoadCrossingCount(deliveryStrategy) == expectedCount
		
		where:
		deliveryStrategy 			| houseNumbers		| expectedCount
		new PaperRound.OneWay()		| [1, 3, 2, 5, 4]	| 3
		new PaperRound.TwoWay()		| [1, 3, 2, 5, 4]	| 1
	}
	
	private PaperRound createPaperRound(List<Integer> houseNumbers) {
		new PaperRound(new StreetPlan(copyOf(houseNumbers)))
	}
}
