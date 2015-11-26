package com.valtech.paperround;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.ImmutableList.copyOf;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

public class StreetPlan {
	
	private ImmutableList<Integer> houseNumbers;
	
	public static enum StreetSide implements Predicate<Integer> {
		NORTH(1), SOUTH(0);
		
		private final int oddOrEven;
		
		private StreetSide(int oddOrEven) {
			this.oddOrEven = oddOrEven;
		}
		
		@Override
		public boolean apply(Integer input) {
			if (input % 2 == this.oddOrEven) {
				return true;
			}
			return false;	
		}
	};
		
	public StreetPlan(ImmutableList<Integer> houseNumbers) {
		this.houseNumbers = houseNumbers;
	}
	
	public ImmutableList<Integer> getHouseNumbers() {
		return houseNumbers;
	}
	
	public ImmutableList<Integer> getHouseNumbers(StreetSide side) {
		return copyOf(filter(houseNumbers, side));
	}

    public int getNumberOfHouses() {
        return houseNumbers.size();
    }
    
    public int countStreetSide(StreetSide side) {
    	return getHouseNumbers(side).size();
    }
    
    public void validate() {
    	if (houseNumbers.size() == 0) {
    		throw new RuntimeException("Failed to validate street specification. No house numbers.");
    	} else {
    		if (houseNumbers.get(0) != 1) {
    			throw new RuntimeException("Failed to validate street specification. First entry is not house number one.");
    		}
    	}
    	
    	for (StreetSide side : StreetSide.values()) {
    		FluentIterable<Integer> iterable = from(houseNumbers).filter(side);
    		Optional<Integer> first = iterable.first();
    		if (first.isPresent()) {
    			Integer curr = first.get();
    			for (Integer next : iterable.skip(1).toImmutableList()) {
    				if (next != (curr + 2)) {
    					throw new RuntimeException("Failed to validate street specification. House number " + curr + " is followed by " + next);
    				}
    				curr = next;
    			}
    		}	
    	}
    }

}
