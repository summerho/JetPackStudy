package com.hexia.jetpackstudy.model;

import java.io.Serializable;
import java.util.Objects;

public class NewsBean implements Serializable {

    public String postid;

    public String source;

    public String title;

    public String digest;

    public String imgsrc;

    public String ptime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsBean newsBean = (NewsBean) o;
        return postid.equals(newsBean.postid) && source.equals(newsBean.source) && title.equals(newsBean.title)
                && digest.equals(newsBean.digest) && imgsrc.equals(newsBean.imgsrc) && ptime.equals(newsBean.ptime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postid, source, title, digest, imgsrc, ptime);
    }
}
