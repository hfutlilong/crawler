package com.netease.music.common.error;

/**
 * 错误码定义
 * 两位数以下错误码保留给公共业务异常使用
 */
public interface CrawlerErrorCode {
    /**
     * 业务异常
     */
    int BIZ_ERROR = -1;
    /**
     * 非法的参数
     */
    int ILLEGAL_PARAM = -2;
    /**
     * 系统异常
     */
    int SYSTEM_ERROR = -3;
    /**
     * 数据已过期
     */
    int DATA_HAS_EXPIRED = -4;

    /**
     * 数据重复
     */
    int DATA_DUPLICATE = -5;

    /**
     * 数据插入失败
     */
    int DATA_INSERT_FAILED = -6;

    /**
     * 数据更新失败
     */
    int DATA_UPDATE_FAILED = -7;

    interface  Corpus {
        int DUPLICATE_BATCH_NAME = -100;
        int MAPPING_WORK_ORDER_TYPE_FAIL = -101;
        int DATA_IS_EMPTY_OR_ORVER_LIMIT = -102;
        int OWN_COUNT_IS_ZERO = -103;
    }
}
