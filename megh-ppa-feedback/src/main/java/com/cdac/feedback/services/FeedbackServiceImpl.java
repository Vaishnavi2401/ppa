package com.cdac.feedback.services;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.feedback.dto.CustomResponeMasterDto;
import com.cdac.feedback.dto.FeedbackMasterDto;
import com.cdac.feedback.dto.FeedbackQuestionMapDto;
import com.cdac.feedback.dto.OptionCount;
import com.cdac.feedback.dto.QuestionMasterDto;
import com.cdac.feedback.dto.ResponesMasterDto;
import com.cdac.feedback.dto.ResponseCount;
import com.cdac.feedback.dto.TypeMasterDto;
import com.cdac.feedback.models.FeedbackMaster;
import com.cdac.feedback.models.FeedbackQuestionMap;
import com.cdac.feedback.models.FeedbackQuestionMapPK;
import com.cdac.feedback.models.OptionsMaster;
import com.cdac.feedback.models.QuestionMaster;
import com.cdac.feedback.models.ResponseMaster;
import com.cdac.feedback.models.ResponseMasterPK;
import com.cdac.feedback.models.TypeMaster;
import com.cdac.feedback.repositories.FeedbackMasterRepo;
import com.cdac.feedback.repositories.FeedbackQuestionMapRepo;
import com.cdac.feedback.repositories.OptionMasterRepo;
import com.cdac.feedback.repositories.QuestionMasterRepo;
import com.cdac.feedback.repositories.ResponseMasterRepo;
import com.cdac.feedback.repositories.TypeMasterRepo;

@Service
public class FeedbackServiceImpl implements FeedbackServices {

	@Autowired
	FeedbackMasterRepo feedbackMasterRepo;

	@Autowired
	FeedbackQuestionMapRepo feedbackQuestionMapRepo;

	@Autowired
	OptionMasterRepo optionMasterRepo;

	@Autowired
	QuestionMasterRepo questionMasterRepo;

	@Autowired
	ResponseMasterRepo responseMasterRepo;

	@Autowired
	TypeMasterRepo typeMasterRepo;

	

	Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

	@Override
	public Optional<TypeMaster> getTypeById(int id) {

		return typeMasterRepo.findById(id);
	}

	@Override
	public TypeMaster addTypeMaster(TypeMasterDto typeMasterDto) {

		ModelMapper modelMapper = new ModelMapper();
		TypeMaster typeMaster = new TypeMaster();
		typeMaster.setUpdatedOn(new Date());
		modelMapper.map(typeMasterDto, typeMaster);

		return typeMasterRepo.save(typeMaster);
	}

	@Override
	public TypeMaster updateTypeMaster(TypeMasterDto typeMasterDTO) {

		ModelMapper modelMapper = new ModelMapper();
		TypeMaster typeMaster = new TypeMaster();
		typeMaster.setUpdatedOn(new Date());
		modelMapper.map(typeMasterDTO, typeMaster);

		return typeMasterRepo.save(typeMaster);
	}

	@Override
	public List<TypeMaster> getAllTypeMasterDetails() {

		return typeMasterRepo.findAll();
	}

	@Override
	public Boolean getTypeByName(String typeTitle) {

		return typeMasterRepo.findByTypeTitleIgnoreCase(typeTitle) != null;

	}

	@Override
	public void deleteTypeById(int typeId) {

		typeMasterRepo.deleteById(typeId);

	}

	@Override
	@Transactional
	public QuestionMaster addQuestion(QuestionMasterDto questionMasterDto) {

		ModelMapper modelMapper = new ModelMapper();
		QuestionMaster questionMaster = new QuestionMaster();
		questionMaster.setUpdatedOn(new Date());
		modelMapper.map(questionMasterDto, questionMaster);

		questionMaster = questionMasterRepo.save(questionMaster);

		// If the type of the question is either Single Choice (SC) or Multiple Choice
		// (MC) then store options in OptionMaster table

		if (questionMaster.getQuestionType().equalsIgnoreCase("SC")
				|| questionMaster.getQuestionType().equalsIgnoreCase("MC")) {

			for (String option : questionMasterDto.getOptions()) {

				OptionsMaster optionMaster = new OptionsMaster();
				optionMaster.setOptionText(option);
				optionMaster.setQuestionMaster(questionMaster);

				optionMasterRepo.save(optionMaster);
			}

		}
		return questionMaster;

	}

