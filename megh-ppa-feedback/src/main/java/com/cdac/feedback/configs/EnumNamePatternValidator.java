package com.cdac.feedback.configs;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumNamePatternValidator implements ConstraintValidator<EnumNamePattern, String> {
	
    private List<String> valueList;

    @Override
    public void initialize(EnumNamePattern constraintAnnotation) {
        valueList = new ArrayList<String>();
        for(String val : constraintAnnotation.acceptedValues()) {
            valueList.add(val);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return valueList.contains(value);
    }
	}

