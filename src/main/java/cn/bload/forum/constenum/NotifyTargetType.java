package cn.bload.forum.constenum;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/13 17:50
 * @describe 类描述:
 */
public enum NotifyTargetType {
    /**
     * 文章
     */
    ARTICLE("article"),
    /**
     * 评论
     */
    COMMENT("comment"),
    /**
     * 公告
     */
    NOTICE("notice"),
    /**
     * 系统
     */
    SYSTEM("system");

    @Getter
    @Setter
    private String name;

    NotifyTargetType(String name) {
        this.name = name;
    }
}
