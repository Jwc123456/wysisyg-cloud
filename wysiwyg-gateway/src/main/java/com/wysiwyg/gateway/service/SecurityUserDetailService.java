package com.wysiwyg.gateway.service;

import com.wysiwyg.gateway.model.po.AdminUserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;


/**
 * @author wwcc
 */
@Service
@Table
public class SecurityUserDetailService  {

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Autowired
    private DatabaseClient databaseClient;

    public Mono<UserDetails> findByUsername(String mobile) {
        Criteria criteria = Criteria.empty()
                .and("MOBILE").is(mobile)
                .and("IS_DELETE").is(0);
        Query query = Query.query(criteria);
        return r2dbcEntityTemplate.selectOne(query, AdminUserPO.class)
                .flatMap(user-> databaseClient.sql("select ROLE_ID from wysiwyg_admin_user_role where user_id = ?")
                        .bind(0,user.getUserId())
                        .map(row-> String.valueOf(row.get("ROLE_ID")))
                        .all()
                        .collect(Collectors.toSet())
                        .map(authorities-> {
                            user.setRoles(authorities);
                            return user;
                        }))
                .cast(UserDetails.class);
    }

    public Flux<GrantedAuthority> findPathAuthorities(String path) {
        return databaseClient.sql("select distinct c.ROLE_ID" +
                                  "  from wysiwyg_admin_permission a" +
                                  "  join wysiwyg_admin_menu_permission b" +
                                  "    on a.ID = b.PERMISSION_ID" +
                                  "  join wysiwyg_admin_role_menu c" +
                                  "    on c.MENU_ID = b.MENU_ID" +
                                  " where a.PATH = ?" +
                                  "   and a.IS_DELETE = 0" +
                                  "   and b.IS_DELETE = 0" +
                                  "   and c.IS_DELETE = 0").bind(0,path)
                .fetch().all().map(row-> new SimpleGrantedAuthority(String.valueOf(row.get("ROLE_ID"))));
    }



}
