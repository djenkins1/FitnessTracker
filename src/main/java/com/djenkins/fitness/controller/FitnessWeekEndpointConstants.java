package com.djenkins.fitness.controller;

public class FitnessWeekEndpointConstants {
	public static final String GET_ALL = "/rest/fitnessWeeks";
	
	public static final String GET_IN_RANGE = "/rest/fitnessWeeks/between";
	
	public static final String GET_BY_IDS = "/rest/fitnessWeeks/ids";
	
	public static final String GET_BY_EXERCISE_TYPE = "/rest/fitnessWeeks/exerciseTypes";
	
	public static final String GET_WEEK = "/rest/fitnessWeek/{id}";
	
	public static final String CREATE_WEEK = "/rest/fitnessWeeks";
	
	public static final String SUM_ALL = "/rest/fitnessWeeks/sum";
	
	public static final String SUM_IN_RANGE = "/rest/fitnessWeeks/sum/between";
	
	public static final String SUM_BY_IDS = "/rest/fitnessWeeks/sum/ids";
	
	public static final String SUM_BY_EXERCISE_TYPES = "/rest/fitnessWeeks/sum/exerciseTypes";	
	
	public static final String SUM_MONTHLY = "/rest/fitnessWeeks/sums";
}
