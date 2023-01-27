package com.cdac.ratingandreview.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;


import com.cdac.ratingandreview.feign.LearnerClient;
import com.cdac.ratingandreview.model.ReplyReview;
import com.cdac.ratingandreview.model.ReviewDetail;
import com.cdac.ratingandreview.model.ReviewMaster;
import com.cdac.ratingandreview.model.ReviewMasterPK;
import com.cdac.ratingandreview.model.SentimentAnalysi;
import com.cdac.ratingandreview.pojo.LearnerMasterPojo;
import com.cdac.ratingandreview.pojo.RatingandReviewPojo;
import com.cdac.ratingandreview.pojo.ReviewAvgPojo;
import com.cdac.ratingandreview.pojo.ReviewDetailsPojo;
import com.cdac.ratingandreview.pojo.ReviewReplyDetailsPojo;
import com.cdac.ratingandreview.pojo.ReviewReplyPojo;
import com.cdac.ratingandreview.repository.ReviewDetailRepository;
import com.cdac.ratingandreview.repository.ReviewMasterRepository;
import com.cdac.ratingandreview.repository.ReviewReplyRepository;
import com.cdac.ratingandreview.repository.SentimentAnalysisRepository;
import com.cdac.ratingandreview.sentiment.SentimentAnalyzer;
import com.cdac.ratingandreview.stanford_nlp.model.SentimentResult;
import com.sun.xml.bind.v2.runtime.reflect.ListIterator;

