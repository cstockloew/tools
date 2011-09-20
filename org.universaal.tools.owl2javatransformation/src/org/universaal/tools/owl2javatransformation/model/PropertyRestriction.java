package org.universaal.tools.owl2javatransformation.model;

public final class PropertyRestriction {
	
	private final OntologyProperty onProperty;
	private final int maxCard;
	private final int minCard;
	private final RestrictionType restrictionType;

	public PropertyRestriction(OntologyProperty onProperty, int maxCard, int minCard, RestrictionType restrictionType) {
		this.onProperty = onProperty;
		this.maxCard = maxCard;
		this.minCard = minCard;
		this.restrictionType = restrictionType;		
	}

	public OntologyProperty onProperty() {
		return onProperty;
	}

	public int getMaxCard() {
		return maxCard;
	}

	public int getMinCard() {
		return minCard;
	}

	public boolean isAllValuesFrom() {
		return restrictionType.equals(RestrictionType.ALL_VALUES_FROM);
	}


	/**
	 * currently not supported
	 */
	public boolean isSomeValuesFrom() {
		return restrictionType.equals(RestrictionType.SOME_VALUES_FROM);
	}
	

	/**
	 * currently not supported
	 */
	public boolean isJustCard() {
		return restrictionType.equals(RestrictionType.JUST_CARDINALITY);
	}

	

}