	@Override
	@Transactional
	public QuestionMaster updateQuestion(QuestionMasterDto questionMasterDto) {

		ModelMapper modelMapper = new ModelMapper();
		QuestionMaster questionMaster = new QuestionMaster();
		questionMaster.setUpdatedOn(new Date());
		modelMapper.map(questionMasterDto, questionMaster);

		questionMaster = questionMasterRepo.save(questionMaster);

		// If the type of the question is either Single Choice (SC) or Multiple Choice
		// (MC) then store options in OptionMaster table

		if (questionMaster.getQuestionType().equalsIgnoreCase("SC")
				|| questionMaster.getQuestionType().equalsIgnoreCase("MC")) {

			// deleting already existing options for this particular question

			optionMasterRepo.deleteByQuestionMaster(questionMaster);

			for (String option : questionMasterDto.getOptions()) {

				OptionsMaster optionMaster = new OptionsMaster();
				optionMaster.setOptionText(option);
				optionMaster.setQuestionMaster(questionMaster);

				optionMasterRepo.save(optionMaster);
			}

		}
		return questionMaster;
	}

	@Override
	public List<QuestionMaster> getQuestionByIds(List<Integer> questionIds) {

		return questionMasterRepo.findByQuestionIdIn(questionIds);

	}


	@Override
	@Transactional
	public void deleteQuestionById(int questionId) {
		QuestionMaster questionMaster = new QuestionMaster();
		questionMaster.setQuestionId(questionId);

		// first removing its options then removing the question
		optionMasterRepo.deleteByQuestionMaster(questionMaster);
		questionMasterRepo.deleteById(questionId);

	}

	@Override
	public void addResponses(List<ResponesMasterDto> responseMasters) {

		for (ResponesMasterDto responseMasterDto : responseMasters) {

			
			
			// Currently the feedback Id is fetched from provided typeId and Id..  instead change the json format so that typeId and 
			//Id comes only once in the ResponseMasterDto
			

				ModelMapper modelMapper = new ModelMapper();
				ResponseMaster responseMaster = new ResponseMaster();
			
				
				Optional <FeedbackMaster> feedbackMaster = getFeedBackByTypeAndId(responseMasterDto.getTypeId(), responseMasterDto.getId());
				
				
				ResponseMasterPK responseMasterPk = new ResponseMasterPK();
				responseMasterPk.setQuestionId(responseMasterDto.getQuestionId());
				responseMasterPk.setFeedbackBy(responseMasterDto.getFeedbackBy());
				responseMasterPk.setFeedbackId(feedbackMaster.get().getFeedbackId());
				responseMasterPk.setFeedbackDate(new Date());
				
				responseMaster.setId(responseMasterPk);
				modelMapper.map(responseMasterDto, responseMaster);
				responseMasterRepo.save(responseMaster);

			
		}

	}

	@Override
	public List<CustomResponeMasterDto> findByFeedbackId(int feedbackId) {
		
		
		
		List<String> userList = responseMasterRepo.getAttemptedUserListByFeedbackId(feedbackId);
		List<CustomResponeMasterDto> responseMaster = new ArrayList<CustomResponeMasterDto>();
		CustomResponeMasterDto customResponse;
		
		for(String user: userList) {
			
			customResponse = new CustomResponeMasterDto();
			List<ResponseMaster> rep = responseMasterRepo.findByIdFeedbackIdAndIdFeedbackBy(feedbackId, user);
			
			customResponse.setResponseMaster(rep);
			customResponse.setUserId(user);
			
			responseMaster.add(customResponse);
			
		}
		
		
		
		
		return responseMaster;
	}

	@Override
	public List<ResponseMaster> findByFeedbackIdAndFeedbackBy(int feedbackId, String feedbackBy) {

		return responseMasterRepo.findByIdFeedbackIdAndIdFeedbackBy(feedbackId, feedbackBy);
	}

