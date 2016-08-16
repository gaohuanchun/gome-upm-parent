package com.gome.upm.domain;

import java.io.Serializable;
import java.util.Arrays;
/**
 * 邮件相关实体
 * @author zhouyaliang
 *
 */
public class MailInfo implements Serializable{
    private String[] mailTo;
    private String[] mailCc;
    
    public String[] getMailTo() {
        return mailTo;
    }

    public void setMailTo(String[] mailTo) {
        this.mailTo = mailTo;
    }

    public String[] getMailCc() {
        return mailCc;
    }

    public void setMailCc(String[] mailCc) {
        this.mailCc = mailCc;
    }

    @Override
    public String toString() {
        return "MailInfo{" + "mailTo=" + Arrays.toString(mailTo) + ", mailCc=" + Arrays.toString(mailCc) + '}';
    }
}
