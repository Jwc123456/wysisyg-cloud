package com.wysiwyg.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wysiwyg.common.context.ContextUser;
import com.wysiwyg.common.enums.IsDeleteEnum;
import com.wysiwyg.common.model.po.BasePO;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author wwcc
 * @date 2024/12/12 20:17:53
 * @description 重写ServiceImpl将平台表的一些公共字段提取自动组装
 */
@Slf4j
public class BaseService<M extends BaseMapper<T>, T extends BasePO> extends ServiceImpl<M, T> {

    /**
     * 是否删除
     */
    private static final String IS_DELETE = "IS_DELETE";
    /**
     * 创建时间
     */
    private static final String CREATE_TIME = "CREATE_TIME";
    /**
     * 修改时间
     */
    private static final String UPDATE_TIME = "UPDATE_TIME";
    /**
     * 更新人
     */
    private static final String UPDATE_BY = "UPDATE_BY";
    /**
     * 创建人
     */
    private static final String CREATE_BY = "CREATE_BY";
    /**
     * 主键
     */
    private static final String ID = "ID";


    /**
     * 列出所有未被标记为删除的记录。
     * @return 未被删除的记录列表。
     */
    public List<T> listActive() {
        return activeQuery().list();
    }

    /**
     * 创建一个查询条件，排除被标记为删除的记录。
     * @return 用于构建查询的LambdaQueryChainWrapper对象。
     */
    public LambdaQueryChainWrapper<T> activeQuery() {
        return this.lambdaQuery().setEntityClass(getEntityClass()).eq(T::getIsDelete, IsDeleteEnum.NO.getCode());
    }


    /**
     * 更新记录时填充基础字段，如更新时间和更新人。
     * @param updateWrapper 包含更新条件的Wrapper对象。
     * @return 更新是否成功。
     */
    public boolean updateWithBase(Wrapper<T> updateWrapper) {
        T entity = updateWrapper.getEntity();
        fillUpdateFields(entity);
        return super.update(entity, updateWrapper);
    }

    /**
     * 通过ID更新记录时填充基础字段，如更新时间和更新人。
     * @param entity 要更新的记录实体。
     * @return 更新是否成功。
     */
    public boolean updateWithBase(T entity) {
        fillUpdateFields(entity);
        return super.updateById(entity);
    }

    /**
     * 执行软删除操作，将指定ID列表的记录标记为已删除，并更新删除时间和删除人。
     * @param idList 要软删除的记录ID列表。
     * @return 软删除操作是否成功。
     */
    public boolean markDeleted(Collection<Long> idList) {
        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in(ID, idList);
        updateWrapper.eq(IS_DELETE, IsDeleteEnum.NO.getCode());
        //排除删除的数据
        updateWrapper.set(IS_DELETE, IsDeleteEnum.YES.getCode());

        updateWrapper.set(UPDATE_BY,ContextUser.getUserId());
        updateWrapper.set(UPDATE_TIME, LocalDateTime.now());
        return super.update(updateWrapper);
    }


    /**
     * 填充创建时的基础字段，如创建时间、创建人和是否删除。
     * @param t 要填充的记录实体。
     */
    public static <T extends BasePO> void fillCreateFields(T t) {
        if (null == t.getIsDelete()) {
            t.setIsDelete(IsDeleteEnum.NO.getCode());
        }
        if (StringUtils.isEmpty(t.getCreateBy())) {
            t.setCreateBy(ContextUser.getUserId());
        }
        if (null == t.getCreateTime()) {
            t.setCreateTime(LocalDateTime.now());
        }
    }

    /**
     * 填充更新时的基础字段，如更新时间和更新人。
     * @param t 要填充的记录实体。
     */
    private static <T extends BasePO> void fillUpdateFields(T t) {
        if (StringUtils.isEmpty(t.getUpdateBy())) {
            t.setUpdateBy(ContextUser.getUserId());
        }
        if (null == t.getUpdateTime()) {
            t.setUpdateTime(LocalDateTime.now());
        }
    }


}
