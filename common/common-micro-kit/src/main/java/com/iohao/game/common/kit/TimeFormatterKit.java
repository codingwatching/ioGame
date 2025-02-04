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
package com.iohao.game.common.kit;

import com.iohao.game.common.kit.time.ConfigTimeKit;
import com.iohao.game.common.kit.time.FormatTimeKit;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 时间格式化相关工具
 *
 * @author 渔民小镇
 * @date 2024-07-06
 * @since 21.11
 * @deprecated 请使用 {@link com.iohao.game.common.kit.time.FormatTimeKit}
 */
@Deprecated
@UtilityClass
public class TimeFormatterKit {
    /**
     * @deprecated 请使用 {@link ConfigTimeKit#getDefaultZoneId()}
     */
    public ZoneId defaultZoneId = ConfigTimeKit.getDefaultZoneId();
    @Deprecated
    public DateTimeFormatter defaultFormatter = FormatTimeKit.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Deprecated
    public DateTimeFormatter ofPattern(String pattern) {
        return FormatTimeKit.ofPattern(pattern);
    }

    @Deprecated
    public String formatter() {
        return defaultFormatter.format(LocalDateTime.now());
    }
}
