package com.quiz.api.jersey.model;

public class QuestionOptionBean extends QuestionBean{

	private String Option_A;
	private String Option_B;
	private String Option_C;
	private String Option_D;
	private String Option_Correct;
	
	public QuestionOptionBean() {
		super();
	}

	public String getOption_A() {
		return Option_A;
	}

	public void setOption_A(String option_A) {
		Option_A = option_A;
	}

	public String getOption_B() {
		return Option_B;
	}

	public void setOption_B(String option_B) {
		Option_B = option_B;
	}

	public String getOption_C() {
		return Option_C;
	}

	public void setOption_C(String option_C) {
		Option_C = option_C;
	}

	public String getOption_D() {
		return Option_D;
	}

	public void setOption_D(String option_D) {
		Option_D = option_D;
	}

	public String getOption_Correct() {
		return Option_Correct;
	}

	public void setOption_Correct(String option_Correct) {
		Option_Correct = option_Correct;
	}
	
	
}
