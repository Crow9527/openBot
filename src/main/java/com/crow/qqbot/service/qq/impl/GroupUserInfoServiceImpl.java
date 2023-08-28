package com.crow.qqbot.service.qq.impl;

import com.crow.qqbot.mode.bean.qq.GroupUserInfo;
import com.crow.qqbot.mapper.qq.GroupUserInfoMapper;
import com.crow.qqbot.service.qq.GroupUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * qq群用户信息表 服务实现类
 * </p>
 *
 * @author crow
 * @since 2023-08-12
 */
@Service
public class GroupUserInfoServiceImpl extends ServiceImpl<GroupUserInfoMapper, GroupUserInfo>
		implements GroupUserInfoService {

}
