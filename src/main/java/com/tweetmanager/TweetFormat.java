package com.tweetmanager;

import java.util.Objects;

public class TweetFormat {

    private String message;
    private String like;
    private String retweet;
    private String comment;

    private TweetFormat(String like,String comment,String message,String retweet){
        this.message = Objects.requireNonNull(message);
        this.like = like;
        this.comment = comment;
        this.retweet = retweet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TweetFormat that = (TweetFormat) o;
        return Objects.equals(message, that.message) && Objects.equals(like, that.like) && Objects.equals(retweet, that.retweet) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, like, retweet, comment);
    }
}
