package com.valtech.paperround

import static com.valtech.paperround.StreetPlan.StreetSide.NORTH
import static com.valtech.paperround.StreetPlan.StreetSide.SOUTH
import spock.lang.Specification
import spock.lang.Unroll


class StreetNumbersSpecification extends Specification {
	
	void "read street specification from a file without error"() {
		given:
		InputStream stream = ClassLoader.class.getResourceAsStream("/street1.txt")
		
		expect:
		StreetPlanFactory.createFromStream(stream)
	}
	
	void "read street specification with five houses"() {
		given: "a string containing a street specification"
		String streetSpec = "1 2 3 4 5"
		
		when: "parse street specification"
		StreetPlan streetPlan = StreetPlanFactory.create(streetSpec)
		
		then: "we have 5 houses"
		streetPlan.getNumberOfHouses() == 5
	}

	void "read street specification with three houses"() {
		given: "a string containing a street specification"
		String streetSpec = "1 2 3"
		
		when: "parse street specification"
		StreetPlan streetPlan = StreetPlanFactory.create(streetSpec)
		
		then: "we have 3 houses"
		streetPlan.getNumberOfHouses() == 3
	}
	
	void "street specification with invalid characters throws exception"() {
		given: "street specification with textual characters"
		String streetSpec = "1 two 3 four"
		
		when: "parse street specification"
		StreetPlan streetPlan = StreetPlanFactory.create(streetSpec)
		
		then:
		def exception = thrown(RuntimeException)
		exception.message == "Unable to parse house numbers. Unexcepted string: two"
	}
	
	@Unroll
	void "count #north houses on the north and #south on the south side with a street plan of \"#streetSpec\""() {
		when: "parse street specification of $streetSpec"
		StreetPlan streetPlan = StreetPlanFactory.create(streetSpec)
		
		then: "correct counts of north and south side numbers"
		streetPlan.countStreetSide(NORTH) == north
		streetPlan.countStreetSide(SOUTH) == south
		
		where:
		streetSpec  | north | south
		"1 2 3 4 5" | 3		| 2
		"1 3 2 4 5" | 3		| 2
		"2 3 4 5 6" | 2		| 3
	}
	
	@Unroll
	void "validating an invalid street spec with missing or skipped house numbers throws exception"() {
		given: "parsed street specification of $streetSpec"
		StreetPlan streetPlan = StreetPlanFactory.create(streetSpec)
		
		when: "parse street specification of $streetSpec"
		streetPlan.validate()
		
		then: "expect an error with messsage of $error"
		Throwable exception = thrown(RuntimeException)
		exception.message == error
		
		where:
		streetSpec | error
		"1 3 4 5 8" | "Failed to validate street specification. House number 4 is followed by 8"
		"1 3 3 5 2" | "Failed to validate street specification. House number 3 is followed by 3"
		"2 4 3 5 9" | "Failed to validate street specification. First entry is not house number one."
	}

} 