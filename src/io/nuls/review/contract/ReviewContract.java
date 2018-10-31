package io.nuls.review.contract;

import io.nuls.contract.sdk.*;
import io.nuls.contract.sdk.annotation.Payable;
import io.nuls.contract.sdk.annotation.Required;
import io.nuls.contract.sdk.annotation.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewContract implements Contract {

    Map<String, List<Review>> reviews = new HashMap<>();

    @View
    public List<Review> getProductReviews(@Required String productId){
        Utils.require(!productId.isEmpty(),"Product ID is required");
        if(!reviews.containsKey(productId)){
            Utils.revert("No Reviews found for the product!!");
        }
        return reviews.get(productId);
    }

    @Payable
    public Review writeReview(@Required String productId,@Required String reviewComments){
        Utils.require(!productId.isEmpty(),"Product ID is required for this operation");
        Utils.require(!reviewComments.isEmpty(),"Review comments are required");
        Address address = Msg.sender();
        ReviewContract.Review review = new Review(reviewComments,productId,address.toString());
        List<Review> reviewList = reviews.get(productId);
        if( null == reviewList){
            reviewList = new ArrayList<>();
        }
        reviewList.add(review);
        reviews.put(productId,reviewList);
        Utils.emit(new WriteReviewEvent(productId,reviewComments));
        return review;
    }

    private static class Review{

        private String comments;
        private String productId;
        private String writer;

        public Review(String comments, String productId,String writter) {
            this.comments = comments;
            this.productId = productId;
            this.writer = writter;
        }

        public Review(String comments, String productId) {
            this(comments,productId,null);
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getWritter() {
            return writer;
        }

        public void setWritter(String writter) {
            this.writer = writter;
        }

        @Override
        public String toString() {
            return "Review{" +
                    " comments='" + comments + '\'' +
                    ", productId='" + productId + '\'' +
                    ", writer='" + writer + '\'' +
                    '}';
        }
    }

    private static class WriteReviewEvent implements Event {

        private String productId;
        private String reviewComments;

        public WriteReviewEvent(String productId, String reviewComments) {
            this.productId = productId;
            this.reviewComments = reviewComments;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getReviewComments() {
            return reviewComments;
        }

        public void setReviewComments(String reviewComments) {
            this.reviewComments = reviewComments;
        }

        @Override
        public String toString() {
            return "WriteReviewEvent{" +
                    "productId='" + productId + '\'' +
                    ", reviewComments='" + reviewComments + '\'' +
                    '}';
        }
    }
}
