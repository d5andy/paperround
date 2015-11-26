package com.valtech.paperround;

import static com.google.common.collect.Iterables.concat;
import static com.valtech.paperround.StreetPlan.StreetSide.NORTH;
import static com.valtech.paperround.StreetPlan.StreetSide.SOUTH;

import java.util.List;

import com.google.common.collect.ImmutableList;


public class PaperRound {
	
	public static interface DeliveryStrategy {
		List<Integer> getOrderedHouseNumbers(StreetPlan streetPlan);
		Integer getRoadCrossingCount(StreetPlan streetPlan);
	}
	
	private final StreetPlan streetPlan;

	public PaperRound(StreetPlan streetPlan) {
		this.streetPlan = streetPlan;
	}

    public List<Integer> getOrderedHouseNumbers(DeliveryStrategy deliveryStrategy) {
    	return deliveryStrategy.getOrderedHouseNumbers(streetPlan);
    }
    
    public Integer getRoadCrossingCount(DeliveryStrategy deliveryStrategy) {
    	return deliveryStrategy.getRoadCrossingCount(streetPlan);
    }
    
    public static class OneWay implements DeliveryStrategy {
		@Override
		public List<Integer> getOrderedHouseNumbers(StreetPlan streetPlan) {
			return streetPlan.getHouseNumbers();
		}

		@Override
		public Integer getRoadCrossingCount(StreetPlan streetPlan) {
			Integer count = 0;
			boolean onTheNorthSide = true;
			for (Integer next : streetPlan.getHouseNumbers()) {
				boolean nextOnTheNorthSide = NORTH.apply(next);
				if (onTheNorthSide == nextOnTheNorthSide) {
					onTheNorthSide = nextOnTheNorthSide;
					count++;
				}
			}
			return count;
		}
		
    }
    
    public static class TwoWay implements DeliveryStrategy {
		@Override
		public List<Integer> getOrderedHouseNumbers(StreetPlan streetPlan) {
			return ImmutableList.copyOf(
					concat(streetPlan.getHouseNumbers(NORTH), 
							streetPlan.getHouseNumbers(SOUTH).reverse()));
		}

		@Override
		public Integer getRoadCrossingCount(StreetPlan streetPlan) {
			return 1;
		}
    }

}
