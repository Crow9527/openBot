package com.crow.qqbot.service.qq.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crow.qqbot.mapper.qq.QQGroupConfigMapper;
import com.crow.qqbot.mode.bean.qq.QQGroupConfig;
import com.crow.qqbot.service.qq.QQGroupConfigService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * qq群组配置表 服务实现类
 * </p>
 *
 * @author qqbot
 * @since 2023-08-07
 */
@Service
public class QQGroupConfigServiceImpl extends ServiceImpl<QQGroupConfigMapper, QQGroupConfig>
		implements QQGroupConfigService {

}
