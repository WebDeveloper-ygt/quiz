package com.quiz.api.jersey.model;

import java.util.List;

public class QuestionBean extends ExamBean {

	private int questionId;
	private String questionType;
	private String questionName;
	private List<QuestionOptionBean> options;

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

	public List<QuestionOptionBean> getOptions() {
		return options;
	}

	public void setOptions(List<QuestionOptionBean> options) {
		this.options = options;
	}

}
