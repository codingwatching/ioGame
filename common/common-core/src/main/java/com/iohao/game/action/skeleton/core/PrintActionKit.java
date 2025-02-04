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
package com.iohao.game.action.skeleton.core;

import com.iohao.game.action.skeleton.IoGameVersion;
import com.iohao.game.action.skeleton.core.codec.DataCodec;
import com.iohao.game.action.skeleton.core.flow.ActionMethodInOut;
import com.iohao.game.action.skeleton.core.runner.Runners;
import com.iohao.game.action.skeleton.i18n.MessageKey;
import com.iohao.game.action.skeleton.i18n.Bundle;
import com.iohao.game.action.skeleton.toy.IoGameBanner;
import com.iohao.game.common.kit.ArrayKit;
import com.iohao.game.common.kit.StrKit;
import com.iohao.game.common.kit.exception.ThrowKit;
import lombok.experimental.UtilityClass;
import org.fusesource.jansi.Ansi;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 打印 action
 * <BR>
 *
 * @author 渔民小镇
 * @date 2021-12-12
 */
@UtilityClass
class PrintActionKit {

    void print(BarSkeleton barSkeleton, BarSkeletonSetting setting) {

        if (!setting.print) {
            return;
        }

        if (setting.isPrintHandler()) {
            var list = List.of(barSkeleton.getHandlers());
            PrintActionKit.printHandler(list);
        }

        if (setting.isPrintInout()) {
            var list = barSkeleton.inOutManager.listInOut();
            PrintActionKit.printInout(list);
        }

        if (setting.isPrintDataCodec()) {
            PrintActionKit.printDataCodec();
        }

        if (setting.isPrintRunners()) {
            extractedRunners(barSkeleton);
        }

        if (setting.isPrintAction()) {
            PrintActionKit.printActionCommand(barSkeleton.actionCommandRegions.actionCommands, setting.printActionShort);
        }

        IoGameBanner.printLine();
    }

    private static void extractedRunners(BarSkeleton barSkeleton) {
        Runners runners = barSkeleton.runners;
        List<String> nameList = runners.listRunnerName();
        String title = "@|CYAN ======================== Runners ========================= |@";
        IoGameBanner.println(Ansi.ansi().render(title));

        var printActionKitClose = Bundle.getMessage(MessageKey.printActionKitPrintClose);
        IoGameBanner.printlnMsg(printActionKitClose + " BarSkeletonBuilder.setting.printRunners");

        for (String name : nameList) {
            String info = String.format("@|BLUE %s |@", name);
            IoGameBanner.println(Ansi.ansi().render(info));
        }
    }

    /**
     * 打印 inout
     *
     * @param inOuts inOuts
     */
    void printInout(List<ActionMethodInOut> inOuts) {
        var businessFrameworkPlugin = Bundle.getMessage(MessageKey.businessFrameworkPlugin);
        printTitle(businessFrameworkPlugin);

        var printActionKitClose = Bundle.getMessage(MessageKey.printActionKitPrintClose);
        IoGameBanner.printlnMsg(printActionKitClose + " BarSkeletonBuilder.setting.printInout");

        for (ActionMethodInOut inOut : inOuts) {
            String info = String.format("@|BLUE %s |@", inOut.getClass());
            IoGameBanner.println(Ansi.ansi().render(info));
        }
    }

    private void printTitle(String title) {
        String formatted = "@|CYAN ======================== %s ========================= |@".formatted(title);
        IoGameBanner.println(Ansi.ansi().render(formatted));
    }

    void printHandler(List<Handler> handlers) {

        var businessFramework = Bundle.getMessage(MessageKey.businessFramework);

        printTitle(businessFramework);
        IoGameBanner.printlnMsg(IoGameVersion.VERSION);

        String colorStr = "@|BLACK BLACK|@ @|RED RED|@ @|GREEN GREEN|@ @|YELLOW YELLOW|@ @|BLUE BLUE|@ @|MAGENTA MAGENTA|@ @|CYAN CYAN|@ @|WHITE WHITE|@ @|DEFAULT DEFAULT|@";
        IoGameBanner.println(Ansi.ansi().render(colorStr));

        printTitle("Handler");

        var printActionKitClose = Bundle.getMessage(MessageKey.printActionKitPrintClose);
        IoGameBanner.printlnMsg(printActionKitClose + " BarSkeletonBuilder.setting.printHandler");

        for (Handler handler : handlers) {
            String info = String.format("@|BLUE %s |@", handler.getClass());
            IoGameBanner.println(Ansi.ansi().render(info));
        }
    }