	@Override
	public FeedbackMaster addFeeback(FeedbackMasterDto feedbackMasterDto) {
		
		ModelMapper modelMapper = new ModelMapper();
		FeedbackMaster feedbackMaster = new FeedbackMaster();
		feedbackMaster.setUpdatedOn(new Date());
		
		modelMapper.map(feedbackMasterDto, feedbackMaster);
		return feedbackMasterRepo.save(feedbackMaster);
	}

	@Override
	public FeedbackMaster updateFeeback(FeedbackMasterDto feedbackMasterDTO) {
		
		
		ModelMapper modelMapper = new ModelMapper();
		FeedbackMaster feedbackMaster = new FeedbackMaster();
		feedbackMaster.setUpdatedOn(new Date());
		
		modelMapper.map(feedbackMasterDTO, feedbackMaster);
		return feedbackMasterRepo.save(feedbackMaster);
	}

	@Override
	public Optional<FeedbackMaster> getFeedBackById(int feedbackId) {
		
		return feedbackMasterRepo.findById(feedbackId);
	}
	
	@Override
	public Optional<FeedbackMaster> getFeedBackByTypeAndId(int typeId ,int feedbackId) {
		
		TypeMaster typeMaster = new TypeMaster();
		typeMaster.setTypeMasterId(typeId);
		
		return feedbackMasterRepo.findByIdAndTypeMaster(feedbackId,typeMaster);
	}

	@Override
	public List<FeedbackMaster> getAllFeedbackDetails() {
		return feedbackMasterRepo.findAll();
	}

	@Override
	@Transactional
	public void deleteFeedback(int feedbackId) {
		
	
		feedbackMasterRepo.deleteById(feedbackId);		
	}

	@Override
	@Transactional
	public void addFeedbackMap(FeedbackQuestionMapDto feedbackQuestionMapDto) {
		
	

	for(Integer questionId : feedbackQuestionMapDto.getQuestionId()) {
		
		FeedbackQuestionMap feedbackQuestionMap = new FeedbackQuestionMap();
		FeedbackQuestionMapPK feedbackQuestionMapPK = new FeedbackQuestionMapPK();
		
		feedbackQuestionMapPK.setFeedbackId(feedbackQuestionMapDto.getFeedbackId());
		feedbackQuestionMapPK.setQuestionId(questionId);
		
		feedbackQuestionMap.setId(feedbackQuestionMapPK);
		feedbackQuestionMapRepo.save(feedbackQuestionMap);
	}

	
		
	}

	@Override
	@Transactional
	public void updateFeedbackMap(FeedbackQuestionMapDto feedbackQuestionMapDto) {


		//delete existing mappings
		deleteMapByFeedbackId(feedbackQuestionMapDto.getFeedbackId());
		
		//add mappings
		addFeedbackMap(feedbackQuestionMapDto);
	}

	@Override
	public void deleteMapByFeedbackId(int feedbackId) {
		
		List<FeedbackQuestionMap> feedbackQuestionMaps = feedbackQuestionMapRepo.findByIdFeedbackId(feedbackId);
		feedbackQuestionMapRepo.deleteAll(feedbackQuestionMaps);
		
	}

	@Override
	public List<FeedbackQuestionMap> getQuestionsByFeedbackId(int feedbackId) {
		
		return feedbackQuestionMapRepo.findByIdFeedbackId(feedbackId);
	}

