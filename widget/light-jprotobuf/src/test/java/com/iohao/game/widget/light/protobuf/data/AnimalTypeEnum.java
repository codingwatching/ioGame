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
package com.iohao.game.widget.light.protobuf.data;

import com.baidu.bjf.remoting.protobuf.EnumReadable;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.widget.light.protobuf.ProtoFileMerge;
import lombok.ToString;

/**
 * TestAnimalTypeEnum
 *
 * @author 渔民小镇
 * @date 2024-12-16
 * @since 21.23
 */
@ToString
@ProtobufClass
@ProtoFileMerge(fileName = TempProtoFile.fileName, filePackage = TempProtoFile.filePackage)
public enum AnimalTypeEnum implements EnumReadable {
    /** the cat */
    cat(0),
    /** the tiger */
    tiger(10),
    ;

    final int value;

    AnimalTypeEnum(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }
}
