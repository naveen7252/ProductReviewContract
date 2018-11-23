package io.nuls.contract.review;

import io.nuls.contract.sdk.*;
import io.nuls.contract.sdk.annotation.Payable;
import io.nuls.contract.sdk.annotation.Required;
import io.nuls.contract.sdk.annotation.View;

import java.util.*;

public class ReviewContract implements Contract {

    Map<String,List<Review>> reviews = new HashMap<String, List<Review>>();

    @Payable
    public Review writeReview(@Required String productId,@Required String reviewComments){
        Utils.require(!productId.isEmpty(),"Product ID is required");
        Utils.require(!reviewComments.isEmpty(),"Review Comments are required");
        Address address = Msg.sender();
        Review review = new Review(productId,reviewComments,address.toString());

        List<Review> reviewList = reviews.get(productId);
        if(null == reviewList){
            reviewList = new ArrayList<Review>();
        }
        reviewList.add(review);
        reviews.put(productId,reviewList);
        Utils.emit(new WriteReviewEvent(productId,reviewComments,address.toString()));
        return review;
    }

    @View
    public List<Review> getReviews(@Required String productId){
        Utils.require(!productId.isEmpty(),"Product is is required");
        return reviews.get(productId);
    }

    @View
    public String getAllProductIds(){
        Set<String> productIds = reviews.keySet();
        List<String> ids = new ArrayList<String>();

        String res = "[";
        for(String id : productIds){
            ids.add(id);
            res = res + "\""+id+"\"" +",";
        }
        res = res.substring(0,res.length()-1);
        res = res + "]";
        return res;
    }

    private class Review{

        private String productId;
        private String comments;
        private String writer;

        public Review(String productId, String comments, String writer) {
            this.productId = productId;
            this.comments = comments;
            this.writer = writer;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getWriter() {
            return writer;
        }

        public void setWriter(String writer) {
            this.writer = writer;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Review review = (Review) o;

            if (!productId.equals(review.productId)) return false;
            if (!comments.equals(review.comments)) return false;
            return writer.equals(review.writer);
        }

        @Override
        public int hashCode() {
            int result = productId.hashCode();
            result = 31 * result + comments.hashCode();
            result = 31 * result + writer.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return
                    "{\"productId\""+":"+ "\""+productId +"\""+
                            ","+"\"comments\""+":" + "\""+comments+ "\""+
                            "," +"\"writer\"" +":"+ "\""+writer+"\""+"}";
        }
    }

    private class WriteReviewEvent implements Event{
        private String productId;
        private String reviewComments;
        private String writer;

        public WriteReviewEvent(String productId, String reviewComments,String writer) {
            this.productId = productId;
            this.reviewComments = reviewComments;
            this.writer = writer;
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

        public String getWriter() {
            return writer;
        }

        public void setWriter(String writer) {
            this.writer = writer;
        }

        @Override
        public String toString() {
            return "productId='" + productId + '\'' +
                    ", reviewComments='" + reviewComments + '\'' +
                    ", writer='" + writer + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            WriteReviewEvent that = (WriteReviewEvent) o;

            if (!productId.equals(that.productId)) return false;
            if (!reviewComments.equals(that.reviewComments)) return false;
            return writer.equals(that.writer);
        }

        @Override
        public int hashCode() {
            int result = productId.hashCode();
            result = 31 * result + reviewComments.hashCode();
            result = 31 * result + writer.hashCode();
            return result;
        }
    }

}
