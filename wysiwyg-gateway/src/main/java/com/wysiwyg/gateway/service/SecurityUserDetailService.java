package com.wysiwyg.gateway.service;

import com.wysiwyg.common.model.po.ContextUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


/**
 * @author wwcc
 */
@Service
@Table
public class SecurityUserDetailService  {

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<UserDetails> findByUsername(String mobile) {
        Criteria criteria = Criteria.empty()
                .and("MOBILE").is(mobile)
                .and("IS_DELETE").is(0);
        Query query = Query.query(criteria);
        return r2dbcEntityTemplate.selectOne(query, ContextUserInfo.class).cast(UserDetails.class);
    }


}
