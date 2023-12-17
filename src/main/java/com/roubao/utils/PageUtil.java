package com.roubao.utils;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.roubao.common.request.PageReqDto;
import com.roubao.common.response.PageList;
import com.roubao.config.exception.ParameterCheckException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 分页封装工具类
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/17
 **/
@Slf4j
public class PageUtil {

    /**
     * 分页查询
     *
     * @param reqDto 请求体
     * @param select select接口
     * @param clazz  响应类型
     * @param <T>    请求类型枚举
     * @param <R>    响应类型枚举
     * @return PageList分页通用响应体
     */
    public static <T extends PageReqDto, R> PageList<R> doStart(T reqDto, ISelect select, Class<R> clazz) {
        if (reqDto.getCurrent() == null || reqDto.getPageSize() == null) {
            log.error("PageUtil ==> The current and pageSize cannot be null.");
            throw new ParameterCheckException("The current and pageSize cannot be null.");
        }

        // 执行分页查询mapper
        PageInfo<R> pageInfo = PageHelper.startPage(reqDto.getCurrent(), reqDto.getPageSize()).doSelectPageInfo(select);

        // 组装分页通用响应体
        PageList<R> respResult = new PageList<>();
        setCommonData(reqDto, respResult);
        respResult.setTotal(pageInfo.getTotal());
        respResult.setList(pageInfo.getList());
        return respResult;
    }

    /**
     * 分页查询（带后置处理）
     *
     * @param reqDto   请求体
     * @param select   select接口
     * @param clazz    响应类型
     * @param consumer 查询结果后置处理
     * @param <T>      请求类型枚举
     * @param <R>      响应类型枚举
     * @return PageList分页通用响应体
     */
    public static <T extends PageReqDto, R> PageList<R> doStartAfter(T reqDto, ISelect select, Class<R> clazz,
                                                                     Consumer<List<R>> consumer) {
        PageList<R> pageResult = doStart(reqDto, select, clazz);
        consumer.accept(pageResult.getList());
        // 重新赋值总数,避免在后置操作中对数据进行修改
        pageResult.setTotal((long) pageResult.getList().size());
        return pageResult;
    }

    /**
     * 获取空分页对象
     *
     * @param reqDto 请求体
     * @param clazz  响应体类型
     * @param <T>    请求体枚举
     * @param <R>    响应体枚举
     * @return PageList
     */
    public static <T extends PageReqDto, R> PageList<R> doEmpty(T reqDto, Class<R> clazz) {
        PageList<R> respResult = new PageList<>();
        setCommonData(reqDto, respResult);
        respResult.setTotal(0L);
        respResult.setList(new ArrayList<>(0));
        return respResult;
    }

    /**
     * 手动分页
     *
     * @param list     响应数据结果集
     * @param pageNum  当前页码
     * @param pageSize 每页数量
     * @param <T>      数据泛型
     * @return PageList
     */
    public static <T> PageList<T> doHandPage(List<T> list, int pageNum, int pageSize) {
        PageList<T> pageResult = new PageList<>();
        pageResult.setCurrent(pageNum);
        pageResult.setPageSize(pageSize);
        if (list == null || list.size() == 0) {
            pageResult.setList(new ArrayList<>(0));
            pageResult.setTotal(0L);
        } else {
            int from = Math.min((pageNum - 1) * pageSize, list.size());
            int to = Math.min((pageNum * pageSize), list.size());
            List<T> pageList = list.subList(from, to);
            pageResult.setTotal((long) list.size());
            pageResult.setList(pageList);
        }
        return pageResult;
    }

    /**
     * 创建分页校验对象
     *
     * @return PageJudge
     */
    public static PageJudge buildJudge() {
        return new PageJudge(true);
    }

    private static <T extends PageReqDto, R> void setCommonData(T reqDto, PageList<R> respResult) {
        respResult.setCurrent(reqDto.getCurrent());
        respResult.setPageSize(reqDto.getPageSize());
    }

    /**
     * 分页查询条件限制类
     */
    public static class PageJudge {
        private boolean conditionResult;

        public PageJudge(boolean conditionResult) {
            this.conditionResult = conditionResult;
        }

        /**
         * 限制条件（true才执行分页查询,false返回空分页对象）
         *
         * @param condition 判断条件
         * @return PageJudge
         */
        public PageJudge condition(boolean condition) {
            if (this.conditionResult) {
                this.conditionResult = condition;
            }
            return this;
        }

        /**
         * 执行分页查询（限制条件为true执行SQL查询,false返回空分页对象）
         *
         * @param reqDto 请求体
         * @param select 查询接口
         * @param clazz  响应体类型
         * @param <T>    请求体枚举
         * @param <R>    响应体枚举
         * @return PageList
         */
        public <T extends PageReqDto, R> PageList<R> doStart(T reqDto, ISelect select, Class<R> clazz) {
            if (this.conditionResult) {
                return PageUtil.doStart(reqDto, select, clazz);
            } else {
                return PageUtil.doEmpty(reqDto, clazz);
            }
        }

        /**
         * 分页查询（带后置处理）
         *
         * @param reqDto   请求体
         * @param select   select接口
         * @param clazz    响应类型
         * @param consumer 查询结果后置处理
         * @param <T>      请求类型枚举
         * @param <R>      响应类型枚举
         * @return PageList分页通用响应体
         */
        public <T extends PageReqDto, R> PageList<R> doStartAfter(T reqDto, ISelect select, Class<R> clazz,
                                                                  Consumer<List<R>> consumer) {
            if (this.conditionResult) {
                PageList<R> pageResult = PageUtil.doStart(reqDto, select, clazz);
                consumer.accept(pageResult.getList());
                return pageResult;
            } else {
                return PageUtil.doEmpty(reqDto, clazz);
            }
        }
    }
}
