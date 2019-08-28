package com.quiz.api.jersey.model;

public class QuestionBean {

	private int questionId;
	private String questionType;
	private String questionName;
	private QuestionOptionBean options;

	public QuestionBean() {
		super();
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public QuestionOptionBean getOptions() {
		return options;
	}

	public void setOptions(QuestionOptionBean options) {
		this.options = options;
	}

}