	@Override
	public void addFeedabackAndQuestion(QuestionMasterDto questionMasterDto) {
		
		// create feedback if not created
		
		FeedbackMaster  feedback;
		
		
		//temp 
		
		int typeMaster=2;
		
		if(questionMasterDto.getCourseId()>0) {
			typeMaster=1;
		}
		
		Optional<FeedbackMaster> feedbackMaster = getFeedBackByTypeAndId(typeMaster, questionMasterDto.getCourseId());
		
		if(!feedbackMaster.isPresent()) {
			
			FeedbackMasterDto feedbackMasterDto = new FeedbackMasterDto();
			feedbackMasterDto.setDescription("NA");
			//feedbackMasterDto.setFeedbackId(questionMasterDto.getCourseId());
			feedbackMasterDto.setId(questionMasterDto.getTenantId());
			feedbackMasterDto.setFeedbackTitle(Integer.toString(questionMasterDto.getCourseId()));
			feedbackMasterDto.setTypeMasterId(typeMaster);
			feedbackMasterDto.setUpdatedBy(questionMasterDto.getUpdatedBy());
			  feedback= addFeeback(feedbackMasterDto);
		}else {
			
			feedback = feedbackMaster.get();
		}
		
		
		

		
		//add questions 
		
		QuestionMaster questionMaster =  addQuestion(questionMasterDto);
		
		// map questions
		
		
		FeedbackQuestionMapDto feedbackQuestionMapDto = new FeedbackQuestionMapDto();
		
		feedbackQuestionMapDto.setFeedbackId(feedback.getFeedbackId());
		List<Integer> quesList = new ArrayList();
		quesList.add(questionMaster.getQuestionId());
		feedbackQuestionMapDto.setQuestionId(quesList);
		
		addFeedbackMap( feedbackQuestionMapDto);
		
	}

	@Override
	public List<QuestionMaster> getAllQuestionDetails() {
		return questionMasterRepo.findAll();
	}
	

	@Override
	public List<QuestionMaster> getQuestionByTypeId(int typeId) {
		return questionMasterRepo.findByTypeId(typeId);
	}
	
	@Override
	public List<QuestionMaster> getQuestionByTypeIdAndUpdatedBy(int typeId, String updatedBy) {
		return questionMasterRepo.findByTypeIdAndUpdatedBy(typeId, updatedBy);
	}
	
	@Override
	public List<ResponseCount> getFeedBackSummary(int type_id, int Id) {

		//List<ResponseCount> responseCount = new ArrayList<ResponseCount>();
		List<ResponseCount> responseCount = new ArrayList<ResponseCount>();
		
		try {

			List<Integer[]> qid = responseMasterRepo.getResponseQuestionIdByCourseAndType(Id, type_id);

			for (Integer[] queId : qid) {
				// System.out.println(queId[0]);
				List<QuestionMaster> QM = questionMasterRepo.findByQId(queId[0]);
				ResponseCount rc = new ResponseCount();
				QM.forEach(e -> {

					rc.setQuestion_id(e.getQuestionId());
					rc.setQuestion(e.getQuestion());
					rc.setMandatory(e.getMandatory());
					rc.setQuestionType(e.getQuestionType());
					// List<OptionCount> loc= new ArrayList<>();
					String queType = e.getQuestionType();
					System.out.println("QuestionMap-----" + e.getFeedbackQuestionMaps());
					if (queType.equals("TF")) {
						List<Object[]> respMast = responseMasterRepo.getResponseCountByQuestionId(queId[0]);
						List<OptionCount> loc = new ArrayList<>();
						for (Object[] rm : respMast) {
							OptionCount oc = new OptionCount();
							System.out.println(rm[0] + " " + rm[1] + " " + rm[2]);
							oc.setOption_text((String) rm[1]);
							oc.setCount((Object) rm[0]);
							loc.add(oc);
							// rc.setOptionCount((List<OptionCount>) oc);
						}
						rc.setOptionCount(loc);
					}
					if (queType.equals("SC")) {
						List<Object[]> respMast = responseMasterRepo.getResponseCountByQuestionId(queId[0]);
						// List<Object[]> OM = optionMasterRepo.getOptionMaster(queId[0]);
						List<OptionsMaster> OM = e.getOptionsMasters();
						System.out.println("========= " + OM.size());
						List<OptionCount> loc = new ArrayList<>();

						for (OptionsMaster om : OM) {
							OptionCount oc = new OptionCount();
							oc.setOption_id(om.getOptionId());
							oc.setOption_text(om.getOptionText());
							oc.setCount(0);

							for (Object[] rm : respMast) {
								if (Integer.parseInt(rm[1].toString()) == om.getOptionId()) {
									oc.setCount((Object) rm[0]);
									break;
								}
							}
							loc.add(oc);
						}
						rc.setOptionCount(loc);
					}
					responseCount.add(rc);

				});

			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return responseCount;

	}
	

}