@Configuration
@PropertySource("classpath:application.properties")
@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewDetailRepository reviewDetailRepository;

	@Autowired
	ReviewMasterRepository reviewMasterRepository;

	@Autowired
	ReviewReplyRepository reviewReplyRepository;

	@Autowired
	SentimentAnalysisRepository sentimentAnalysisRepository;

	@Autowired
	ReviewReplyRepository replyRepository;

	@Autowired
	private LearnerClient learnerClient;

	@Value("${imageurl.path}")
	String imageurlpath;

	final String SUCCESS = "Success";
	final String FAILED = "Failed";

	@Override
	@Transactional
	public String saveReview(RatingandReviewPojo ratingandreviewPojo) {

		try {

			/*
			 * System.out.println("--------------------" +
			 * ratingandreviewPojo.getReviewId());
			 * System.out.println("Review Text--------------------" +
			 * ratingandreviewPojo.getReviewText());
			 * System.out.println("item id--------------------" +
			 * ratingandreviewPojo.getItemId());
			 * System.out.println("learner id-------------------" +
			 * ratingandreviewPojo.getLearnerId());
			 * System.out.println("rating--------------------" +
			 * ratingandreviewPojo.getRating());
			 * System.out.println("Review status--------------------" +
			 * ratingandreviewPojo.getReviewStatus());
			 * System.out.println("Review type--------------------" +
			 * ratingandreviewPojo.getReviewType());
			 */

			/* below code will calculate sentiment type for review text */

			String text = ratingandreviewPojo.getReviewText();
			SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
			sentimentAnalyzer.initialize();
			SentimentResult sentimentResult = sentimentAnalyzer.getSentimentResult(text);

			/*
			 * System.out.println("Sentiment Score: " +
			 * sentimentResult.getSentimentScore()); System.out.println("Sentiment Type: " +
			 * sentimentResult.getSentimentType()); System.out.println("Very positive: " +
			 * sentimentResult.getSentimentClass().getVeryPositive() + "%");
			 * System.out.println("Positive: " +
			 * sentimentResult.getSentimentClass().getPositive() + "%");
			 * System.out.println("Neutral: " +
			 * sentimentResult.getSentimentClass().getNeutral() + "%");
			 * System.out.println("Negative: " +
			 * sentimentResult.getSentimentClass().getNegative() + "%");
			 * System.out.println("Very negative: " +
			 * sentimentResult.getSentimentClass().getVeryNegative() + "%");
			 */

			/* End */

			ReviewDetail reviewDetail = new ReviewDetail();

			reviewDetail.setReviewType(ratingandreviewPojo.getReviewType());
			reviewDetail.setReviewText(HtmlUtils.htmlEscape(ratingandreviewPojo.getReviewText()));
			reviewDetail.setRating(ratingandreviewPojo.getRating());
			reviewDetail.setTenantId(ratingandreviewPojo.getTenantId());
			reviewDetail.setReviewStatus(ratingandreviewPojo.getReviewStatus());
			Calendar calendar = Calendar.getInstance();
			reviewDetail.setCreationTime(new java.sql.Timestamp(calendar.getTime().getTime()));
			reviewDetail.setSentimentAnalysis(sentimentResult.getSentimentType());

			List<ReviewMaster> rm = reviewMasterRepository.findByIdLearnerIdAndIdRviewItemId(
					ratingandreviewPojo.getLearnerId(), ratingandreviewPojo.getItemId());
			// System.out.println("size----------"+rm.size());
			if (rm.size() == 1) {

				return "Exist";
			} else {
				reviewDetailRepository.save(reviewDetail);

				// System.out.println("review id-------------->" + reviewDetail.getReviewId());
				ReviewMaster reviewMaster = new ReviewMaster();
				ReviewDetail rd = new ReviewDetail();
				rd.setReviewId(reviewDetail.getReviewId());
				ReviewMasterPK rmpk = new ReviewMasterPK();
				rmpk.setLearnerId(ratingandreviewPojo.getLearnerId());
				rmpk.setRviewItemId(ratingandreviewPojo.getItemId());

				reviewMaster.setId(rmpk);
				reviewMaster.setReviewDetail(rd);

				reviewMasterRepository.save(reviewMaster);

				SentimentAnalysi sa = new SentimentAnalysi();
				sa.setReviewId(reviewDetail.getReviewId());
				sa.setSentimentScore(sentimentResult.getSentimentScore());
				sa.setSentimentType(sentimentResult.getSentimentType());
				sa.setVeryPositive(sentimentResult.getSentimentClass().getVeryPositive());
				sa.setPositive(sentimentResult.getSentimentClass().getPositive());
				sa.setNeutral(sentimentResult.getSentimentClass().getNeutral());
				sa.setNegative(sentimentResult.getSentimentClass().getNegative());
				sa.setVeryNegative(sentimentResult.getSentimentClass().getVeryNegative());
				sa.setDate(new java.sql.Timestamp(calendar.getTime().getTime()));

				sentimentAnalysisRepository.save(sa);

				return SUCCESS;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return FAILED;

	}

	@Override
	public String updateReview(RatingandReviewPojo ratingandreviewPojo) {

		try {

			ReviewDetail reviewDetail = reviewDetailRepository.findByReviewId(ratingandreviewPojo.getReviewId());

			if (reviewDetail != null) {

				/* below code will calculate sentiment type for review text */

				String text = ratingandreviewPojo.getReviewText();
				SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
				sentimentAnalyzer.initialize();
				SentimentResult sentimentResult = sentimentAnalyzer.getSentimentResult(text);

				// reviewDetail.setReviewType(ratingandreviewPojo.getReviewType());
				reviewDetail.setReviewText(HtmlUtils.htmlEscape(ratingandreviewPojo.getReviewText()));
				reviewDetail.setRating(ratingandreviewPojo.getRating());
				// reviewDetail.setReviewStatus(ratingandreviewPojo.getReviewStatus());
				Calendar calendar = Calendar.getInstance();
				reviewDetail.setCreationTime(new java.sql.Timestamp(calendar.getTime().getTime()));
				reviewDetail.setSentimentAnalysis(sentimentResult.getSentimentType());

				reviewDetailRepository.save(reviewDetail);

				SentimentAnalysi sa = new SentimentAnalysi();
				sa.setReviewId(reviewDetail.getReviewId());
				sa.setSentimentScore(sentimentResult.getSentimentScore());
				sa.setSentimentType(sentimentResult.getSentimentType());
				sa.setVeryPositive(sentimentResult.getSentimentClass().getVeryPositive());
				sa.setPositive(sentimentResult.getSentimentClass().getPositive());
				sa.setNeutral(sentimentResult.getSentimentClass().getNeutral());
				sa.setNegative(sentimentResult.getSentimentClass().getNegative());
				sa.setVeryNegative(sentimentResult.getSentimentClass().getVeryNegative());
				sa.setDate(new java.sql.Timestamp(calendar.getTime().getTime()));

				sentimentAnalysisRepository.save(sa);

				return SUCCESS;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return FAILED;
	}

	@Override
	public String deleteReview(int reviewid) {

		ReviewDetail reviewDetail = reviewDetailRepository.findByReviewId(reviewid);

		// ReviewDetail
		// rd=reviewMasterRepository.findById(reviewid).get().getReviewDetail();

		SentimentAnalysi sentimentAnalysi = sentimentAnalysisRepository.findByReviewId(reviewid);

		try {
			if (reviewDetail != null) {

				/* below code will calculate sentiment type for review text */
				System.out.println("review details-------------" + reviewDetail.getReviewId());

				// reviewMaster.se
				ReviewMaster rm = reviewMasterRepository.findByReviewDetail(reviewDetail);

				reviewMasterRepository.delete(rm);
				// reviewMasterRepository.delete(reviMaster);
				sentimentAnalysisRepository.delete(sentimentAnalysi);
				reviewDetailRepository.delete(reviewDetail);

				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
		}

		return FAILED;
	}

	@Override
	public String saveReviewReply(ReviewReplyPojo replyPojo) {

		try {

			System.out.println("----------" + replyPojo.getRepliedBy() + replyPojo.getReplyId()
					+ replyPojo.getReplyText() + replyPojo.getReviewId());
			ReplyReview replyReview = new ReplyReview();

			replyReview.setRepliedBy(replyPojo.getRepliedBy());
			replyReview.setReplyText(replyPojo.getReplyText());
			Calendar calendar = Calendar.getInstance();
			replyReview.setRepliedDate(new java.sql.Timestamp(calendar.getTime().getTime()));

			ReviewDetail rd = new ReviewDetail();
			rd.setReviewId(replyPojo.getReviewId());
			replyReview.setReviewDetail1(rd);

			replyRepository.save(replyReview);

			return SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
			// TODO: handle exception
		}
	}

	@Override
	public String updateReviewReply(ReviewReplyPojo replyPojo) {

		try {

			ReplyReview rr = replyRepository.findByReplyId(replyPojo.getReplyId());

			if (rr != null) {

				rr.setRepliedBy(replyPojo.getRepliedBy());
				Calendar calendar = Calendar.getInstance();
				rr.setRepliedDate(new java.sql.Timestamp(calendar.getTime().getTime()));
				rr.setReplyText(replyPojo.getReplyText());

				ReviewDetail rd = new ReviewDetail();
				rd.setReviewId(replyPojo.getReviewId());
				rr.setReviewDetail1(rd);

				replyRepository.save(rr);
				return SUCCESS;

			}

		} catch (Exception e) {
			return FAILED;
		}

		return FAILED;
	}

	@Override
	public String deleteReviewReply(int replyId) {

		try {
			ReplyReview rr = replyRepository.findByReplyId(replyId);

			if (rr != null) {

				replyRepository.delete(rr);
				;
				return SUCCESS;
			}

		} catch (Exception e) {
			return FAILED;
		}

		return FAILED;
	}

	@Override
	public ReviewDetail findbyReviewId(int reviewid) {

		try {
			return reviewDetailRepository.findById(reviewid).get();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public ReviewDetailsPojo getreviewDetails(int reviewid) {
		try {
			String profilepicurl;
			ReviewDetailsPojo rdp = new ReviewDetailsPojo();
			ReviewDetail rd = reviewDetailRepository.findByReviewId(reviewid);
			ReviewMaster rm = reviewMasterRepository.findByReviewDetail(rd);
			List<ReplyReview> rr = reviewReplyRepository.findByReviewDetail(rd);
			List<ReviewReplyDetailsPojo> rrdplist = new ArrayList<>();
			LearnerMasterPojo lmo = learnerClient.getDetailsofLearner(
					rm.getId().getLearnerId()); /* get learner details from User management service */

			if (rd != null) {
				// System.out.println("Inside if" + rd.getRating());
				rdp.setReviewId(rd.getReviewId());
				rdp.setRating(rd.getRating());
				rdp.setReviewText(rd.getReviewText());
				rdp.setReviewType(rd.getReviewType());
                rdp.setTenantId(rd.getTenantId());
				rdp.setSentimentAnalysis(rd.getSentimentAnalysi().getSentimentType());
				rdp.setCreationTime(rd.getCreationTime());
				rdp.setLearnerUsername(lmo.getLearnerUsername());
				rdp.setFirstName(lmo.getFirstName());
				rdp.setLastName(lmo.getLastName());
				profilepicurl = imageurlpath + lmo.getLearnerUsername();
				rdp.setProfilePicUrl(profilepicurl);

				for (int i = 0; i < rr.size(); i++) {
					ReviewReplyDetailsPojo rrdp = new ReviewReplyDetailsPojo();
					rrdp.setRepliedBy(rr.get(i).getRepliedBy());
					rrdp.setRepliedDate(rr.get(i).getRepliedDate());
					rrdp.setReplyId(rr.get(i).getReplyId());
					rrdp.setReplyText(rr.get(i).getReplyText());
					rrdp.setReviewId(rr.get(i).getReviewDetail().getReviewId());
					rrdplist.add(rrdp);
				}

				rdp.setReviewReply(rrdplist);

				return rdp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ReviewDetailsPojo> getreviewDetailsbyItemId(String reviewItemId) {
		try {
			String profilepicurl;
			List<ReviewDetailsPojo> rdplist = new ArrayList<>();
			List<ReviewMaster> rmlist = reviewMasterRepository.findByIdRviewItemId(reviewItemId);

			for (int i = 0; i < rmlist.size(); i++) {
				LearnerMasterPojo lmo = learnerClient.getDetailsofLearner(
						rmlist.get(i).getId().getLearnerId()); /* get learner details from User management service */
				ReviewDetail rd = reviewDetailRepository.findByReviewId(rmlist.get(i).getReviewDetail().getReviewId());
				ReviewDetailsPojo rdp = new ReviewDetailsPojo();
				List<ReplyReview> rr = reviewReplyRepository.findByReviewDetail(rd);
				List<ReviewReplyDetailsPojo> rrdplist = new ArrayList<>();
				if (rd != null) {
					// System.out.println("Inside if" + rd.getRating());
					rdp.setReviewId(rd.getReviewId());
					rdp.setRating(rd.getRating());
					rdp.setReviewText(rd.getReviewText());
					rdp.setReviewType(rd.getReviewType());
					rdp.setTenantId(rd.getTenantId());
					rdp.setSentimentAnalysis(rd.getSentimentAnalysi().getSentimentType());
					rdp.setCreationTime(rd.getCreationTime());
					rdp.setLearnerUsername(lmo.getLearnerUsername());
					rdp.setFirstName(lmo.getFirstName());
					rdp.setLastName(lmo.getLastName());
					profilepicurl = imageurlpath + lmo.getLearnerUsername();
					rdp.setProfilePicUrl(profilepicurl);

					for (int j = 0; j < rr.size(); j++) {
						ReviewReplyDetailsPojo rrdp = new ReviewReplyDetailsPojo();
						rrdp.setRepliedBy(rr.get(j).getRepliedBy());
						rrdp.setRepliedDate(rr.get(j).getRepliedDate());
						rrdp.setReplyId(rr.get(j).getReplyId());
						rrdp.setReplyText(rr.get(j).getReplyText());
						rrdp.setReviewId(rr.get(j).getReviewDetail().getReviewId());
						rrdplist.add(rrdp);
					}
					rdp.setReviewReply(rrdplist);
					rdplist.add(rdp);
				}

			}

			return rdplist;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ReviewAvgPojo> getAvgreviewbyItemId(List<ReviewAvgPojo> ItemIdList) {
		
		List<ReviewAvgPojo> avglist = new ArrayList<>();
		try {
				for (int i=0; i<ItemIdList.size();i++) {
				ReviewAvgPojo rap=new ReviewAvgPojo();
				List<Integer> reviewIdlist = new ArrayList<>();
				List<ReviewMaster> rmlist = reviewMasterRepository.findByIdRviewItemId(ItemIdList.get(i).getItemId()); // get the review id from reviewItem id
				
				if (rmlist != null) {
					for(int j=0; j<rmlist.size();j++) {
						
						reviewIdlist.add(rmlist.get(j).getReviewDetail().getReviewId()); 
					}
				
				}
				double total = 0;
				for(int k=0;k<reviewIdlist.size();k++) {
 				//	ReviewDetail rd=reviewDetailRepository.findByReviewId(reviewIdlist.get(k));// get the review details from review id
 					ReviewDetail rd=reviewDetailRepository.findByReviewIdAndTenantId(reviewIdlist.get(k), ItemIdList.get(i).getTenantId());// get the review details by reviewid and tenantid
					if(rd!=null) {// checking whether review details exist or not
						total=total+rd.getRating(); // finding sum of all reviews  	
						rap.setTenantId(rd.getTenantId());
						rap.setItemId(ItemIdList.get(i).getItemId());
					}
					else { // if review id and tenantid does not match returns all values as zero						
						rap.setTenantId(0);
						rap.setItemId("0");
					}
 					
			}
                       double avg=Math.round((total/reviewIdlist.size()) * 10.0) / 10.0; //to round the average value to two decimals
                        
                        
                        rap.setAvgScore(avg);
                        avglist.add(rap);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return avglist;
	}
}