    void printActionCommand(ActionCommand[][] behaviors, boolean shortName) {
        printTitle("action");

        var printActionKitClose = Bundle.getMessage(MessageKey.printActionKitPrintClose);
        IoGameBanner.printlnMsg(printActionKitClose + " BarSkeletonBuilder.setting.printAction");

        var printActionKitPrintFull = Bundle.getMessage(MessageKey.printActionKitPrintFull);
        IoGameBanner.printlnMsg(printActionKitPrintFull + " BarSkeletonBuilder.setting.printActionShort");

        String cmdName = Bundle.getMessage(MessageKey.cmdName);

        for (int cmd = 0; cmd < behaviors.length; cmd++) {
            ActionCommand[] subBehaviors = behaviors[cmd];

            if (Objects.isNull(subBehaviors)) {
                continue;
            }

            for (int subCmd = 0; subCmd < subBehaviors.length; subCmd++) {
                ActionCommand subBehavior = subBehaviors[subCmd];

                if (Objects.isNull(subBehavior)) {
                    continue;
                }

                ActionCommand.ParamInfo[] paramInfos = subBehavior.getParamInfos();
                String paramInfo = "";
                String paramInfoShort = "";

                if (ArrayKit.notEmpty(paramInfos)) {
                    paramInfoShort = ArrayKit.join(paramInfos, ", ");

                    paramInfo = Arrays.stream(paramInfos)
                            .map(theParamInfo -> theParamInfo.toString(true))
                            .collect(Collectors.joining(", "));
                }

                Map<String, Object> params = new HashMap<>();
                params.put("cmd", cmd);
                params.put("subCmd", subCmd);
                params.put("actionName", subBehavior.getActionControllerClazz().getName());
                params.put("methodName", subBehavior.getActionMethodName());
                params.put("paramInfo", paramInfo);
                params.put("paramInfoShort", paramInfoShort);
                params.put("actionNameShort", subBehavior.getActionControllerClazz().getSimpleName());
                params.put("throw", subBehavior.isThrowException() ? "throws" : "");

                shortName(params, shortName);

                // 返回类型
                ActionCommand.ActionMethodReturnInfo actionMethodReturnInfo = subBehavior.getActionMethodReturnInfo();
                params.put("returnTypeClazz", actionMethodReturnInfo.toString());
                params.put("returnTypeClazzShort", actionMethodReturnInfo.toString(false));

                checkReturnType(actionMethodReturnInfo.getReturnTypeClazz());

                shortName(params, shortName);

                params.put("actionSimpleName", subBehavior.getActionControllerClazz().getSimpleName());
                params.put("lineNumber", subBehavior.actionCommandDoc.getLineNumber());

                String routeCell = Color.red.format(cmdName + ": {cmd} - {subCmd}", params);
                String actionCell = Color.white.wrap("--- action :");
                String actionNameCell = Color.blue.format("{actionName}", params);
                String methodNameCell = Color.blue.format("{methodName}", params);
                String paramInfoCell = Color.green.format("{paramInfo}", params);

                String returnCell = Color.defaults.wrap("return");
                String returnValueCell = Color.magenta.format("{returnTypeClazz}", params);
                String throwCell = Color.red.format("{throw}", params);

                params.put("routeCell", routeCell);
                params.put("actionCell", actionCell);
                params.put("actionNameCell", actionNameCell);
                params.put("methodNameCell", methodNameCell);
                params.put("returnCell", returnCell);
                params.put("returnValueCell", returnValueCell);
                params.put("throwCell", throwCell);
                params.put("paramInfoCell", paramInfoCell);

                String lineTemplate = "{routeCell} {actionCell} {actionNameCell}.{methodNameCell}({paramInfoCell}) {throwCell} --- return {returnValueCell}  ~~~ see.({actionSimpleName}.java:{lineNumber})";
                String text = StrKit.format(lineTemplate, params);
                IoGameBanner.println(Ansi.ansi().render(text));
            }
        }
    }

    void printDataCodec() {
        var printActionKitDataCodec = Bundle.getMessage(MessageKey.printActionKitDataCodec);
        printTitle(printActionKitDataCodec);

        var printActionKitClose = Bundle.getMessage(MessageKey.printActionKitPrintClose);
        IoGameBanner.printlnMsg(printActionKitClose + " BarSkeletonBuilder.setting.printDataCodec");

        DataCodec dataCodec = DataCodecKit.dataCodec;
        String info = String.format("@|BLUE %s - %s |@", dataCodec.codecName(), dataCodec.getClass().getName());
        IoGameBanner.println(Ansi.ansi().render(info));

    }

    private void shortName(Map<String, Object> params, boolean shortName) {
        if (!shortName) {
            return;
        }

        params.put("paramInfo", params.get("paramInfoShort"));
        params.put("actionName", params.get("actionNameShort"));
        params.put("returnTypeClazz", params.get("returnTypeClazzShort"));
        params.put("actualTypeArgumentClazz", params.get("actualTypeArgumentClazzShort"));
    }

    private void checkReturnType(final Class<?> returnTypeClazz) {
        if (Set.class.isAssignableFrom(returnTypeClazz) || Map.class.isAssignableFrom(returnTypeClazz)) {
            // 参数的不支持不写逻辑了，这里告诉一下就行了。看之后的需要在考虑是否支持吧
            // Action return values and parameters do not support set, map and basic types!
            var printActionKitCheckReturnType = Bundle.getMessage(MessageKey.printActionKitCheckReturnType);
            ThrowKit.ofRuntimeException(printActionKitCheckReturnType);
        }
    }

    private static class Color {
        String start;
        static final Color red = new Color("@|red");
        static final Color white = new Color("@|white");
        static final Color blue = new Color("@|blue");
        static final Color green = new Color("@|green");
        static final Color defaults = new Color("@|default");
        static final Color magenta = new Color("@|magenta");

        public Color(String start) {
            this.start = start;
        }

        String wrap(String str) {
            return start + " " + str + "|@";
        }

        String format(String template, Map<String, Object> params) {
            String str = StrKit.format(template, params);
            str = wrap(str);
            return str;
        }
    }
}
