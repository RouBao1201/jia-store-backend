package com.roubao.config.asserts;

/**
 * 断言执行器异常处理器
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/23
 **/
public interface AssertPerformerExceptionHandler extends AssertLogicPerformer, ExceptionBasicFunction {

    @Override
    default AssertException newException() {
        return new AssertException(this.getCode(), this.getMessage());
    }

    @Override
    default AssertException newException(String errorMessage) {
        return new AssertException(this.getCode(), errorMessage);
    }
}
