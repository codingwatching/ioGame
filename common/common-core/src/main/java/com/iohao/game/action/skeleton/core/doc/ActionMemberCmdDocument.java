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
package com.iohao.game.action.skeleton.core.doc;

import com.iohao.game.action.skeleton.core.CmdKit;
import lombok.Getter;
import lombok.Setter;

/**
 * action 成员变量的路由文档
 *
 * @author 渔民小镇
 * @date 2024-06-26
 * @since 21.11
 */
@Getter
@Setter
public final class ActionMemberCmdDocument {
    final int cmd;
    final int subCmd;
    final int cmdMerge;
    String comment;
    String memberName;

    ActionMemberCmdDocument(int cmd, int subCmd, String memberName, String comment) {
        this.cmd = cmd;
        this.subCmd = subCmd;
        this.cmdMerge = CmdKit.merge(cmd, subCmd);
        this.comment = comment;
        this.memberName = memberName;
    }
}
