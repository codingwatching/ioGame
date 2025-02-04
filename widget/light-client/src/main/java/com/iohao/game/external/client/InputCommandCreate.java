/*
 * ioGame
 * Copyright (C) 2021 - present  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.iohao.game.external.client;

import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.protocol.wrapper.IntValue;
import com.iohao.game.action.skeleton.protocol.wrapper.LongValue;
import com.iohao.game.action.skeleton.protocol.wrapper.StringValue;
import com.iohao.game.common.kit.exception.ThrowKit;
import com.iohao.game.external.client.command.InputCommand;
import com.iohao.game.external.client.command.RequestDataDelegate;
import com.iohao.game.external.client.kit.AssertKit;
import com.iohao.game.external.client.kit.ClientKit;
import com.iohao.game.external.client.kit.ClientUserConfigs;
import com.iohao.game.external.client.kit.ScannerKit;
import com.iohao.game.external.client.user.ClientUserInputCommands;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 模块输入命令域
 *
 * @author 渔民小镇
 * @date 2023-07-09
 */
@Slf4j
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PUBLIC)
public class InputCommandCreate {
    int cmd = -1;
    /** true 相同路由的 InputCommand 只能存在一个 */
    boolean uniqueInputCommand = ClientUserConfigs.uniqueInputCommand;
    /** 模块描述的前缀 */
    String cmdName = "";

    ClientUserInputCommands clientUserInputCommands;

    public CmdInfo ofCmdInfo(int subCmd) {
        AssertKit.assertTrueThrow(cmd < 0, "cmd 不能小于 0");
        return CmdInfo.of(cmd, subCmd);
    }

    public InputCommand getInputCommand(int subCmd) {
        CmdInfo cmdInfo = ofCmdInfo(subCmd);
        InputCommand inputCommand = clientUserInputCommands.getInputCommand(cmdInfo);
        Objects.requireNonNull(inputCommand, "没有对应的请求配置");
        return inputCommand;
    }

    /**
     * 创建模拟命令
     *
     * @param subCmd 子路由
     * @return InputCommand
     */
    public InputCommand ofInputCommand(int subCmd) {
        return ofInputCommand(subCmd, null);
    }

    private InputCommand ofInputCommand(int subCmd, RequestDataDelegate requestData) {

        CmdInfo cmdInfo = ofCmdInfo(subCmd);

        // 唯一性路由命令检测，先检查命令是否存在
        extractedChecked(cmdInfo);

        return clientUserInputCommands.ofCommand(cmdInfo)
                .setCmdName(this.cmdName)
                .setRequestData(requestData);
    }

    private void extractedChecked(CmdInfo cmdInfo) {
        if (uniqueInputCommand) {
            var inputName = ClientKit.toInputName(cmdInfo);
            InputCommand inputCommand = clientUserInputCommands.getInputCommand(inputName);
            if (Objects.nonNull(inputCommand)) {
                // 存在重复的路由命令
                ThrowKit.ofRuntimeException("There are duplicate routing commands : " + cmdInfo);
            }
        }
    }

    /**
     * 创建模拟命令，在使用命令时需要在控制台输入 long 类型的请求参数
     *
     * @param subCmd 子路由
     * @return InputCommand
     */
    public InputCommand ofInputCommandLong(int subCmd) {
        RequestDataDelegate requestData = nextParamLong("参数");
        return ofInputCommand(subCmd, requestData);
    }

    /**
     * 创建模拟命令，在使用命令时需要在控制台输入 long 类型的 userId 请求参数
     *
     * @param subCmd 子路由
     * @return InputCommand
     */
    public InputCommand ofInputCommandUserId(int subCmd) {
        RequestDataDelegate requestData = nextParamLong("对方的 userId");
        return ofInputCommand(subCmd, requestData);
    }

    public RequestDataDelegate nextParamLong(String paramTips) {
        return () -> {
            log.info("请输入{} | 参数类型 : long.class", paramTips);

            long longValue = ScannerKit.nextLong();
            return LongValue.of(longValue);
        };
    }

    public InputCommand ofInputCommandInt(int subCmd) {
        RequestDataDelegate requestData = nextParamInt("参数");
        return ofInputCommand(subCmd, requestData);
    }

    public RequestDataDelegate nextParamInt(String paramTips) {
        return () -> {
            String info = "请输入{} | 参数类型 : int.class";
            log.info(info, paramTips);

            int intValue = ScannerKit.nextInt();
            return IntValue.of(intValue);
        };
    }

    public InputCommand ofInputCommandString(int subCmd) {
        RequestDataDelegate requestData = nextParamString("参数");
        return ofInputCommand(subCmd, requestData);
    }

    public RequestDataDelegate nextParamString(String paramTips) {
        return () -> {
            String info = "请输入{} | 参数类型 : String.class";
            log.info(info, paramTips);
            String s = ScannerKit.nextLine();
            Objects.requireNonNull(s);

            return StringValue.of(s);
        };
    }
}
