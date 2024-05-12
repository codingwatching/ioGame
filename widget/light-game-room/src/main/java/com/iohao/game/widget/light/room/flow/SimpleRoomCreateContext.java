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
package com.iohao.game.widget.light.room.flow;

import com.iohao.game.action.skeleton.core.commumication.CommunicationAggregationContext;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.core.flow.attr.FlowAttr;
import com.iohao.game.common.kit.attr.AttrOptions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

/**
 * @author 渔民小镇
 * @date 2024-05-12
 * @since 21.8
 */
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
final class SimpleRoomCreateContext implements RoomCreateContext {
    final long creatorUserId;
    final CommunicationAggregationContext aggregationContext;

    long gameId;
    int spaceSize;
    int startGameMinSpaceSize;
    AttrOptions options;

    public AttrOptions getOptions() {
        if (Objects.isNull(options)) {
            this.options = new AttrOptions();
        }

        return options;
    }

    public RoomCreateContext setSpaceSize(int spaceSize, int startGameMinSpaceSize) {
        this.spaceSize = spaceSize;
        this.startGameMinSpaceSize = startGameMinSpaceSize;
        return this;
    }

    SimpleRoomCreateContext(FlowContext flowContext) {
        this.creatorUserId = flowContext.getUserId();
        this.aggregationContext = flowContext.option(FlowAttr.aggregationContext);
    }
}