package com.genepoint.auth.controller.dto.user;

import com.genepoint.auth.controller.dto.PageReq;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * @author xiabiao
 * @since 2023-08-31
 */
@Getter
@Setter
public class UserPageQueryDTO extends PageReq {

    private String username;

    private Date beginTime;

    private Date endTime;
}
